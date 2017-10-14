package com.wsq.ebottle.service;

import android.bluetooth.BluetoothGattCharacteristic;

public interface BluetoothDataInterface{

	public void onReceiveBattery(byte []batteryData,boolean haveData);//读取电量
	public void onReceiveTemp(byte []tempData,boolean haveData);//读取温度
	public void onReceiveFeedRecord(byte []data,boolean haveData);//接收饮水/奶的记录
	public void onCompleteReceiveFeedRecord(BluetoothGattCharacteristic characteristic);//接收记录完成

	public void onReceiveSerialNum(byte []data,boolean haveData);//接收机器序列号
	public void onReceiveParam(byte[] data,boolean haveData);//接收机器参数
}
