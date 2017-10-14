package com.wsq.ebottle.fragment;

import com.wsq.ebittle.common.Constants;
import com.wsq.ebottle.MainActivity;
import com.wsq.ebottle.R;
import com.wsq.ebottle.bean.StateMsg;
import com.wsq.ebottle.utils.BlueToothUtils;
import com.wsq.ebottle.utils.ToastUtils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Path.FillType;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

@SuppressLint("NewApi") public class MainFragment extends Fragment implements OnClickListener{



	//时刻关爱
	private ImageView imgXin;
	//喂奶
	private ImageView imgBottle;
	//水瓶
	private ImageView imgWater;
	//刷新
	private ImageView imgRefresh;
	private Animation animRefresh;
	private ImageView imgLight;

	private TextView txtTemp;
	private TextView txtState;
	private TextView txtStateTip;
	//消毒
	private Button btnSerilize;
	
	private static int UI_UPDATE_TEMP=1001;
	private static int WRITE_PARAM_SUCCESS=1002;
	
	private MediaPlayer mp=null;
	Vibrator vibrator=null;
	PopupWindow tvPWindow=null;
	boolean isShowPop=false;
	SharedPreferences preferences=null;
	SharedPreferences.Editor editor=null;
	
	LinearLayout layoutParent=null;

	//接收广播
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			String action=arg1.getAction();
			int temp=arg1.getIntExtra("temp",0);
			int model=arg1.getIntExtra("model",-1);
			if(Constants.BROADCAST_TEMP.equals(action))
			{
				
				Message message=new Message();
				message.arg1=temp;
				message.arg2=model;
				StateMsg state=new StateMsg();
				state.setBit0(arg1.getIntExtra("bit0", -1));
				state.setBit1(arg1.getIntExtra("bit1", -1));
				state.setBit2(arg1.getIntExtra("bit2", -1));
				message.obj=state;
				
				message.what=UI_UPDATE_TEMP;
				handler.sendMessage(message);
			}else if(Constants.BROADCAST_WRITE_PARAM.equals(action))
			{
				handler.sendEmptyMessage(WRITE_PARAM_SUCCESS);
				
			}else if(Constants.BROADCAST_STOP_REFRESH.equals(action))
			{
				if(imgRefresh!=null)
				{
					imgRefresh.clearAnimation();
				}
			}
		}
	};
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_main, container,false);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		
	}

	//初始化UI
	private void initView() {
		editor=getActivity().getSharedPreferences("alert_setting", Context.MODE_PRIVATE).edit();
		preferences=getActivity().getSharedPreferences("alert_setting", Context.MODE_PRIVATE);
		
		layoutParent=(LinearLayout)getActivity().findViewById(R.id.layout_parent);
		
		imgXin=(ImageView)getView().findViewById(R.id.img_xin);
		imgXin.setOnClickListener(this);
		
		imgBottle=(ImageView)getView().findViewById(R.id.img_bottle);
		imgBottle.setOnClickListener(this);
		
		imgWater=(ImageView)getView().findViewById(R.id.img_water);
		imgWater.setOnClickListener(this);
		
		imgRefresh=(ImageView)getView().findViewById(R.id.img_refresh);
		imgRefresh.setOnClickListener(this);
		animRefresh=AnimationUtils.loadAnimation(getActivity(), R.anim.loading_animation);
		
		txtTemp=(TextView)getActivity().findViewById(R.id.txt_temp);
		imgLight=(ImageView)getActivity().findViewById(R.id.img_light);
		txtState=(TextView)getActivity().findViewById(R.id.txt_state);
		txtStateTip=(TextView)getActivity().findViewById(R.id.txt_state_tip);
		
		btnSerilize=(Button)getActivity().findViewById(R.id.btn_sterilize);
		btnSerilize.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				//启动消毒
				if(btnSerilize.getText().toString().equals(getActivity().getString(R.string.start_sterilize)))
				{
					Intent intent=new Intent(Constants.BROADCAST_START_SERILIZE);
					getActivity().sendBroadcast(intent);
				}
				return true;
			}
		});
		
		
		btnSerilize.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				
				if(btnSerilize.getText().toString().equals(getActivity().getString(R.string.start_sterilize)))
				{
				   ToastUtils.show(getActivity().getResources().getString(R.string.tip17),Gravity.CENTER);
				   
				}else if(btnSerilize.getText().toString().equals(getActivity().getString(R.string.stop_sterilize)))
				{
					Intent intent=new Intent(Constants.BROADCAST_STOP_SERILIZE);
					getActivity().sendBroadcast(intent);
				}
						
			}
		});
		
		getActivity().registerReceiver(receiver, makeGattUpdateIntentFilter());
		
	}
	
	private static IntentFilter makeGattUpdateIntentFilter() 
	{
		IntentFilter filter=new IntentFilter();
		filter.addAction(Constants.BROADCAST_TEMP);
		filter.addAction(Constants.BROADCAST_WRITE_PARAM);
		filter.addAction(Constants.BROADCAST_STOP_REFRESH);
		return filter;
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId())
		{
		   case R.id.img_xin:
			  MainActivity.getInstance().selectItem(7);
			  break;
		  case R.id.img_bottle:
			  MainActivity.getInstance().selectItem(8);
			  break;
		  case R.id.img_water:
			  MainActivity.getInstance().selectItem(9);
			break;
		  case R.id.img_refresh:
			  onRefresh();
			  break;
		}
	}

	//刷新
	private void onRefresh() {

		imgRefresh.startAnimation(animRefresh);
		Intent intent=new Intent(Constants.BROADCAST_REFRESH);
		getActivity().sendBroadcast(intent);
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {

				handler.removeCallbacks(this);
				imgRefresh.clearAnimation();
			}
		}, 3000);
	}


	//UI更新
	Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {	
			if(msg.what==UI_UPDATE_TEMP)
			{
				float temp=msg.arg1/10.0f;
				if(msg.arg1>550)
				{
				    txtTemp.setText(">55");
				}else
				{
					txtTemp.setText(temp+"");
				}
				
				if(msg.arg1<320)
				{
					imgLight.setImageResource(R.drawable.light_purple);
				}else if(msg.arg1>400)
				{
					imgLight.setImageResource(R.drawable.light_red);
					txtStateTip.setText(R.string.tip15);
					
				}else 
				{
					imgLight.setImageResource(R.drawable.light_blue);
					txtStateTip.setText(R.string.tip16);
				}
			
				//ģʽ
				int model=msg.arg2;
				if(model==0)
				{
					txtState.setText(R.string.state_thermostat);
					int tempSetting=preferences.getInt("temp_setting",0);
					int settingTemp=0;
					if(tempSetting==1)
					{
						settingTemp=37;
					}else if(tempSetting==2)
				    {
						settingTemp=38;
					}else if(tempSetting==3)
					{
						settingTemp=39;
					}else if(tempSetting==4)
					{
						settingTemp=40;
					}else if(tempSetting==5)
					{
						settingTemp=41;
					}else 
					{
						return;
					}
					if(temp>settingTemp&&temp<(settingTemp+1))
					{
					   showPopAlert();
					}
					
				}else if(model==1)
				{
					txtState.setText(R.string.state_sterilizing);
				}else if(model==2)
				{
					txtState.setText(R.string.state_sterilized);
				}
				//״̬
				StateMsg stateMsg=(StateMsg) msg.obj;
				//Log.i("wsq","state="+stateMsg.getBit1());
				if(stateMsg.getBit1()==0)
				{
					txtState.setText(R.string.state_no_power);
					
				}else if(stateMsg.getBit1()==1)
				{
					
				}

			}else if(msg.what==WRITE_PARAM_SUCCESS)
			{
				if(btnSerilize.getText().toString().equals(getActivity().getString(R.string.start_sterilize)))
				{
				              btnSerilize.setText(R.string.stop_sterilize);
				}else
				{
					btnSerilize.setText(R.string.start_sterilize);
				}
			}
			
		};
	};


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		getActivity().unregisterReceiver(receiver);
		
		super.onDestroy();
	}

	//显示提醒
	protected void showPopAlert() {
		int tempAlertRingSel=preferences.getInt("temp_alert_ring", 0);
		int resId=0;//铃声资源
		if(tempAlertRingSel==0)
		{
			resId=R.raw.xiao;
			
		}else if(tempAlertRingSel==1)
		{
			resId=R.raw.cai;
		}
		else if(tempAlertRingSel==2)
		{
			resId=R.raw.duo;
		}
		else if(tempAlertRingSel==3)
		{
		   resId=R.raw.ling;	
		}
		else if(tempAlertRingSel==4)
		{
			resId=R.raw.shu;
		}
		int tempAlertStyleSel=preferences.getInt("temp_alert_style", 0);
		int style=0;//播放方式
		if(tempAlertStyleSel==0)
		{
			style=Constants.RING_VIB_TEXT;
		}else if(tempAlertStyleSel==1)
		{
			style=Constants.VIB_TEXT;
		}else if(tempAlertStyleSel==2)
		{
			style=Constants.ALL_OFF;
		}
		if(!isShowPop&&tempAlertStyleSel!=2)
		{
			//播放铃声
			playMusic(style,resId);
			//弹出窗口
			onPopupTextWindow();
		}
			
	}

	//弹出窗口
    private void onPopupTextWindow() {
		LinearLayout layout=(LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_text, null);
		LinearLayout layoutText=(LinearLayout) layout.findViewById(R.id.layout_tip);
		layoutText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(tvPWindow!=null)
				{
					tvPWindow.dismiss();
					isShowPop=false;
					setAllOff();
					editor.putInt("temp_alert_style", 2);
					editor.commit();
					
				}
			}
		});
		tvPWindow=new PopupWindow();
		tvPWindow.setWidth(400);
		tvPWindow.setHeight(60);
		tvPWindow.setOutsideTouchable(true);
		tvPWindow.setFocusable(false);
		// 设置所在布局
		tvPWindow.setContentView(layout);
		tvPWindow.showAtLocation(layoutParent,Gravity.CENTER_HORIZONTAL, 0,-50);
		isShowPop=true;
		
		
	}

	//播放铃声
	private void playMusic(int style,int resId) {
		if(style==Constants.RING_VIB_TEXT)
		{
			setAllOff();
			
		    mp=MediaPlayer.create(getActivity(), resId);
		    mp.start();
		    
		    vibrator=(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
			//等待1秒，震动2秒，等待1秒，震动3秒
			long[] pattern = {1000, 2000, 1000, 3000};
			//-1表示不重复, 如果不是-1, 比如改成1, 表示从前面这个long数组的下标为1的元素开始重复.
			vibrator.vibrate(pattern, 0);
		    

		    
		}else if(style==Constants.VIB_TEXT)
		{
			setAllOff();
			
			vibrator=(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
			//等待2000秒，震动0.5秒，等待3秒，震动0.5秒
			long[] pattern = {2000,500,3000,500};
			//-1表示不重复, 如果不是-1, 比如改成1, 表示从前面这个long数组的下标为1的元素开始重复.
			vibrator.vibrate(pattern, 0);
   
		}else if(style==Constants.ALL_OFF)
		{
			setAllOff();
		}
	}
	//关闭所有
	public void setAllOff()
	{
		if(mp!=null)
		{
			mp.stop();
			mp=null;
		}
		if(vibrator!=null)
		{
			vibrator.cancel();
			vibrator=null;
		}
	}
}
