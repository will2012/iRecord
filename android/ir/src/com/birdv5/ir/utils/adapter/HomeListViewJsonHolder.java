package com.birdv5.ir.utils.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.birdv5.ir.R;
import com.google.gson.JsonObject;

public class HomeListViewJsonHolder{
	private TextView txtView;
	
	public HomeListViewJsonHolder(Activity activity, View row) {
		txtView = (TextView) row.findViewById(R.id.title);
	}

	public void populateFrom(JsonObject jsonObject) {
		String title = jsonObject.get("title").getAsString();
		txtView.setText(title);
	}
}
