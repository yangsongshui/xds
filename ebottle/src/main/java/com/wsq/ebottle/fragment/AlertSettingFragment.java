package com.wsq.ebottle.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.wsq.ebittle.common.Constants;

import com.wsq.ebottle.R;
import com.wsq.ebottle.adapter.PopItemAdapter;
import com.wsq.ebottle.bean.Event;
import com.wsq.ebottle.broadcast.CountDownReceiver;
import com.wsq.ebottle.db.dao.EventTableDao;
import com.wsq.ebottle.service.AlertService;

import com.wsq.ebottle.utils.AlertUtils;
import com.wsq.ebottle.utils.TimeUtils;
import com.wsq.ebottle.utils.ToastUtils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ImageButton;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import android.widget.TextView;

public class AlertSettingFragment extends Fragment implements OnClickListener{
	 
	//记录喂奶提醒的选择
	private int nurAlertTimeSel=0;
	private int nurAlertStyleSel=0;
	private int nurAlertRingSel=0;
	//记录温度提醒的选择
	private int tempSettingSel=0;
	private int tempAlertStyleSel=0;
	private int tempAlertRingSel=0;
	//当前设置的温度
	private static int curTemp=0;
	
	SharedPreferences.Editor editor=null;
	
	private static AlertService alertService=null;
	SharedPreferences preferences=null;
	private int tempResId=0;
	
	//提醒时间
	private TextView txtAlertTime;
	private ImageButton imgAlertTime;
	private PopupWindow pwAlertTime;
	private ArrayList<String> alertTimeItems;
	boolean isShow1=false;
	
	//提醒方式
	private TextView txtAlertStyle;
	private ImageButton imgAlertStyle;
	private PopupWindow pwAlertStyle;
	private ArrayList<String> alertStyleItems;
	boolean isShow2=false;
	
	//提醒铃声
	private TextView txtNurseRing;
	private ImageButton imgNurseRing;
	private PopupWindow pwNurseRing;
	private ArrayList<String> nurseRingItems;
	boolean isShow3=false;
	
	//温度设置
	private TextView txtTempSetting;
	private ImageButton imgTempSetting;
	private PopupWindow pwTempSetting;
	private ArrayList<String> tempSettingItems;
	boolean isShow4=false;
	
	//温度提醒方式
	private TextView txtTempAlertStyle;
	private ImageButton imgTempAlertStyle;
	private PopupWindow pwTempAlertStyle;
	private ArrayList<String> tempAlertStyleItems;
	boolean isShow5=false;
	//温度提醒铃声
	private TextView txtTempAlertRing;
	private ImageButton imgTempAlertRing;
	private PopupWindow pwTempAlertRing;
	private ArrayList<String> tempAlertRingItems;
	boolean isShow6=false;
	
	//弹出喂奶时间到提示窗口
	private PopupWindow nurPopupWindow;
	boolean isShowNurseAlert=false;
	LinearLayout layoutParent;
	String remindTime;
	private static int UPDATE_TIME_SETTIMG_EN=2001;
	private static int UPDATE_TIME_SETTIMG_NO=2002;
	
	//记录时间戳
	SharedPreferences.Editor countDownEditor;
	SharedPreferences preferences2;

	//接收广播
	BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			String action=arg1.getAction();
			if(Constants.BROADCAST_ONTICKING.equals(action))
			{
				
				remindTime=arg1.getStringExtra("time");
				handler.sendEmptyMessage(UPDATE_TIME_SETTIMG_NO);
				
				
			}else if(Constants.BROADCAST_ONTICK_FINISH.equals(action))
			{
				//倒计时时间到
				AlertUtils.stopAlertService(getActivity());
				countDownEditor.putBoolean("isStart",false);
				countDownEditor.commit();
				 
				 if(!isShowNurseAlert)
				 {
					 Intent intent=new Intent("com.wsq.ebottle.service.action.AlertService");
					 getActivity().startService(intent);
					 onShowAlertPopupWondow(); 
				 }
				 handler.sendEmptyMessage(UPDATE_TIME_SETTIMG_EN);
				//保存时间
				 onSaveEvent();
			}
		}
	};
	
	private static IntentFilter makeGattUpdateIntentFilter() 
	{
		IntentFilter filter=new IntentFilter();
		filter.addAction(Constants.BROADCAST_TEMP);
		filter.addAction(Constants.BROADCAST_ONTICKING);
		filter.addAction(Constants.BROADCAST_ONTICK_FINISH);
		return filter;
	}

	//弹出倒计时时间到窗口
	protected void onShowAlertPopupWondow() {
		// TODO Auto-generated method stub
		LinearLayout layout=(LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_text, null);
		LinearLayout layoutText=(LinearLayout) layout.findViewById(R.id.layout_tip);
		layoutText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(nurPopupWindow!=null)
				{
					nurPopupWindow.dismiss();
					isShowNurseAlert=false;
					editor.putInt("nurse_alert_time", 0);
					//editor.putInt("nurse_alert_style", 2);
					editor.commit();
					//停止后台服务
					Intent intent=new Intent("com.wsq.ebottle.service.action.AlertService");
					getActivity().stopService(intent);
					handler.removeCallbacks(runnable);
					//刷新界面
					initView();
					
				}
			}
		});
		TextView tvTip=(TextView)layout.findViewById(R.id.textView1);
		tvTip.setText("妈妈，喂奶的时间到了");
		nurPopupWindow=new PopupWindow();
		nurPopupWindow.setWidth(400);
		nurPopupWindow.setHeight(60);
		nurPopupWindow.setOutsideTouchable(true);
		nurPopupWindow.setFocusable(false);
		// 设置所在布局
		nurPopupWindow.setContentView(layout);
		nurPopupWindow.showAtLocation(layoutParent,Gravity.CENTER_HORIZONTAL, 0,-50);
		isShowNurseAlert=true;
		//超时三十秒自动关闭后台服务
		handler.postDelayed(runnable,30000);
		
		
	}

	Runnable runnable=new Runnable() {
		
		@Override
		public void run() {

			Intent intent=new Intent("com.wsq.ebottle.service.action.AlertService");
			getActivity().stopService(intent);
			
		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_alert_setting, container, false);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		getActivity().registerReceiver(receiver, makeGattUpdateIntentFilter());
		initItems();
        initView();
	}


	
	private void initView() {
		editor=getActivity().getSharedPreferences("alert_setting", Context.MODE_PRIVATE).edit();
		preferences=getActivity().getSharedPreferences("alert_setting", Context.MODE_PRIVATE);
		countDownEditor=getActivity().getSharedPreferences(Constants.COUNT_DOWN_TIME,Context.MODE_PRIVATE).edit();
		preferences2=getActivity().getSharedPreferences(Constants.COUNT_DOWN_TIME,Context.MODE_PRIVATE);

		//提醒时间
		txtAlertTime=(TextView)getActivity().findViewById(R.id.txt_alert_time);
		imgAlertTime=(ImageButton)getActivity().findViewById(R.id.img_alert_time_arrow);
		imgAlertTime.setOnClickListener(this);
		//提醒方式
		txtAlertStyle=(TextView)getActivity().findViewById(R.id.txt_nurse_style);
		imgAlertStyle=(ImageButton)getActivity().findViewById(R.id.img_nurse_style_arrow);
		imgAlertStyle.setOnClickListener(this);

		//喂奶提醒铃声
		txtNurseRing=(TextView)getActivity().findViewById(R.id.txt_nurse_ring);
		imgNurseRing=(ImageButton)getActivity().findViewById(R.id.img_nurse_ring_arrow);
		imgNurseRing.setOnClickListener(this);

		//温度设置
		txtTempSetting=(TextView)getActivity().findViewById(R.id.txt_temp_setting);
		imgTempSetting=(ImageButton)getActivity().findViewById(R.id.img_temp_setting_arrow);
		imgTempSetting.setOnClickListener(this);
		//温度提醒方式
		txtTempAlertStyle=(TextView)getActivity().findViewById(R.id.txt_temp_alert_style);
		imgTempAlertStyle=(ImageButton)getActivity().findViewById(R.id.img_temp_alert_style_arrow);
		imgTempAlertStyle.setOnClickListener(this);
		//温度提醒铃声
		txtTempAlertRing=(TextView)getActivity().findViewById(R.id.txt_temp_alert_ring);
		imgTempAlertRing=(ImageButton)getActivity().findViewById(R.id.img_temp_alert_ring_arrow);
		imgTempAlertRing.setOnClickListener(this);

		//发一次广播更新时间
		Intent intent=new Intent(getActivity(),CountDownReceiver.class);
		intent.setAction("com.wsq.ebottle");
		getActivity().sendBroadcast(intent);

		txtAlertTime.setText(alertTimeItems.get(preferences.getInt("nurse_alert_time", 0)));
		txtAlertStyle.setText(alertStyleItems.get(preferences.getInt("nurse_alert_style",0)));
		txtNurseRing.setText(nurseRingItems.get(preferences.getInt("nurse_alert_ring",0)));
		txtTempSetting.setText(tempSettingItems.get(preferences.getInt("temp_setting",0)));
		txtTempAlertStyle.setText(tempAlertStyleItems.get(preferences.getInt("temp_alert_style",0)));
		txtTempAlertRing.setText(tempAlertRingItems.get(preferences.getInt("temp_alert_ring",0)));
        handler.sendEmptyMessage(1);
        
        layoutParent=(LinearLayout)getActivity().findViewById(R.id.layout_parent);
       
	}

	//初始化数据
	private void initItems() {
		//提醒时间
		if(alertTimeItems==null)
		{
			  alertTimeItems=new ArrayList<String>();
			 String[] strItem=getActivity().getResources().getStringArray(R.array.array_nurse_alert_time);
			  for(String str:strItem)
			  {
				  alertTimeItems.add(str);
			  }
		}

		//提醒方式
		  if(alertStyleItems==null)
		  {
			  alertStyleItems=new ArrayList<String>();
			  String[] strItem2=getActivity().getResources().getStringArray(R.array.array_nurse_alert_way);
			  for(String str:strItem2)
			  {
				  alertStyleItems.add(str);
			  }
		  }
		//喂奶提醒铃声
		  if(nurseRingItems==null)
		  {
			  nurseRingItems=new ArrayList<String>();
			  String[] strItems3=getActivity().getResources().getStringArray(R.array.array_nurse_ring);
			  for(String str:strItems3)
			  {
				  nurseRingItems.add(str);
			  }
		  }

		//温度设置
		  if(tempSettingItems==null)
		  {
			  tempSettingItems=new ArrayList<String>();
			  String[] strItems4=getActivity().getResources().getStringArray(R.array.array_temp_setting);
			  for(String str:strItems4)
			  {
				  tempSettingItems.add(str);
			  }
		  }
		//温度提醒方式
		  if(tempAlertStyleItems==null)
		  {
			  tempAlertStyleItems=new ArrayList<String>();
			  String[] strItems5=getActivity().getResources().getStringArray(R.array.array_nurse_alert_way);
			  for(String str:strItems5)
			  {
				  tempAlertStyleItems.add(str);
			  }
		  }
		//温度提醒铃声
		  if(tempAlertRingItems==null)
		  {
			  tempAlertRingItems=new ArrayList<String>();
			  String[] strItems6=getActivity().getResources().getStringArray(R.array.array_nurse_ring);
			  for(String str:strItems6)
			  {
				  tempAlertRingItems.add(str);
			  }
		  }
		  
	}



	//弹出窗口
		@SuppressLint("InflateParams") 
		private PopupWindow onShowPopupWindow(final TextView tv,final ArrayList<String> items)
		{
			LinearLayout layout=(LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_popup_water_intake,null);
			ListView list=(ListView)layout.findViewById(R.id.list_pup);
			final PopupWindow popupWindow=new PopupWindow();
			list.setAdapter(new PopItemAdapter(getActivity(),items));
			
			popupWindow.setWidth(tv.getWidth());
			popupWindow.setHeight(200);
			popupWindow.setOutsideTouchable(true);
		    popupWindow.setFocusable(false);
			// 设置所在布局
		    popupWindow.setContentView(layout);
		    popupWindow.showAsDropDown(tv,0,0);
		    list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					tv.setText(items.get(arg2));
					if(popupWindow!=null)
					{
						popupWindow.dismiss();
						if(tv.getId()==R.id.txt_alert_time)
						{
							isShow1=false;
							nurAlertTimeSel=arg2;
							nurseAlertSave();
						}else if(tv.getId()==R.id.txt_nurse_style)
						{
							isShow2=false;
							nurAlertStyleSel=arg2;
							nurseAlertSave();
							
						}else if(tv.getId()==R.id.txt_nurse_ring)
						{
							isShow3=false;
							nurAlertRingSel=arg2;
							nurseAlertSave();
							
						}else if(tv.getId()==R.id.txt_temp_setting)
						{
							isShow4=false;
							tempSettingSel=arg2;
							tempAlertSave();
						}else if(tv.getId()==R.id.txt_temp_alert_style)
						{
							isShow5=false;
							tempAlertStyleSel=arg2;
							tempAlertSave();
						}else if(tv.getId()==R.id.txt_temp_alert_ring)
						{
							isShow6=false;
							tempAlertRingSel=arg2;
							tempAlertSave();
						}
						
					}
					
				}
			});
			return popupWindow;
		}
	

	Handler handler=new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==UPDATE_TIME_SETTIMG_EN)
			{
				imgAlertTime.setClickable(true);
			}else if(msg.what==UPDATE_TIME_SETTIMG_NO)
			{
				txtAlertTime.setText(remindTime);
				imgAlertTime.setClickable(false);
			}
		}
	};

	//保存喂奶提醒设置
	protected void nurseAlertSave() {
		editor.putInt("nurse_alert_time", nurAlertTimeSel);
		editor.putInt("nurse_alert_style", nurAlertStyleSel);
		editor.putInt("nurse_alert_ring", nurAlertRingSel);
		if(editor.commit())
		{	 
			ToastUtils.show(getActivity().getString(R.string.save_success), Gravity.CENTER);
		}
		//开始设置定时器
		if(!preferences2.getBoolean("isStart",false))
		{
			AlertUtils.startAlertService(getActivity());
			countDownEditor.putLong("start_mill",TimeUtils.getCurrentMill());
			countDownEditor.putBoolean("isStart",true);
			countDownEditor.commit();
		}
	}


	//保存温度提醒设置
	protected void tempAlertSave() {
		editor.putInt("temp_setting",tempSettingSel);
		editor.putInt("temp_alert_style", tempAlertStyleSel);
		editor.putInt("temp_alert_ring",tempAlertRingSel);
		if(editor.commit())
		{
		    ToastUtils.show(getActivity().getString(R.string.save_success), Gravity.CENTER);
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		  case R.id.img_alert_time_arrow:
			if(!isShow1)  
			{
			   pwAlertTime=onShowPopupWindow(txtAlertTime,alertTimeItems);
			   isShow1=true;
			}else
			{
				if(pwAlertTime!=null)
					pwAlertTime.dismiss();
				isShow1=false;
			}
			  break;
		  case R.id.img_nurse_style_arrow:
			  if(!isShow2)  
				{
				   pwAlertStyle=onShowPopupWindow(txtAlertStyle,alertStyleItems);
				   isShow2=true;
				}else
				{
					if(pwAlertStyle!=null)
						pwAlertStyle.dismiss();
					isShow2=false;
				}
			  break;
		  case R.id.img_nurse_ring_arrow:
			  if(!isShow3)  
				{
				   pwNurseRing=onShowPopupWindow(txtNurseRing,nurseRingItems);
				   isShow3=true;
				}else
				{
					if(pwNurseRing!=null)
						pwNurseRing.dismiss();
					isShow3=false;
				}
			  break;
		  case R.id.img_temp_setting_arrow:
			  if(!isShow4)  
				{
				   pwTempSetting=onShowPopupWindow(txtTempSetting,tempSettingItems);
				   isShow4=true;
				}else
				{
					if(pwTempSetting!=null)
						pwTempSetting.dismiss();
					isShow4=false;
				}
			  break;
		  case R.id.img_temp_alert_style_arrow:
			  if(!isShow5)  
				{
				   pwTempAlertStyle=onShowPopupWindow(txtTempAlertStyle,tempAlertStyleItems);
				   isShow5=true;
				}else
				{
					if(pwTempAlertStyle!=null)
						pwTempAlertStyle.dismiss();
					isShow5=false;
				}
			  break;
		  case R.id.img_temp_alert_ring_arrow:
			  if(!isShow6)  
				{
				   pwTempAlertRing=onShowPopupWindow(txtTempAlertRing,tempAlertRingItems);
				   isShow6=true;
				}else
				{
					if(pwTempAlertRing!=null)
						pwTempAlertRing.dismiss();
					isShow6=false;
				}
			  break;
		}
	}
	
	
	
	@Override
	public void onDestroy() 
	{

		super.onDestroyView();
		
		
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {

		super.onHiddenChanged(hidden);
		if(!hidden)
		{
			initView();
		}
		
	}

	//保存事件
	protected void onSaveEvent() {
		//保存事件
		 Event event=new Event();
		 event.setDot(2);//1代表喂奶
		 SimpleDateFormat df=new SimpleDateFormat("MM-dd HH:mm");
		 Date date=new Date(TimeUtils.getCurrentMill());
		 event.setTime(df.format(date));
		 event.setDes("为宝宝喂奶时间要到了,请准备");
		 new EventTableDao(getActivity()).insertEvent(event);
	}
	
}
