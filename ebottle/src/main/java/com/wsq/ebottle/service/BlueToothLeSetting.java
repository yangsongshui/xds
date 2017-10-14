package com.wsq.ebottle.service;

import android.net.ParseException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;



public class BlueToothLeSetting{

	//设置时间
	public static final void setCurrentTime(Date date, BlueToothLeService blueToothLeService)
	{
		String TAG="wsq";
		if(blueToothLeService == null ||date == null){
			return;
		}
		  SimpleDateFormat dateFormat;
		  String time;

		  
		  byte[] sendData= new byte[6];
		  
		  try {

			  dateFormat = new SimpleDateFormat("yy");
			  time =  dateFormat.format(date);
			  Log.i(TAG, "setCurrentTime:  time year="+time);
			  sendData[0] =  (byte) Integer.parseInt(time);

			  
			  dateFormat = new SimpleDateFormat("MM");
			  time =  dateFormat.format(date);
			  sendData[1] =  (byte) Integer.parseInt(time);
			  //Log.v(TAG,"sendData="+sendData[currentIndex]+"--index="+currentIndex);

			  dateFormat = new SimpleDateFormat("dd");
			  time =  dateFormat.format(date);
			  sendData[2] =  (byte) Integer.parseInt(time);
			  //Log.v(TAG,"sendData="+sendData[currentIndex]+"--index="+currentIndex);

			  TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
			  dateFormat = new SimpleDateFormat("HH");
			  time =  dateFormat.format(date);
			  sendData[3] =  (byte) Integer.parseInt(time);
			  //Log.v(TAG,"sendData="+sendData[currentIndex]+"--index="+currentIndex);

			  dateFormat = new SimpleDateFormat("mm");
			  time =  dateFormat.format(date);
			  sendData[4] =  (byte) Integer.parseInt(time);
			  //Log.v(TAG,"sendData="+sendData[currentIndex]+"--index="+currentIndex);

			  dateFormat = new SimpleDateFormat("ss");
			  time =  dateFormat.format(date);
			  sendData[5] =  (byte) Integer.parseInt(time);
			  //Log.v(TAG,"sendData="+sendData[currentIndex]+"--index="+currentIndex);

			  blueToothLeService.writeDataByUUID(BlueToothUUID.UUID_TIME_SERVICE_R_W, sendData);
			  
		  } catch (ParseException e1) {
		   e1.printStackTrace();
		  }
	}
}
