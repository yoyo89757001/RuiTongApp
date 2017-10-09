package com.example.ruitongapp;

import android.app.Application;

import android.database.sqlite.SQLiteDatabase;

import android.util.Log;


import com.example.ruitongapp.beans.DaoMaster;
import com.example.ruitongapp.beans.DaoSession;
import com.example.ruitongapp.cookies.CookiesManager;
import com.tencent.bugly.Bugly;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;


/**
 * Created by tangjun on 14-8-24.
 */
public class MyApplication extends Application {

	private final static String TAG = "CookiesManager";
	public static MyApplication myAppLaction;
	public static String zhujidizhi=null;
	// 超时时间
	public static final int TIMEOUT = 1000 * 30;
	public  DaoMaster mDaoMaster;
	public  DaoSession mDaoSession;


	@Override
	public void onCreate() {
		super.onCreate();

		myAppLaction = this;
		setDatabase();
		try {

			Bugly.init(getApplicationContext(), "e13f717f4e", false);

		} catch (Exception e) {
			Log.d("gggg", e.getMessage());

		}

	}

	/**
	 * 设置greenDao
	 */
	private void setDatabase() {
		// 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
		// 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
		// 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
		// 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
		DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "ruitongapp", null);
		SQLiteDatabase db = mHelper.getWritableDatabase();
		// 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
		mDaoMaster = new DaoMaster(db);
		mDaoSession = mDaoMaster.newSession();
	}


	public  DaoSession getDaoSession() {
		Log.d(TAG, "mDaoSession:" + mDaoSession);
		return mDaoSession;
	}



	public static OkHttpClient getOkHttpClient(){

		return new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();
	}



}
