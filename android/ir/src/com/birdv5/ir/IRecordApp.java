package com.birdv5.ir;

import java.io.IOException;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;

import com.birdv5.ir.database.DataBaseHelper;

public class IRecordApp extends Application {
	public static String versionName;
	public static DataBaseHelper dbHelper;

	@Override
	public void onCreate() {
		versionName = getVersionName();
		dbHelper = new DataBaseHelper(this);
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized SQLiteDatabase getDB() {
		return dbHelper.getDB();
	}
	
	public synchronized void closeDB() {
		 dbHelper.close();
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
