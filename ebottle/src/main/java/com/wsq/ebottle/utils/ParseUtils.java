package com.wsq.ebottle.utils;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class ParseUtils{

	
	
	
	public static String nurseParse(BluetoothDevice bluetoothDevice)
	{
		//奶瓶编号转换
	    String[] type=bluetoothDevice.getName().split("-");
		String[] str=bluetoothDevice.getAddress().split(":");
		Log.i("wsq","蓝牙地址:"+str.toString());
		
		String nurseId="";
		String waterId="";
		if(type[1].equals("B10"))
		{
			int bma1=Integer.parseInt(str[3], 16);
			int bma2=Integer.parseInt(str[4], 16);
			int bma3=Integer.parseInt(str[5], 16);
			nurseId="B10-"+(bma1+"")+(bma2+"")+(bma3+"02");
			return nurseId;
		}else if(type[1].equals("A10"))
		{
			
			int bma1=Integer.parseInt(str[3], 16);
			int bma2=Integer.parseInt(str[4], 16);
			int bma3=Integer.parseInt(str[5], 16);
			waterId="A10-"+(bma1+"")+(bma2+"")+(bma3+"");
			return waterId;
		}
		
		return null;
	}
	
}
