package com.birdv5.ir.ui;

/**
 * 开屏广告模式：
 * 
 *  1.缓存开屏广告
 *  	- 开屏广告第一次只是去请求到广告，不会展现。
 *  	- 当应用第二次启动的时候会展示上一次请求到的广告，广告展示结束请求一次请求。
 *  	- 竖屏请求到的广告，在横屏的时候是不会展现的，因为广告长和宽的尺寸不一致。
 *  
 *  2.实时开屏广告
 * 		- 开屏广告每次都实时请求广告，加载资源，展现广告。
 * 		- 如果广告请求到广告返回时间超出设定的超时时间，则本次请求不会展示。
 * 		- 如果广告资源加载时间超出设定的超时时间，则本次请求不会展示。
 *   
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import cn.domob.android.ads.DomobRTSplashAd;
import cn.domob.android.ads.DomobRTSplashAdListener;
import cn.domob.android.ads.DomobSplashAd;
import cn.domob.android.ads.DomobSplashAd.DomobSplashMode;
import cn.domob.android.ads.DomobSplashAdListener;

import com.birdv5.ir.R;
import com.birdv5.ir.ui.home.HomeActivity;

public class SplashActivity extends Activity {
	DomobSplashAd splashAd;
	DomobRTSplashAd rtSplashAd;
	// 缓存开屏广告:true 实时开屏广告:false
	// Cache splash ad:true Real-time splash ad:false
	private boolean isSplash = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		/**
		 * 
		 * DomobSplashMode.DomobSplashModeFullScreen 请求开屏广告的尺寸为全屏
		 * DomobSplashMode.DomobSplashModeSmallEmbed
		 * 请求开屏广告的尺寸不是全屏，根据设备分辨率计算出合适的小屏尺寸
		 * DomobSplashMode.DomobSplashModeBigEmbed
		 * 请求开屏广告的尺寸不是全屏，更具设备分辨率计算出合适的相对SmallMode的尺寸
		 * 
		 */
		String publisherId = this.getResources().getString(R.string.publisher_id);
		String splashPpid = this.getResources().getString(R.string.splash_ppid);
		if (isSplash) {
			// 缓存开屏广告
			// Cache splash ad
			splashAd = new DomobSplashAd(this,
					publisherId,
					splashPpid,
					DomobSplashMode.DomobSplashModeFullScreen);
			// setSplashTopMargin is available when you choose non-full-screen
			// splash mode.
			// splashAd.setSplashTopMargin(200);
			splashAd.setSplashAdListener(new DomobSplashAdListener() {
				@Override
				public void onSplashPresent() {
					Log.i("DomobSDKDemo", "onSplashStart");
				}

				@Override
				public void onSplashDismiss() {
					Log.i("DomobSDKDemo", "onSplashClosed");
					// 开屏回调被关闭时，立即进行界面跳转，从开屏界面到主界面。
					// When splash ad is closed, jump to the next(main) Activity
					// immediately.
					jump();
					// 如果应用没有单独的闪屏Activity，需要调用closeSplash方法去关闭开屏广告
					// If you do not carry a separate advertising activity, you
					// need to call closeRTSplash way to close the splash ad

					// splashAd.closeSplash();
				}

				@Override
				public void onSplashLoadFailed() {
					Log.i("DomobSDKDemo", "onSplashLoadFailed");
				}
			});

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					if (splashAd.isSplashAdReady()) {
						splashAd.splash(SplashActivity.this, SplashActivity.this
								.findViewById(R.id.splash_holder));
					} else {
						Toast.makeText(SplashActivity.this,
								"Splash ad is NOT ready.", Toast.LENGTH_SHORT)
								.show();
						jump();
					}
				}
			}, 1);
		} else {
			// 实时开屏广告
			// Real-time splash ad
			rtSplashAd = new DomobRTSplashAd(this,publisherId,splashPpid,
					DomobSplashMode.DomobSplashModeFullScreen);
			// setRTSplashTopMargin is available when you choose non-full-screen
			// splash mode.
			// rtSplashAd.setRTSplashTopMargin(200);
			rtSplashAd.setRTSplashAdListener(new DomobRTSplashAdListener() {
				@Override
				public void onRTSplashDismiss() {
					Log.i("DomobSDKDemo", "onRTSplashClosed");
					// 开屏回调被关闭时，立即进行界面跳转，从开屏界面到主界面。
					// When rtSplash ad is closed, jump to the next(main)
					// Activity immediately.
					jump();
					// 如果应用没有单独的闪屏Activity，需要调用closeRTSplash方法去关闭开屏广告
					// If you do not carry a separate advertising activity, you
					// need to call closeRTSplash way to close the splash ad

					// rtSplashAd.closeRTSplash();
				}

				@Override
				public void onRTSplashLoadFailed() {
					Log.i("DomobSDKDemo", "onRTSplashLoadFailed");
				}

				@Override
				public void onRTSplashPresent() {
					Log.i("DomobSDKDemo", "onRTSplashStart");
				}

			});

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					rtSplashAd.splash(SplashActivity.this,
							SplashActivity.this.findViewById(R.id.splash_holder));
				}
			}, 1);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Back key disabled
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void jump() {
		startActivity(new Intent(SplashActivity.this, HomeActivity.class));
		finish();
	}
}

// public class SplashActivity extends DelaySwitchActivity{
// // private final String LOG_TAG="SplashActivity";
// @Override
// protected void onCreate(Bundle savedInstanceState) {
// ActivityUtility.hideTitleBar(this);
// super.onCreate(savedInstanceState);
// setContentView(R.layout.activity_splash);
// setNextActivity(HomeActivity.class);
// updateVersion();
// }
//
// private void updateVersion(){
// UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
// @Override
// public void onUpdateReturned(int updateStatus,
// UpdateResponse updateInfo) {
// UmengUpdateAgent.setDefault();
//
// if (updateStatus == UpdateStatus.Yes) {
// UmengUpdateAgent.showUpdateDialog(SplashActivity.this, updateInfo);
// }
// }
// });
//
// UmengUpdateAgent.setUpdateAutoPopup(false);
// UmengUpdateAgent.setUpdateOnlyWifi(false);
// UmengUpdateAgent.update(this);
// }
// }

