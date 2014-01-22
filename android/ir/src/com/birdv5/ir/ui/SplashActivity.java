package com.birdv5.ir.ui;


import android.os.Bundle;

import com.birdv5.ir.R;
import com.birdv5.ir.ui.home.HomeActivity;
import com.birdv5.ir.utils.system.ActivityUtility;
import com.birdv5.ir.utils.view.DelaySwitchActivity;


public class SplashActivity extends DelaySwitchActivity{
	private final String LOG_TAG="SplashActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityUtility.hideTitleBar(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		setNextActivity(HomeActivity.class);
	}
}
