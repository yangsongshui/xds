package com.wsq.ebottle.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;

import com.wsq.ebittle.common.Constants;
import com.wsq.ebottle.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BlueToothLeService extends Service {

	private static String TAG = "BlueToothLeService";

	private BluetoothDevice bluetoothDevice;
	private BluetoothAdapter bluetoothAdapter;
	private BluetoothManager bluetoothManager;
	private BluetoothGatt bluetoothGatt;

	private BluetoothDevice deviceToConnect;

	private int connectionState = STATE_DISCONNECTED;

	private static final int STATE_DISCONNECTED = 0;
	private static final int STATE_CONNECTING = 1;
	private static final int STATE_CONNECTED = 2;

	private String bluetoothDeviceAddress;
	private BluetoothDataInterface dataInterface;

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					bluetoothGatt.discoverServices();
					break;
			}

		}
	};

	@Override
	public IBinder onBind(Intent arg0) {
		return iBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(TAG,"onUnbind");
		close();
		stopSelf();
		return super.onUnbind(intent);
	}

	public class LocalBinder extends Binder {
		public BlueToothLeService getService() {
			return BlueToothLeService.this;
		}
	}

	private final IBinder iBinder = new LocalBinder();

	@SuppressLint("NewApi")
	private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

			super.onCharacteristicChanged(gatt, characteristic);
		}

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			// TODO Auto-generated method stub
			Log.v(TAG, "onCharacteristicRead status" + status +
    	    		"read data="+Arrays.toString(characteristic.getValue())+"=length="+characteristic.getValue().length);
			 if (status == BluetoothGatt.GATT_SUCCESS) 
			 {
				    
	                broadcastUpdate(Constants.ACTION_DATA_AVAILABLE, characteristic);
	      
	         }else
	         {
	            	
	                 broadcastUpdate(Constants.ACTION_WRITE_ERROR);
	         }
		}

		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {

			if(status == BluetoothGatt.GATT_FAILURE || status == 133)
			{
				Log.i(TAG,"写入错误");
        		broadcastUpdate(Constants.ACTION_WRITE_ERROR);
            }	
			else if(BlueToothUUID.UUID_TIME_SERVICE_R_W.equals(characteristic.getUuid()))
			{
				Log.i(TAG,"写入时间成功");
				broadcastUpdate(Constants.BROADCAST_WRITE_TIME);
			}else if(BlueToothUUID.UUID_PARAM.equals(characteristic.getUuid()))
			{
				Log.i(TAG,"写入PARAM成功");
				broadcastUpdate(Constants.BROADCAST_WRITE_PARAM);
			}
			
	    }


		//连接状态监听
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			// TODO Auto-generated method stub
			String intentAction ;
			if(newState==BluetoothProfile.STATE_CONNECTED)
			{
				Log.i(TAG,"onCharacteristicStateChange=="+"连接成功");
				   intentAction = Constants.ACTION_GATT_CONNECTED;
	               connectionState = STATE_CONNECTED;
	               broadcastUpdate(intentAction);

				   handler.sendEmptyMessageDelayed(0,200);

	                
			}else if(newState==BluetoothProfile.STATE_DISCONNECTED)
			{
				
				intentAction=Constants.ACTION_GATT_DISCONNECTED;
				connectionState=STATE_DISCONNECTED;
				broadcastUpdate(intentAction);
				
			}
			
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			// TODO Auto-generated method stub
			Log.i(TAG,"发现GATT Service");
			if(status==BluetoothGatt.GATT_SUCCESS)
			{ 
				
				List<BluetoothGattService> services=bluetoothGatt.getServices();
				if(services.size()<0)
				{
					 bluetoothGatt.discoverServices();
				}else
				{
					// 发送广播
					 broadcastUpdate(Constants.ACTION_GATT_SERVICES_DISCOVERED);
				}
			}else
			{
				broadcastUpdate(Constants.ACTION_WRITE_ERROR);
			}
		}

	};


	//发送广播,不带数据
	private void broadcastUpdate(String action)
	{
		Intent intent=new Intent(action);
		sendBroadcast(intent);
	}
	//发送带数据的广播
	@SuppressLint("NewApi") 
	private void broadcastUpdate(String action,BluetoothGattCharacteristic characteristic)
	{

		//饮水/奶服务
		if(BlueToothUUID.UUID_FEED_SERVICE.equals(characteristic.getUuid()))
		{
			getFeedHisData(characteristic);
		}
		//电量
		else if(BlueToothUUID.UUID_BATT_POWER.equals(characteristic.getUuid()))
		{
			getBattData(characteristic);
		}
		//参数配置
		else if(BlueToothUUID.UUID_PARAM.equals(characteristic.getUuid()))
		{
			getParamData(characteristic);
		}
		//机器序列号
		else if(BlueToothUUID.UUID_SERIAL_NUM_R_W.equals(characteristic.getUuid()))
		{
			Log.i(TAG,"读取机器序列号成功");
			getSerialNumData(characteristic);
		}
			
	}

	//获取机器序列号
	private void getSerialNumData(BluetoothGattCharacteristic characteristic) {
		// TODO Auto-generated method stub
		byte[] data=characteristic.getValue();
		Log.i(TAG,"读取机器序列号length="+data.length);
		if(data.length<=0)
		{
			return;
		}
		if(dataInterface!=null)
		{
			dataInterface.onReceiveSerialNum(data, true);
		}
		
	}

	//获取机器配置参数
	private void getParamData(BluetoothGattCharacteristic characteristic) {
		// TODO Auto-generated method stub
		byte[] data=characteristic.getValue();
		Log.i(TAG,"读取机器参数length="+data.length);
		if(data.length<=0)
		{
			return;
		}
		if(dataInterface!=null)
		{
			dataInterface.onReceiveParam(data, true);
		}
	}

	//获取feed记录
	private void getFeedHisData(BluetoothGattCharacteristic characteristic) 
	{
		// TODO Auto-generated method stub
		
		byte[] data=characteristic.getValue();
		Log.i(TAG,"读取Feed的历史记录length="+data.length);
		if(data.length<=0)
		{
			return;
		}
		if((data[0]&0xff)==0&&(data[1]&0xff)==0)
    	{
    		dataInterface.onCompleteReceiveFeedRecord(characteristic);
    		return;
    	}
		
		if(dataInterface!=null)
		{
			dataInterface.onReceiveFeedRecord(data, true);
		}
		
		
	}

	//获取电量
	@SuppressLint("NewApi")
	private void getBattData(BluetoothGattCharacteristic characteristic) {
		byte[] data=characteristic.getValue();
		Log.i(TAG, "获取电量数据length="+data.length);
		if(data.length<=0)
		{
			return;
		}
		
		if(((data[0]&0xff) == 0))
	    {
	    	 if(dataInterface != null)
	    		 dataInterface.onReceiveBattery(null, false);
	    	 return;
	    }
		
		 if(dataInterface!=null)
	     {
	    	dataInterface.onReceiveBattery(data, true);
	    	
	     }
		
		
	}
	

	@SuppressLint("NewApi")
	public boolean initialize() {
		if (bluetoothManager == null) {
			bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
			if (bluetoothManager == null) {
				Log.i(TAG, "Unable to initialize BluetoothManager.");
				return false;
			}
		}
		bluetoothAdapter = bluetoothManager.getAdapter();
		if (bluetoothAdapter == null) {
			Log.i(TAG, "Unale to initialize BluetoothAdapter");
			return false;
		}

		return true;
	}

	// 连接ble设备
	@SuppressLint("NewApi")
	public boolean connect(String address) {
		if (bluetoothAdapter == null || address == null) {
			Log.i(TAG,
					"BluetoothAdapter not initialized or unspecified address.");
			return false;
		}
		if (bluetoothDeviceAddress != null&& address.equals(bluetoothDeviceAddress)
				&& bluetoothGatt != null) {
			Log.i(TAG,"Trying to use an existing mBluetoothGatt for connection.");
			if (bluetoothGatt.connect()) {
				connectionState = STATE_CONNECTING;
				return true;
			} else {
				Log.i(TAG, "连接失败");
				return false;
			}
		}

		BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
		deviceToConnect = device;
		if (device == null) {
			Log.i(TAG, "Device not found.  Unable to connect.");
			return false;
		}
		bluetoothGatt = device.connectGatt(this, false, gattCallback);
		Log.i(TAG, "Trying to create a new connection.");
		bluetoothDeviceAddress = address;
		connectionState = STATE_CONNECTING;
		Log.i(TAG, "device.getBondState==" + device.getBondState());

		return true;
	}

	public boolean connect() {

		if (bluetoothGatt == null) 
		{
			return false;
		} else {
			connectionState = STATE_CONNECTING;
			return bluetoothGatt.connect();
		}
	}

	// 断开连接
	@SuppressLint("NewApi")
	public void disConnect() {
		Log.i(TAG, "disConnect: 354 主动 断开蓝牙");
		if (bluetoothAdapter == null || bluetoothGatt == null) {
			Log.i(TAG, "BluetoothAdapter not initialized");
			return;
		}
		bluetoothGatt.disconnect();
	}

	// 判断连接状态
	public boolean isConnected() {
		if (connectionState == STATE_CONNECTING) {
			return true;
		} else {
			return false;
		}
	}

	// 关闭gatt
	@SuppressLint("NewApi")
	public void close() {
		if (bluetoothGatt == null) {
			return;
		}
		bluetoothGatt.disconnect();
		if (bluetoothGatt != null) {
			bluetoothGatt.close();
			bluetoothGatt = null;
		}
	}

	//读取数据
	@SuppressLint("NewApi") 
	public void readDataByUUID(UUID uuid)
	{
		Log.i(TAG,"开读数据");
		if(bluetoothGatt==null)
		{
			Log.i(TAG,"bluetoothGatt is null");
			return;
		}
		List<BluetoothGattService> list=this.getSupportedGattServices();
		for(int i=0;i<list.size();i++)
		{
			BluetoothGattService bluetoothGattService = list.get(i);
	    	if(bluetoothGattService == null){
	    		continue;
	    	}
	    	BluetoothGattCharacteristic gattCharacteristic=bluetoothGattService.getCharacteristic(uuid);
	    	if(gattCharacteristic!=null)
	    	{
				Log.i(TAG, "=write=UUID==" + gattCharacteristic.getUuid());
				Log.i(TAG, "==getProperties==" + gattCharacteristic.getProperties());
				Log.i(TAG, "==PROPERTY_READ==" + gattCharacteristic.PROPERTY_READ);


					boolean success=bluetoothGatt.readCharacteristic(gattCharacteristic);
					Log.i(TAG,"read data=="+success);


	    	}else
	    	{
	    		Log.i(TAG, "not  UUID");
	    	}
	    	
	    	
		}

	}

	//写入数据
	@SuppressLint("NewApi") 
	public void writeDataByUUID(UUID uuid,byte[] data)
	{
		Log.i(TAG,"开始写数据");
		if (bluetoothGatt == null) 
		{
			return;
		}
		
		BluetoothGattService gattService=bluetoothGatt.getService(BlueToothUUID.UUID_SERVICE_ID);
		if(gattService!=null)
		{
			BluetoothGattCharacteristic characteristic=gattService.getCharacteristic(uuid);
			if(characteristic!=null)
			{
				Log.i(TAG, "=write=UUID==" + characteristic.getUuid());
				Log.i(TAG, "==getProperties==" + characteristic.getProperties());
				Log.i(TAG, "==writeDataByCharacteristic==" + new String(data));
				characteristic.setValue(data);
			}

			boolean success=bluetoothGatt.writeCharacteristic(characteristic);
			Log.i(TAG,"write data=="+success);
			
		}else
		{
			ToastUtils.show("写入数据失败", Gravity.CENTER);
		}
		
	}


	//获取所有的gattservice
	@SuppressLint("NewApi") 
	public List<BluetoothGattService> getSupportedGattServices()
	{
		if(bluetoothGatt==null)
		{
			return null;
		}
		 List<BluetoothGattService> gattServices=new ArrayList<BluetoothGattService>();
		 gattServices.add(bluetoothGatt.getService(BlueToothUUID.UUID_SERVICE_ID));
		 return gattServices;
		
	}
	
	
	
	public void setDataInterface(BluetoothDataInterface dataInterface)
	{
		this.dataInterface=dataInterface;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	
	
	
}
