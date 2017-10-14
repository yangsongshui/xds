package com.wsq.ebottle.service;




import com.wsq.ebittle.common.Constants;
import com.wsq.ebottle.R;
import com.wsq.ebottle.utils.TimeUtils;
import com.wsq.ebottle.utils.ToastUtils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

public class AlertService extends Service{


	private int resId; //资源
	private int style;//闹铃方式
	private int time;//提醒时间
	
	private MediaPlayer mp=null;
	Vibrator vibrator=null;
	Handler handler=new Handler();
	boolean isShowText=false;
	//MyCountDownTimer timer=null;
	long countDown=-1;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	SharedPreferences preferences2;//倒计时
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		editor=getApplicationContext().getSharedPreferences(Constants.COUNT_DOWN_TIME, Context.MODE_PRIVATE).edit();
		preferences=getApplicationContext().getSharedPreferences("alert_setting", Context.MODE_PRIVATE);
		preferences2=getApplicationContext().getSharedPreferences(Constants.COUNT_DOWN_TIME,Context.MODE_PRIVATE);
		

		playMusic();
		
		
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	//播放铃声
	public void playMusic() {
		int nurAlertRingSel=preferences.getInt("nurse_alert_ring", 0);
		if(nurAlertRingSel==0)
		{
			resId=R.raw.xiao;
			
		}else if(nurAlertRingSel==1)
		{
			resId=R.raw.cai;
		}
		else if(nurAlertRingSel==2)
		{
			resId=R.raw.duo;
		}
		else if(nurAlertRingSel==3)
		{
		   resId=R.raw.ling;	
		}
		else if(nurAlertRingSel==4)
		{
			resId=R.raw.shu;
		}
		style=preferences.getInt("nurse_alert_style", -1);
	
		
		if(style==Constants.RING_VIB_TEXT)
		{
			setAllOff();
			
		    mp=MediaPlayer.create(getApplication(), resId);
		    mp.start();
		    
		    vibrator=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			//等待1秒，震动2秒，等待1秒，震动3秒
			long[] pattern = {1000, 2000, 1000, 3000};
			//-1表示不重复, 如果不是-1, 比如改成1, 表示从前面这个long数组的下标为1的元素开始重复.
			vibrator.vibrate(pattern, 0);
		    
		  
		    isShowText=true;
		    
		}else if(style==Constants.VIB_TEXT)
		{
			setAllOff();
			
			vibrator=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			//等待2000秒，震动0.5秒，等待3秒，震动0.5秒
			long[] pattern = {2000,500,3000,500};
			//-1表示不重复, 如果不是-1, 比如改成1, 表示从前面这个long数组的下标为1的元素开始重复.
			vibrator.vibrate(pattern, 0);

			//显示文字

			isShowText=true;
		    
		}else if(style==Constants.ALL_OFF)
		{
			setAllOff();
		}
		
		
	}
	
	
	public void setAllOff()
	{
		if(mp!=null)
		{
			mp.stop();
			mp=null;
		}
		if(vibrator!=null)
		{
			vibrator.cancel();
			vibrator=null;
		}
		isShowText=false;
		
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(mp!=null)
			 mp.stop();
		if(vibrator!=null)
			vibrator.cancel();
		/*if(timer!=null)
			timer.cancel();*/
		stopSelf();
		super.onDestroy();
	}
}
