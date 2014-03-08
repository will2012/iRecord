/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.birdv5.ir.ui.home.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.birdv5.ir.IRecordApp;
import com.birdv5.ir.R;
import com.birdv5.ir.database.ArticlesTable;
import com.birdv5.ir.ui.DetailActivity;
import com.birdv5.ir.utils.adapter.HomeListViewAdapter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class SuperAwesomeFragment extends SherlockFragment{
	public static final String ARG_TYPE = "TYPE";
	private HomeListViewAdapter adapter;
	private PullToRefreshListView pullToRefreshView;
	private int type;
    private  int pos = 0;
    private final static int PAGE_SIZE = 10;
	
	
	public static SuperAwesomeFragment newInstance(int position) {
		SuperAwesomeFragment f = new SuperAwesomeFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_TYPE, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getArguments().getInt(ARG_TYPE);
	}

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_all, null);
		pullToRefreshView = (PullToRefreshListView) view.findViewById(R.id.lstView);
		adapter = new HomeListViewAdapter(this.getActivity());
		
		pullToRefreshView.setAdapter(adapter);
		
		pullToRefreshView.setOnRefreshListener(new OnRefreshListener2<ListView>(){
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				pos = 0;
				adapter.clear();
				getData();
				refreshView.onRefreshComplete();
				Log.i("pullToRefreshView","onPullDownToRefresh:"+refreshView.isRefreshing());
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getData();
				refreshView.onRefreshComplete();
				Log.i("pullToRefreshView","onPullUpToRefresh");
			}
			
		});
		
		getData();
		
		pullToRefreshView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,long arg3) {
				JsonObject object = adapter.getItem(position-1);
				Intent intent = new Intent(getActivity(),DetailActivity.class);
				String content = object.get("content").getAsString();
				String title = object.get("title").getAsString();
				
				intent.putExtra("title", title);
				intent.putExtra("content", content);
				getActivity().startActivity(intent);
			}
		});
		
		return view;
	}
	
	private void getData(){
		SQLiteDatabase db = ((IRecordApp) getActivity().getApplication()).getDB();
		String[] columns = {
				ArticlesTable.Columns.COLUMN_CONTENT};
		
		String selection = ArticlesTable.Columns.COLUMN_TYPE + "=? " + " limit "+PAGE_SIZE+" offset " + pos;
		String[] selectionArgs = { type+""};
	
		Cursor cursor = ArticlesTable.getInstance().query(db, columns,
				selection, selectionArgs, null);

		cursor.moveToFirst();
		
		int count = 0;
		while (!cursor.isAfterLast()) {
			String content = cursor.getString(0);
			JsonObject obj = new JsonParser().parse(content).getAsJsonObject();
			
			adapter.add(obj);
			cursor.moveToNext();
			count++; 
		}
		
		if (count > 0){
			pos++;
		}
		
		cursor.close();
		db.close();
		
	}
}