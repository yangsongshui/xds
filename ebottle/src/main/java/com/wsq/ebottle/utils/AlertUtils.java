package com.wsq.ebottle.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.wsq.ebottle.broadcast.CountDownReceiver;
import com.wsq.ebottle.service.AlertService;

public class AlertUtils {

	
	public static void startAlertService(Context context)
	{
		AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent=new Intent(context,CountDownReceiver.class);
		intent.setAction("com.wsq.ebottle");
		
	    PendingIntent pi= PendingIntent.getBroadcast(context, 0, intent, 0);
		//触发的起始时间
	     long triggerAtTime = SystemClock.elapsedRealtime();
         alarmManager.setRepeating(AlarmManager.RTC, triggerAtTime,60*1000, pi);
		Log.i("wsq","开始闹钟服务");
	}
	
	public static void stopAlertService(Context context)
	{
		AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent=new Intent(context,CountDownReceiver.class);
		intent.setAction("com.wsq.ebottle");
	    PendingIntent pi= PendingIntent.getBroadcast(context, 0, intent,0);
	    alarmManager.cancel(pi);
	    
	}
	
}
