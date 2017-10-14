package com.wsq.ebottle.broadcast;

import com.wsq.ebittle.common.Constants;
import com.wsq.ebottle.utils.TimeUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class CountDownReceiver extends BroadcastReceiver{

	long countDown;
	SharedPreferences preferences;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		
		if(arg1.getAction().equals("com.wsq.ebottle"))
		{
			
			long offset=TimeUtils.getCurrentMill()-arg0.getSharedPreferences(Constants.COUNT_DOWN_TIME,Context.MODE_PRIVATE)
					.getLong("start_mill",TimeUtils.getCurrentMill());
			preferences=arg0.getSharedPreferences("alert_setting", Context.MODE_PRIVATE);
			
			int alertTime=preferences.getInt("nurse_alert_time", 0);
			if(alertTime==1)
			{
				countDown=2*3600;
				
			}else if(alertTime==2)
			{
				countDown=(long) (2.5f*3600);
			}
			else if(alertTime==3)
			{
				countDown=3*3600;
			}
			else if(alertTime==4)
			{
				countDown=(long) (3.5f*3600);
			}
			else if(alertTime==5)
			{
				countDown=4*3600;
			}
			
			if(offset>countDown*1000)
			{
				if(arg0.getSharedPreferences(Constants.COUNT_DOWN_TIME,Context.MODE_PRIVATE).getBoolean("isStart",false))
				{
					Log.i("wsq","时间到了");
					Intent intent=new Intent(Constants.BROADCAST_ONTICK_FINISH);
					arg0.sendBroadcast(intent);
				}
				return;
			}
			long remindTime=(countDown*1000-offset)/1000;
			Log.i("wsq","剩余时间="+remindTime);
			
			int hour=0;
			int minute=0;
			if(remindTime<3600)
			{
				hour=0;
			}else
			{
			   hour=(int) (remindTime/3600);  
			}
			
			if(remindTime<60)
			{
				minute=0;
			}else
			{
				minute=(int) (remindTime%3600)/60;
			}
            //计算倒计时
			
			Intent intent=new Intent(Constants.BROADCAST_ONTICKING);
			intent.putExtra("time",hour+"小时"+minute+"分");
			arg0.sendBroadcast(intent);
			
		}
	}

}
