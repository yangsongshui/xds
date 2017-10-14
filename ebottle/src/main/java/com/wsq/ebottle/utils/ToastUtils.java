package com.wsq.ebottle.utils;

import com.wsq.ebottle.MyApplication;

import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {
	
	
	
	public static void show(String msg)
	{
		Toast.makeText(MyApplication.getInstance().getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
	}

	public static void show(String msg,int position)
	{
		Toast toast=Toast.makeText(MyApplication.getInstance().getApplicationContext(),msg, Toast.LENGTH_SHORT);
		toast.setGravity(position, 0, 20);
		toast.show();

	}
}
