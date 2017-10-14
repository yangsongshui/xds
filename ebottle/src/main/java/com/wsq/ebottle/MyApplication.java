package com.wsq.ebottle;

import android.app.Application;
import android.content.SharedPreferences;

public class MyApplication extends Application{

	public int fTemp = 40;


	private static MyApplication application=null;
//
	public static MyApplication getInstance()
	{
		return application;
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		application=this;

		SharedPreferences sp = getSharedPreferences("device_data",MODE_PRIVATE);
		fTemp = sp.getInt("ftemp",40);


	}
}
