package com.birdv5.ir;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.umeng.analytics.MobclickAgent;

public class IRecordApp extends Application {
	   public static String versionName;
	   @Override
	    public void onCreate() {
		   versionName = getVersionName();
		   MobclickAgent.setDebugMode( true );
	   }
	   
	   
	   
	   /**
	     * Get versionName from Manifest.xml
	     *
	     * @return versionName
	     */
	    private String getVersionName() {
	        PackageManager pm = getPackageManager();
	        try {
	            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
	            return pi.versionName == null ? "" : pi.versionName;
	        } catch (NameNotFoundException e) {
	            return "";
	        }
	    }
}
