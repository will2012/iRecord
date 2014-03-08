package com.birdv5.ir.utils.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.birdv5.ir.R;
import com.google.gson.JsonObject;

public class HomeListViewAdapter extends ArrayAdapter<JsonObject>{

	private Activity activity;

	public HomeListViewAdapter(Activity activity) {
		super(activity, R.layout.home_list_row);
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		HomeListViewJsonHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			row = inflater.inflate(R.layout.home_list_row, parent,false);
			holder = new HomeListViewJsonHolder(activity, row);
			row.setTag(holder);
		} else {
			holder = (HomeListViewJsonHolder) row.getTag();
		}
		
		holder.populateFrom(getItem(position));
		
		return row;
	}
}
