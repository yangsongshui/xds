package com.wsq.ebittle.common;

public class Constants {
	
	
	public static String BlueTooth_ON="com.wsq.ebottle.BlueTooth_ON";
	public static String BlueTooth_OFF="com.wsq.ebottle.BlueTooth_OFF";
	
	
	//错误吗
	public static String ERROR_WEIGHT="体重数据错误";
	public static String ERROR_DES_EMPTY="日记内容不能为空";
	
	//提醒模式
	public static int RING_VIB_TEXT=0;
	public static int VIB_TEXT=1;
	public static int ALL_OFF=2;

	//广播字段
    public final static String ACTION_GATT_CONNECTED ="com.wsq.ebottle.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.wsq.ebottle.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED ="com.wsq.ebottle.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_WRITE_ERROR ="com.wsq.ebottle.ACTION_WRITE_ERROR";
    
    public final static String ACTION_DATA_AVAILABLE ="com.wsq.ebottle.ACTION_DATA_AVAILABLE";
    public final static String BROADCAST_WRITE_TIME= "com.wsq.ebottle.BROADCAST_WRITE_TIME";
    
    public final static String BROADCAST_TEMP="com.wsq.ebottle.BROADCAST_TEMP";
    public final static String BROADCAST_START_SERILIZE="com.wsq.ebottle.BROADCAST_START_SERILIZE";
    public final static String BROADCAST_STOP_SERILIZE="com.wsq.ebottle.BROADCAST_STOP_SERILIZE";
    public final static String BROADCAST_START_FINAL_TEMP="com.wsq.ebottle.BROADCAST_START_FINAL_TEMP";

    public final static String BROADCAST_WRITE_PARAM="com.wsq.ebottle.BROADCAST_WRITE_PARAM";
    public final static String BROADCAST_REFRESH="com.wsq.ebottle.BROADCAST_REFRESH";
    
    public final static String BROADCAST_STOP_REFRESH="com.wsq.ebottle.BROADCAST_STOP_REFRESH";
    
    public final static String BROADCAST_BABY_UPDATA="com.wsq.ebottle.BROADCAST_BABY_UPDATA";
    //提醒倒计时
    public final static String BROADCAST_ONTICKING="com.wsq.ebottle.BROADCAST_ONTICKING";
    public final static String BROADCAST_ONTICK_FINISH="com.wsq.ebottle.BROADCAST_ONTICK_FINISH";
    
    //平均温度
    public final static String BROADCAST_AVETEMP="com.wsq.ebottle.BROADCAST_AVETEMP";
    //ͬ同步
    public final static String BROADCAST_SNY="com.wsq.ebottle.BROADCAST_SNY";
    
    //共享文件名
    public final static String COUNT_DOWN_TIME="count_down_time";
}
