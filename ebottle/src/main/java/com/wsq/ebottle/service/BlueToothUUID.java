package com.wsq.ebottle.service;

import java.util.UUID;

public class BlueToothUUID {
	
	//服务Service_ID
    public static UUID UUID_SERVICE_ID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
    //时间服务
    public static UUID UUID_TIME_SERVICE_R_W= UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb") ;
    //饮水/奶服务
    public static UUID UUID_FEED_SERVICE  = UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb");
     //参数配置
    public static UUID UUID_PARAM = UUID.fromString("0000fff3-0000-1000-8000-00805f9b34fb") ;
     //电池电量
    public static UUID UUID_BATT_POWER= UUID.fromString("0000fff4-0000-1000-8000-00805f9b34fb") ;
     //机器序列号数据（不用）
    public static UUID UUID_SERIAL_NUM_R_W = UUID.fromString("0000fffe-0000-1000-8000-00805f9b34fb") ;
}
