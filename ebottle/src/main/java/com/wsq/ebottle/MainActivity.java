package com.wsq.ebottle;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wsq.ebittle.common.Constants;
import com.wsq.ebottle.adapter.MenuListAdapter;
import com.wsq.ebottle.dialog.CustomDialog;
import com.wsq.ebottle.fragment.AlertSettingFragment;
import com.wsq.ebottle.fragment.BabyDataFragment;
import com.wsq.ebottle.fragment.BabyDataFragment.HeadImgListener;
import com.wsq.ebottle.fragment.BabyDiaryFragment;
import com.wsq.ebottle.fragment.DeviceManagerFragment;
import com.wsq.ebottle.fragment.DeviceManagerFragment.DeviceListener;
import com.wsq.ebottle.fragment.GrowRecordFragment;
import com.wsq.ebottle.fragment.LoveFragment;
import com.wsq.ebottle.fragment.MainFragment;
import com.wsq.ebottle.fragment.NurseFragment;
import com.wsq.ebottle.fragment.TempFragment;
import com.wsq.ebottle.fragment.WaterFragment;
import com.wsq.ebottle.service.BlueToothLeService;
import com.wsq.ebottle.service.BlueToothLeSetting;
import com.wsq.ebottle.service.BlueToothUUID;
import com.wsq.ebottle.service.BluetoothDataInterface;
import com.wsq.ebottle.utils.AlertUtils;
import com.wsq.ebottle.utils.BlueToothUtils;
import com.wsq.ebottle.utils.ParseUtils;
import com.wsq.ebottle.utils.ToastUtils;
import com.wsq.ebottle.utils.slidingmenu.SlidingMenu;
import com.wsq.ebottle.widget.CircleImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends FragmentActivity implements OnClickListener{
	private static final String TAG="MainActivity";
	
	public static MainActivity mainActivity=null;
	//侧滑菜单
	private SlidingMenu slidingMenu=null;	
	private ListView listMenu;
	private ArrayList<Map<String,Integer>> menus;
	private MenuListAdapter adapter;
	
	private ImageView imgOpenMenu;
	private ImageView imgLy;
	private ImageView imgDc;
	private TextView txtTittle;
	private ImageView imgHome;

	//头像
	private CircleImageView cimgHead;
	private ImageView imgHeadBg;
	private RelativeLayout topLayout;
	private BabyDataFragment dataFragment;
	private DeviceManagerFragment deviceManagerFragment;
	private MainFragment mainFragment;
	private BabyDiaryFragment babyDiaryFragment;
	private LoveFragment loveFragment;
	private WaterFragment waterFragment;
	private NurseFragment nurseFragment;
	private GrowRecordFragment growRecordFragment;
	private AlertSettingFragment alertSettingFragment;
	private TempFragment tempFragment;

	Fragment fragment=null;
	Fragment[] fragments=null;
	int index;
	int curIndex;
	//要进行连接的蓝牙设备
	private BluetoothDevice bluetoothDevice=null;
	
	private String curFragmentTag="com.wsq.ebottle";
	
	public static  BlueToothLeService blueToothLeService=null;
	
	private static int SCAN_BROADCAST=1001;
	private BluetoothAdapter bluetoothAdapter;

	//是否开始扫描
	private boolean isScan=false;
	SharedPreferences.Editor connectedBluetootheDataEitor;
	
	SharedPreferences.Editor countDownEditor;
	
	SharedPreferences.Editor babyDataEditor;
	//蓝牙地址
	private String bluetoothAddress=null;
	//连接状态
	private boolean isConnected=false;
	//是否进行消毒的有关操作
	private boolean isSerilized=false;
	private boolean isFinalTemp = false;


	//记录温度
	ArrayList<Integer> temps=new ArrayList<Integer>();

	//连接服务回调
	private ServiceConnection serviceConnection=new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			blueToothLeService=null;
		}
		
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			blueToothLeService=((BlueToothLeService.LocalBinder)arg1).getService();
			if(!blueToothLeService.initialize())
			{
				Log.i("wsq", "Unable to initialize Bluetooth");
                finish();
			}else
			{
				//连接设备
				String address=getSharedPreferences("device_data",Context.MODE_PRIVATE).getString("address","null");
				if(!address.equals("null"))
				{
					//连接设备
					OnConnect(address);
				}
			}
			Log.i("wsq","连接服务");
		}
	};


	//广播
	BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			if(BlueToothUtils.isOpenBlueTooth(MainActivity.this))
			{
				imgLy.setImageResource(R.drawable.ly);

			}else
			{
				imgLy.setImageResource(R.drawable.ly2);

			}
		}
	};


	private void sendBroadcast(String action){
		sendBroadcast(new Intent(action));
	}


	//服务广播
	private BroadcastReceiver gattUpdateReceiver=new BroadcastReceiver()
	{

		@Override
		public void onReceive(Context arg0, Intent intent) {

			final String action=intent.getAction();
			//连接成功
			if(Constants.ACTION_GATT_CONNECTED.equals(action))
			{
				isConnected=true;
				Log.i(TAG,"连接成功"+isConnected);
				
			}else if(Constants.ACTION_GATT_DISCONNECTED.equals(action))
			{
				
				isConnected=false;
				Log.i("wsq","断开连接");
				//停止刷新
				sendBroadcast(Constants.BROADCAST_STOP_REFRESH);
				
			}else if (Constants.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) 
			{
				Log.i(TAG,"215 发现gatt服务");
				  
				  handler2.removeCallbacks(runnable);

				//停止刷新
				  sendBroadcast(Constants.BROADCAST_STOP_REFRESH);

				   if(pd!=null)
					{
						pd.dismiss();
					}
				//保存蓝牙mac地址
					if(bluetoothDevice!=null)
					{
					    connectedBluetootheDataEitor.putString("address", bluetoothDevice.getAddress());
					    connectedBluetootheDataEitor.commit();
					    
					    String str=ParseUtils.nurseParse(bluetoothDevice);
					    String[] temp=str.split("-");
						if(temp[0].equals("B10"))//奶瓶
						{
							babyDataEditor.putString("nurse_bottle_id",temp[1]);
							//Log.i("wsq","奶瓶编号:"+temp[1]);
						}else if(temp[0].equals("A10"))//水瓶
						{
							babyDataEditor.putString("water_bottle_id",temp[1]);
							//Log.i("wsq","水瓶编号:"+temp[1]);
						}
					    babyDataEditor.commit();
  
					}

				//设置时间
				  BlueToothLeSetting.setCurrentTime(new Date(), blueToothLeService);
				  
				  blueToothLeService.setDataInterface(new BluetoothDataInterface() {

					  //接收温度
					@Override
					public void onReceiveTemp(byte[] tempData, boolean haveData) {

					}
					
					@Override
					public void onReceiveBattery(byte[] batteryData, boolean haveData) {

						int battery=Integer.valueOf((batteryData[0]&0xff)+"", 10);
						Log.i(TAG,"battery=="+battery);
						if(battery>=80&&battery<=100)
						{
							handler2.sendEmptyMessage(3);
							
						}else if(battery>=30&&battery<80)
						{
							handler2.sendEmptyMessage(2);
							
						}else if(battery>=0&&battery<30)
						{
							handler2.sendEmptyMessage(1);
						}
						
						connectedBluetootheDataEitor.putInt("battery",battery);
						connectedBluetootheDataEitor.apply();



						//TODO 2017-5-16 在这里读记录
//						读完电量开始读饮水/奶服务的记录
						blueToothLeService.readDataByUUID(BlueToothUUID.UUID_FEED_SERVICE);
						
					}
					  //接收记录数据
					@Override
					public void onReceiveFeedRecord(byte[] data,
							boolean haveData) {
						// TODO Auto-generated method stub
						Log.i("wsq","接收feed记录");
						//处理数据
						int type=data[2];
						int year=2000+data[3];
						int month=data[4];
						int day=data[5];
						int hour=data[6];
						int minute=data[7];
						int lastTime=data[8];
						String date=null;
						date=String.format("%d年%d月%d日-%d:%d",year,month,day,hour,minute);
						String eventDate=String.format("%d-%d %d:%d",month,day,hour,minute);
						 int tem1=data[9]&0xff;
					     int tem2=data[10]&0xff;
					    int temp=tem1*255+tem2;
						Log.i("wsq","记录时间为=="+date+";lastTime="+lastTime+";temp="+temp);

						temps.add(temp);

						blueToothLeService.readDataByUUID(BlueToothUUID.UUID_FEED_SERVICE);
					}

					  //完成记录接收
					@Override
					public void onCompleteReceiveFeedRecord( BluetoothGattCharacteristic characteristic) {

						Log.i(TAG,"完成feed记录的接收");
						if(temps.size()>0)
						{
							int sumTemp=0;
							for(int i:temps)
							{
								sumTemp+=i;
							}
						   float aveTemp=sumTemp/temps.size();
						   Intent intent=new Intent(Constants.BROADCAST_AVETEMP);
						   intent.putExtra("aveTemp",aveTemp);
						   sendBroadcast(intent);
						   temps.clear();
						}

						//if() 是现在的新版本
						// 是否消毒都读取配置参数
						blueToothLeService.readDataByUUID(BlueToothUUID.UUID_PARAM);


						//TODO 旧版本使用
						//读取机器参数
//						if(isSerilized)
//						{
//							Log.i("wsq","开始读param");
//							blueToothLeService.readDataByUUID(BlueToothUUID.UUID_PARAM);
//						}else
//						{
//							//Log.i("wsq","开始广播"+isConnected);
//							//开始扫描广播
//							if(isConnected)
//							{
//								blueToothLeService.disConnect();
//								handler2.sendEmptyMessage(SCAN_BROADCAST);
//							}
//						}
						
					}

					  //接收机器参数
					@Override
					public void onReceiveParam(byte[] data, boolean haveData) {

						Log.i(TAG,"接收机器参数");
						int version= data[5];
						Log.i(TAG, "onReceiveParam:  version="+version);
					  if(data[4]=='B')
					  {
						   int type=data[0];
						   int model=data[1];
						  String state=Integer.toBinaryString(data[2]&0x01); //0停止，1加热
						  String state2=Integer.toBinaryString((data[2]&(1<<0x01))>>1);//0未插电，1已插电
						  String state3=Integer.toBinaryString((data[2]&(2<<0x01))>>2);//0恒温状态，1未达到恒温
						  String state4=Integer.toBinaryString((data[2]&(3<<0x01))>>3);//消毒模式，0停止，1开始
						  Log.i(TAG,"机器参数："+"type="+type+";model="+model+"state="+state+":"+state2+":"+state3+":"+state4+";瓶子："+(char)data[4]);

						  if(state2.equals("0"))
						  {
							  Log.i(TAG, "onReceiveParam: 未插电");
							  handler2.sendEmptyMessage(HANDLER_WHAT_SHOW_TOAST);
						  }else{
							  if(version>=11){
//								  if(isFinalTemp){
									  // 新的版本
								  Log.i(TAG, "onReceiveParam: fTemap="+myapp.fTemp);
								  data[1]=0 ; // 改为恒温
								  data[3]= (byte) myapp.fTemp; // 恒温温度45
								  blueToothLeService.writeDataByUUID(BlueToothUUID.UUID_PARAM, data);
							  }else{
								  if(isSerilized){
									  if (data[1]==1 ||data[1]==3) {
										  data[1]=(byte) 0 ;
									  }else{
										  data[1]=(byte) 1 ;
									  }
									  blueToothLeService.writeDataByUUID(BlueToothUUID.UUID_PARAM, data);
								  }
							  }
			               }
					}else{
						ToastUtils.show("请选用恒温奶瓶！");
					}
					  isSerilized=false;
					}
					  //接收机器序列号（不用）
					@Override
					public void onReceiveSerialNum(byte[] data, boolean haveData) {
						Log.i(TAG,"接收机器序列号");
						Log.i(TAG, "onReceiveSerialNum: "+ Arrays.toString(data));

						//TODO 2017-5-16 21:24
						//TODO cxiaox
						//读完电量开始读饮水/奶服务的记录
//						blueToothLeService.readDataByUUID(BlueToothUUID.UUID_FEED_SERVICE);
					}
				});
	  
			}else if(Constants.BROADCAST_WRITE_TIME.equals(action))
			{
				Log.i("wsq", "写入时间成功");
				//读取电量
				blueToothLeService.readDataByUUID(BlueToothUUID.UUID_BATT_POWER);
				
			}else if(Constants.BROADCAST_WRITE_PARAM.equals(action))
			{
				//开始扫描广播
				if(isConnected)
				{
					Log.i(TAG, "onReceive 396:  在这样主动断开蓝牙设置");
					blueToothLeService.disConnect();
					handler2.sendEmptyMessage(SCAN_BROADCAST);	
				}
              
			}
			else if(Constants.BROADCAST_START_FINAL_TEMP.equals(action)){
				if(blueToothLeService.initialize()){
					isFinalTemp = true; // 启动恒温

					boolean flag=OnConnectWithPd(bluetoothAddress);
					if(!flag)
					{
						if(pd!=null)
						{
							pd.setMessage("连接失败");
							pd.dismiss();
							isFinalTemp=false;
						}
					}

				}else{
					if(pd!=null)
					{
						pd.setMessage("初始化失败");
						pd.dismiss();
						isFinalTemp=false;
						handler2.removeCallbacks(runnable);
					}
				}


			}
			else if(Constants.BROADCAST_START_SERILIZE.equals(action))
			{
				//启动消毒
                   if(blueToothLeService.initialize()){
						isSerilized=true;
						boolean flag=OnConnectWithPd(bluetoothAddress);
		            	if(!flag)
		            	{
		            		if(pd!=null)
		            		{
								pd.setMessage("连接失败");
		            			pd.dismiss();
		            			isSerilized=false;
		            		}
		            	}
		            	
					}else{
						if(pd!=null)
	            		{
							pd.setMessage("初始化失败");
	            			pd.dismiss();
	            			isSerilized=false;
	            			handler2.removeCallbacks(runnable);
	            		}
					}

				//设置超时
                   handler2.postDelayed(runnable,10000);
                   
                   
			}else if(Constants.BROADCAST_STOP_SERILIZE.equals(action))
			{
				//停止消毒
	            if(blueToothLeService.initialize())
	            {
		            	isSerilized=true;
		            	boolean flag=OnConnectWithPd(bluetoothAddress);
		            	if(!flag)
		            	{
		            		if(pd!=null)
		            		{
								pd.setMessage("连接失败");
		            			pd.dismiss();
		            			isSerilized=false;
		            		}
		            	}
		            	
	            }else
	            {
	            	if(pd!=null)
            		{
						pd.setMessage("初始化失败");
            			pd.dismiss();
            			isSerilized=false;
            			handler2.removeCallbacks(runnable);
            		}
	            		
	            }

				//设置超时
                handler2.postDelayed(runnable,10000);
	            
	            
		    }else if(Constants.BROADCAST_REFRESH.equals(action))
		    {
				//刷新
		    	blueToothLeService.connect(bluetoothAddress);
		    	
		    }else if(Constants.BROADCAST_SNY.equals(action))
		    {
				//开始连接
	            if(blueToothLeService.initialize())
	            {
		            	boolean flag=OnConnectWithPd(bluetoothAddress);
		            	if(!flag)
		            	{
		            		if(pd!=null)
		            		{
								pd.setMessage("连接失败");
		            			pd.dismiss();
		            		}
		            	}
		            	
	            }else
	            {
	            	if(pd!=null)
            		{
						pd.setMessage("连接失败");
            			pd.dismiss();
            			handler2.removeCallbacks(runnable);
            		}
	            }

				//设置超时
                handler2.postDelayed(runnable,10000);
		    }
		}
	};



	//超时线程
	Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			if(pd!=null)
			{
				pd.setMessage("连接超时");
				pd.dismiss();
			}
		}
	};
	
	
	@Override
	protected void onResume() {
		super.onResume();

		//注册广播
		registerReceiver(gattUpdateReceiver, makeGattUpdateIntentFilter());
	}



	private MyApplication myapp ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myapp = (MyApplication) getApplication();
		mainActivity=this;
		//绑定服务
		Intent intent=new Intent(this,BlueToothLeService.class);
		bindService(intent, serviceConnection, BIND_AUTO_CREATE);
		
		BluetoothManager bluetoothManager=(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		bluetoothAdapter=bluetoothManager.getAdapter();
		
		initView();
	}
	
	
	public static MainActivity getInstance()
	{
		return mainActivity;
	}

	//初始化
	private void initView() {


		//侧滑菜单
		slidingMenu=new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);// 触摸边界拖出菜单

//		slidingMenu.setSecondaryMenu(R.layout.activity_main_left_menu);
		slidingMenu.setBehindOffsetRes(R.dimen.main_left_menu_offset);   //设置左侧menu拖出的距离
		// 设置渐入渐出效果的值
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.activity_main_left_menu);
		//列表菜单
		listMenu=(ListView)findViewById(R.id.list_left_menu);
		initMenuData();

		adapter=new MenuListAdapter(MainActivity.this );
		listMenu.setAdapter(adapter);
		listMenu.setOnItemClickListener(itemClickListener);
		adapter.notifyDataSetChanged();

		//顶部
		imgOpenMenu=(ImageView)findViewById(R.id.img_left_open);
		imgOpenMenu.setOnClickListener(this);
		//蓝牙图标
		imgLy=(ImageView)findViewById(R.id.img_ly);
		if(BlueToothUtils.isOpenBlueTooth(this))
		{
			imgLy.setImageResource(R.drawable.ly);
		}else
		{
			imgLy.setImageResource(R.drawable.ly2);
		}
		//电池图标
		imgDc=(ImageView)findViewById(R.id.img_dc);
		
		
		txtTittle=(TextView)findViewById(R.id.txt_title);
		imgHome=(ImageView)findViewById(R.id.img_home);
		imgHome.setOnClickListener(this);
		imgHeadBg=(ImageView)findViewById(R.id.img_center_bg);
		
		cimgHead=(CircleImageView)findViewById(R.id.img_center);
		String imgPath=this.getSharedPreferences("baby_data", Context.MODE_PRIVATE).getString("img_head_path", "");
		cimgHead.setImageBitmap(BitmapFactory.decodeFile(imgPath));
		
		topLayout=(RelativeLayout)findViewById(R.id.layout_bg);
		showTop1();
		
		alertSettingFragment=new AlertSettingFragment();
		growRecordFragment=new GrowRecordFragment();
		loveFragment=new LoveFragment();
		waterFragment=new WaterFragment();
		nurseFragment=new NurseFragment();
		babyDiaryFragment=new BabyDiaryFragment();
		dataFragment=new BabyDataFragment();
		tempFragment = new TempFragment();
		dataFragment.setHeadImgListener(new HeadImgListener() {
			
			@Override
			public void onImgPath(String path) {
				Bitmap bitmap=BitmapFactory.decodeFile(path);
				cimgHead.setImageBitmap(bitmap);
			}
		});
		
		deviceManagerFragment=new DeviceManagerFragment();
		deviceManagerFragment.setOnDeviceLister(new DeviceListener() {
			
			@Override
			public void onDevice(BluetoothDevice device) {
				//连接设备
				OnConnect(device);
				Log.i("wsq","mainactivity==onDevice");
				
			}
		});
		mainFragment=new MainFragment();
		
		fragments=new Fragment[]{mainFragment,dataFragment,growRecordFragment,babyDiaryFragment,
				alertSettingFragment,tempFragment,deviceManagerFragment,loveFragment,nurseFragment,waterFragment};
		FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
		if(!mainFragment.isAdded()&&mainFragment!=null)
		{
					ft.add(R.id.linear_Fragment, mainFragment,curFragmentTag);
					ft.show(mainFragment);
					ft.commit();
		}
		
		connectedBluetootheDataEitor=getSharedPreferences("device_data",Context.MODE_PRIVATE).edit();
		//宝贝资料共享文件
		babyDataEditor=getSharedPreferences("baby_data",Context.MODE_PRIVATE).edit();
		//时间戳共享文键
		countDownEditor=getSharedPreferences(Constants.COUNT_DOWN_TIME,Context.MODE_PRIVATE).edit();


		//判断蓝牙是否打开
		if(!BlueToothUtils.isOpenBlueTooth(this))
		{
			showOpenBLEDialog();
		}

		//开始闹铃
		AlertUtils.startAlertService(this);



		//注册广播
		registerBroadcast();
		

	}

	//打开蓝牙对话框
	private void showOpenBLEDialog() {

		CustomDialog.Builder builder=new CustomDialog.Builder(this);
		builder.setTitle(R.string.prompt);
		builder.setMessage(R.string.tip14);
		builder.setPositiveButton(R.string.setting,new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if(arg1==DialogInterface.BUTTON_POSITIVE)
				{
					Intent intent=new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
					startActivity(intent);
				}
			}
		});
		builder.setNegativeButton(R.string.cancel,null);	
	    builder.create().show();

	}


    OnItemClickListener itemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

			
			selectItem(arg2);
			
		}
	};


	//初始化菜单数据
	private void initMenuData() {
		menus=new ArrayList<Map<String,Integer>>();
		//主页
		Map<String,Integer> map_main=new HashMap<String,Integer>();
		map_main.put("img",R.drawable.icon_main);
		map_main.put("name",R.string.menu_main);
		menus.add(map_main);
		//宝贝资料
		Map<String,Integer> map_baby_data=new HashMap<String,Integer>();
		map_baby_data.put("img",R.drawable.icon_baby_data);
		map_baby_data.put("name",R.string.menu_baby_data);
		menus.add(map_baby_data);
		//成长记录
		Map<String,Integer> map_grow_record=new HashMap<String,Integer>();
		map_grow_record.put("img",R.drawable.icon_grow_record);
		map_grow_record.put("name",R.string.menu_grow_record);
		menus.add(map_grow_record);
		//宝宝日记
		Map<String,Integer> map_baby_diary=new HashMap<String,Integer>();
		map_baby_diary.put("img",R.drawable.icon_baby_diary);
		map_baby_diary.put("name",R.string.menu_baby_diary);
		menus.add(map_baby_diary);
		//提醒设置
		Map<String,Integer> map_alert_setting=new HashMap<String,Integer>();
		map_alert_setting.put("img",R.drawable.icon_alert_setting);
		map_alert_setting.put("name",R.string.menu_alert_setting);
		menus.add(map_alert_setting);

		//恒温设置
		Map<String,Integer> mapTemSetting=new HashMap<String,Integer>();
		map_alert_setting.put("img",R.drawable.icon_alert_setting);
		map_alert_setting.put("name",R.string.menu_tem_setting);
		menus.add(mapTemSetting);

		//设备管理
		Map<String,Integer> map_device_manager=new HashMap<String,Integer>();
		map_device_manager.put("img",R.drawable.icon_device_manager);
		map_device_manager.put("name",R.string.menu_device_manager);
		menus.add(map_device_manager);
		
	}

	//页面选择
	public void selectItem(int arg2) {

		index=arg2;
		fragment=null;
		switch (arg2) {
		case 0:
			fragment=mainFragment;
            showTop1();
			break;

		case 1:
			fragment=dataFragment;
			showTop2(R.string.menu_baby_data);

			break;
		case 2:
			fragment=growRecordFragment;
			showTop2(R.string.menu_grow_record);

			break;
		case 3:
			fragment=babyDiaryFragment;
			showTop2(R.string.menu_baby_diary);
			break;
		case 4:
			fragment=alertSettingFragment;
			showTop2(R.string.menu_alert_setting);
			break;
		case 5:
			fragment = tempFragment;
			showTop2(R.string.menu_tem_setting);
			break;
		case 6:
			fragment=deviceManagerFragment;
			showTop2(R.string.menu_device_manager);
			break;

		case 7:
			fragment=loveFragment;
			showTop2(R.string.love);
			break;
		case 8:
			fragment=nurseFragment;
			showTop2(R.string.nurse);
			break;
		case 9:
			fragment=waterFragment;
			showTop2(R.string.water);
			break;

		}




		if(fragment!=null)
		{
			if(arg2!=7&&arg2!=8&&arg2!=9)
			{
				 slidingMenu.toggle(true);
			}
			adapter.changeState(arg2,true);
			Message message=new Message();
			message.obj=fragment;
			handler.sendMessage(message);
		}
		
		
	}

	//切换fragment的handler
	Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			if(curIndex!=index)
			{
				FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
				
				ft.hide(fragments[curIndex]);
				if (!fragments[index].isAdded()) {
					ft.add(R.id.linear_Fragment,fragments[index],curFragmentTag);
				}
				ft.show(fragments[index]).commit();
			}
			curIndex=index;
			
		};
	};


	//
	private int HANDLER_WHAT_SHOW_TOAST=10;
	//更新UI
	Handler handler2=new Handler()
	{
		public void handleMessage(Message msg) {
			if(msg.what==1)
			{
				imgDc.setImageResource(R.drawable.dc1);
			}else if(msg.what==2)
			{
				imgDc.setImageResource(R.drawable.dc2);
				
			}else if(msg.what==3)
			{
				imgDc.setImageResource(R.drawable.dc3);
				
			}else if(msg.what==SCAN_BROADCAST)
			{
				//开始扫描广播
				if(!isScan)
				{
					 onScan();
					 isScan=true;
				}
				    
			}else if(msg.what==HANDLER_WHAT_SHOW_TOAST){
				ToastUtils.show("奶瓶或水瓶没有插电，插电后才能加热");
			}

			
			
		};
	};

	//menu开关
	public void menuToggle()
	{
		slidingMenu.toggle();
	}



	//注册广播
	public void registerBroadcast()
	{
			IntentFilter filter=new IntentFilter();
			filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
			registerReceiver(receiver, filter);
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.img_left_open:
			menuToggle();
			break;

		case R.id.img_home:
			index=0;
			fragment=mainFragment;
			adapter.changeState(0,true);
			Message message=new Message();
			message.obj=fragment;
			handler.sendMessage(message);
			showTop1();
			break;
		}
	}
	
	//show top1
	public void showTop1()
	{
		topLayout.setBackgroundResource(R.drawable.main_top);
		imgHome.setVisibility(View.INVISIBLE);
		txtTittle.setVisibility(View.GONE);
		imgHeadBg.setVisibility(View.VISIBLE);
		cimgHead.setVisibility(View.VISIBLE);
	}

	public void showTop2(int tittle)
	{
		topLayout.setBackgroundResource(R.drawable.main_top2);
		imgHome.setVisibility(View.VISIBLE);
		txtTittle.setVisibility(View.VISIBLE);
		txtTittle.setText(tittle);
		imgHeadBg.setVisibility(View.INVISIBLE);
		cimgHead.setVisibility(View.INVISIBLE);
	}
	
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		
		Fragment fragment=getSupportFragmentManager().findFragmentByTag(curFragmentTag);
		if(fragment!=null)
		{
			Log.i("wsq","main--onActivityResult");
		}
		fragment.onActivityResult(arg0, arg1, arg2);
		
	}
	

	@Override
	protected void onDestroy() {

		unregisterReceiver(receiver);
		unregisterReceiver(gattUpdateReceiver);
		unbindService(serviceConnection);
		isScan=false;
		blueToothLeService=null;	
		super.onDestroy();
	}


	//设置广播过滤
	private static IntentFilter makeGattUpdateIntentFilter() 
	{
		    final IntentFilter intentFilter = new IntentFilter();
		    intentFilter.addAction(Constants.ACTION_GATT_CONNECTED);
		    intentFilter.addAction(Constants.ACTION_GATT_SERVICES_DISCOVERED);
		    intentFilter.addAction(Constants.BROADCAST_WRITE_TIME);
		    intentFilter.addAction(Constants.BROADCAST_START_SERILIZE);
		    intentFilter.addAction(Constants.BROADCAST_STOP_SERILIZE);
		    intentFilter.addAction(Constants.BROADCAST_WRITE_PARAM);
		    intentFilter.addAction(Constants.BROADCAST_REFRESH);
		    intentFilter.addAction(Constants.BROADCAST_SNY);
		    intentFilter.addAction(Constants.BROADCAST_START_FINAL_TEMP);
            return intentFilter;
    }



	//连接设备
	ProgressDialog pd;
	protected void OnConnect(BluetoothDevice device) {

		Log.i("wsq","mainActivity_device==连接设备");
		bluetoothDevice=device;
		bluetoothAddress=device.getAddress();
		pd=new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("正在连接");
		pd.show();
		blueToothLeService.connect(device.getAddress());
			
	}
	
	protected void OnConnect(String address) {

		Log.i("wsq","mainActivity_address==连接设备");
		bluetoothAddress=address;
		blueToothLeService.connect(address);
			
	}
	
	private boolean  OnConnectWithPd(String address)
	{
		bluetoothAddress=address;
		pd=new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("正在连接");
		pd.show();
		return blueToothLeService.connect(address);
	}

	//扫描广播
	protected void onScan() {

		new Thread()
		{
			public void run() {
				
				while(true)	
			   {
					
					if(isScan)
					{
						
						synchronized (ACCESSIBILITY_SERVICE) 
						{
							 bluetoothAdapter.stopLeScan(leScanCallback);
							 bluetoothAdapter.startLeScan(leScanCallback);
						}
					   
					}
					
					try {
					   Thread.sleep(1000);
				     } catch (InterruptedException e) {

					    e.printStackTrace();
				    }
			   }
			
			};
		}.start();
	}

	//扫描
	private BluetoothAdapter.LeScanCallback leScanCallback=new LeScanCallback() {
		
		@Override
		public void onLeScan(BluetoothDevice device, int arg1, byte[] scanRecord) {
			if(bluetoothAddress.equals(device.getAddress()))
			{
			     bluetoothAdapter.stopLeScan(this);
				//温度
				String hex=Integer.toHexString((scanRecord[5]&0xff));
				String hex1=Integer.toHexString((scanRecord[6]&0xff));
				//地址
				String hex2=Integer.toHexString((scanRecord[7]&0xff));
			    String hex3=Integer.toHexString((scanRecord[8]&0xff));
			    String hex4=Integer.toHexString((scanRecord[9]&0xff));

				Log.i(TAG, "onLeScan: mac 2="+hex2);
				Log.i(TAG, "onLeScan: mac 3="+hex3);
				Log.i(TAG, "onLeScan: mac 4="+hex4);

			    int tem1=scanRecord[5]&0xff;
			    int tem2=scanRecord[6]&0xff;
			    int temp=tem1*255+tem2;
				//模式
				int model=scanRecord[10];
				//状态
				int bit0=scanRecord[11]&0x01; //0停止，1加热
				int bit1=(scanRecord[11]&(1<<0x01))>>1;//0未插电，1已插电
				int bit2=(scanRecord[11]&(2<<0x01))>>2;//0恒温状态，1未达到恒温
				//广播数据
			    Intent intent=new Intent(Constants.BROADCAST_TEMP);
			    intent.putExtra("temp",temp);
			    intent.putExtra("model",model);
			    intent.putExtra("bit0", bit0);
			    intent.putExtra("bit1", bit1);
			    intent.putExtra("bit2", bit2);
			    sendBroadcast(intent);
				Log.i(TAG, "onLeScan: "+"扫描广播=="+"温度="+temp+";模式"+model+";状态="+bit0+":"+bit1+":"+bit2);
			}
		}
	};
}
