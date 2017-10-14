package com.wsq.ebottle.utils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

public class BlueToothUtils {



	//判断蓝牙是否打开
	@SuppressLint("NewApi") 
	public static boolean isOpenBlueTooth(Context context)
	{
		BluetoothManager bluetoothManager=(BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
		BluetoothAdapter adapter=bluetoothManager.getAdapter();
		if(adapter.isEnabled())
		{
			return true;
		}
		return false;
		
	}
}
