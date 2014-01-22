package com.birdv5.ir.ui.base;

import java.io.Serializable;

import android.app.Activity;
import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;

/**
 * Base  activity
 */
public class BaseActivity extends Activity {

	/**
	 * Finder bound to this activity's view
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * Get intent extra
	 * 
	 * @param name
	 * @return serializable
	 */
	@SuppressWarnings("unchecked")
	protected <V extends Serializable> V getSerializableExtra(final String name) {
		return (V) getIntent().getSerializableExtra(name);
	}

	/**
	 * Get intent extra
	 * 
	 * @param name
	 * @return int
	 */
	protected int getIntExtra(final String name) {
		return getIntent().getIntExtra(name, -1);
	}

	/**
	 * Get long extra
	 * 
	 * @param name
	 * @return
	 */
	protected long getLongExtra(final String name) {
		return getIntent().getLongExtra(name, -1);
	}

	/**
	 * Get intent extra
	 * 
	 * @param name
	 * @return string
	 */
	protected String getStringExtra(final String name) {
		return getIntent().getStringExtra(name);
	}

	/**
	 * Get intent extra
	 * 
	 * @param name
	 * @return string array
	 */
	protected String[] getStringArrayExtra(final String name) {
		return getIntent().getStringArrayExtra(name);
	}

	
}