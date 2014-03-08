package com.birdv5.ir.ui;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.birdv5.ir.R;

public class DetailActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		
		String title = this.getIntent().getStringExtra("title");
		String content = this.getIntent().getStringExtra("content");
		
		getSupportActionBar().setTitle(title);

		setContentView(R.layout.activity_detail);
		TextView detailTxtView = (TextView) findViewById(R.id.detailTxtView);
		detailTxtView.setText(Html.fromHtml(content));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	      case android.R.id.home:
	    	  finish();
	        return(true);
	    }
	    // more code here for other cases
	    return (true);
	 }
}
