package com.wsq.ebottle.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.wsq.ebittle.common.Constants;
import com.wsq.ebottle.R;
import com.wsq.ebottle.adapter.PopItemAdapter;
import com.wsq.ebottle.adapter.PopItemIntegerAdapter;
import com.wsq.ebottle.bean.DataTable;
import com.wsq.ebottle.bean.DateTable;
import com.wsq.ebottle.bean.Event;
import com.wsq.ebottle.db.dao.DataTableDao;
import com.wsq.ebottle.db.dao.DateTableDao;
import com.wsq.ebottle.db.dao.EventTableDao;
import com.wsq.ebottle.utils.AlertUtils;
import com.wsq.ebottle.utils.TimeUtils;
import com.wsq.ebottle.utils.ToastUtils;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class NurseFragment extends Fragment implements OnClickListener{
	
	private Button btnSyn;
	private TextView tvNurseStartDate;
	private EditText etNurseStartTime;
	private ToggleButton tbNurseStartTime;
	private ToggleButton tbHouqi;
	private RadioGroup rgHouqi;
	private RadioButton rb3Hour;
	private RadioButton rb3nHour;
	private RadioButton rb4Hour;
	//选择奶粉
	private EditText etMilkPower;
	private ToggleButton tbRecommand;
	//喝奶量
	private ImageButton ibNurseIntake;
	private TextView tvNurseIntake;
	private ListView listIntake;
	private LinearLayout layoutIntake;
	private ArrayList<Integer> items;
	PopupWindow popupWindow;
	boolean isShowPop=false;
	//结束时间
	private TextView tvNurseEndDate;
	private EditText etNurseEndTime;
	private ToggleButton tbNurseEndTime;
	//保存修改
	private Button btnSave;
	//平均温度
	private TextView txtAveTemp;
	private static int UPDATE_AVE_TEMP=1001;
	private static float aveTemp=0f;
	
	private int houqiSel;
	SharedPreferences.Editor editor;
	SharedPreferences.Editor countDownEditor;
	SharedPreferences preferences;

	int intakeSel=1;//

	//数据库
	DateTableDao dateTableDao;
	DataTableDao dataTableDao;


	//广播接收平均温度
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			String action=arg1.getAction();
			if(Constants.BROADCAST_AVETEMP.equals(action))
			{
			   aveTemp=arg1.getIntExtra("aveTemp",0);
				
				handler.sendEmptyMessage(UPDATE_AVE_TEMP);
				
			}
		}
	};


	//设置广播过滤
	private static IntentFilter makeGattUpdateIntentFilter() 
	{
			final IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(Constants.BROADCAST_AVETEMP);
	        return intentFilter;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) 
	{
		return inflater.inflate(R.layout.fragment_nurse, container,false);
		
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().registerReceiver(receiver, makeGattUpdateIntentFilter());
		initView();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(receiver);
		
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		//同步
		btnSyn=(Button)getActivity().findViewById(R.id.btn_syn);
		btnSyn.setOnClickListener(this);
		//喂奶时间
		tvNurseStartDate=(TextView)getActivity().findViewById(R.id.txt_nurse_start_date);
		etNurseStartTime=(EditText)getActivity().findViewById(R.id.edit_nurse_start_time);
		etNurseStartTime.setInputType(InputType.TYPE_NULL);
		etNurseStartTime.setOnClickListener(this);
		etNurseStartTime.setEnabled(false);
	
		
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy年MM月dd日-HH:mm");
		Date date=new Date(TimeUtils.getCurrentMill());
		String strDate=dateFormat.format(date);
		String[] strTemp=strDate.split("-");
		tvNurseStartDate.setText(strTemp[0]);
		etNurseStartTime.setText(strTemp[1]);
		
		tbNurseStartTime=(ToggleButton) getActivity().findViewById(R.id.toggle_nurse_start_time);
		tbNurseStartTime.setChecked(true);
		tbNurseStartTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1)
				{
					etNurseStartTime.setEnabled(false);
				}else
				{
					etNurseStartTime.setEnabled(true);
				}
			}
		});
		//后期喂奶
		tbHouqi=(ToggleButton)getActivity().findViewById(R.id.toggle_houqi);
		tbHouqi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
				{
					rb3Hour.setEnabled(true);
					rb3nHour.setEnabled(true);
					rb4Hour.setEnabled(true);
				}else
				{
					rb3Hour.setEnabled(false);
					rb3nHour.setEnabled(false);
					rb4Hour.setEnabled(false);
				}
			}
		});
		rgHouqi=(RadioGroup)getActivity().findViewById(R.id.rg_houqi);
		rgHouqi.setOnCheckedChangeListener(radioGChangeListener);
		rb3Hour=(RadioButton)getActivity().findViewById(R.id.radio_3_hour);
		rb3nHour=(RadioButton)getActivity().findViewById(R.id.radio_3_h_hour);
		rb4Hour=(RadioButton)getActivity().findViewById(R.id.radio_4_hour);
		rb3Hour.setEnabled(false);
		rb3nHour.setEnabled(false);
		rb4Hour.setEnabled(false);
		//选择奶粉
		etMilkPower=(EditText)getActivity().findViewById(R.id.edit_milk_powder);
		etMilkPower.setText("雅培");
		etMilkPower.setEnabled(false);
		tbRecommand=(ToggleButton)getActivity().findViewById(R.id.toggle_recommond);
		tbRecommand.setChecked(true);
		tbRecommand.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
				{
					etMilkPower.setEnabled(false);
				}else
				{
					etMilkPower.setEnabled(true);
				}
			}
		});

		//平均温度
		txtAveTemp=(TextView)getActivity().findViewById(R.id.txt_ave_temp);

		//喝奶量
		ibNurseIntake=(ImageButton)getActivity().findViewById(R.id.img_nurse_intake_arrow);
		ibNurseIntake.setOnClickListener(this);
		tvNurseIntake=(TextView)getActivity().findViewById(R.id.txt_nurse_intake);

		//结束时间
		tvNurseEndDate=(TextView)getActivity().findViewById(R.id.txt_nurse_end_date);
		etNurseEndTime=(EditText)getActivity().findViewById(R.id.edit_nurse_end_time);
		etNurseEndTime.setInputType(InputType.TYPE_NULL);
		etNurseEndTime.setOnClickListener(this);
		etNurseEndTime.setEnabled(false);
		
		tvNurseEndDate.setText(strTemp[0]);
		etNurseEndTime.setText(strTemp[1]);
		tbNurseEndTime=(ToggleButton)getActivity().findViewById(R.id.toggle_nurse_end_time);
		tbNurseEndTime.setChecked(true);
		tbNurseEndTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
				{
					etNurseEndTime.setEnabled(false);
				}else
				{
					etNurseEndTime.setEnabled(true);
				}
				
			}
		});
		btnSave=(Button)getActivity().findViewById(R.id.btn_save_update);
		btnSave.setOnClickListener(this);

		//共享文件
		editor=getActivity().getSharedPreferences("alert_setting",Context.MODE_PRIVATE).edit();
		
		countDownEditor=getActivity().getSharedPreferences(Constants.COUNT_DOWN_TIME,Context.MODE_PRIVATE).edit();
		preferences=getActivity().getSharedPreferences(Constants.COUNT_DOWN_TIME,Context.MODE_PRIVATE);
		//数据库
         dateTableDao=new DateTableDao(getActivity());
         dataTableDao=new DataTableDao(getActivity());
		
		initPopItem();
		
	}
	
	android.widget.RadioGroup.OnCheckedChangeListener  radioGChangeListener=new android.widget.RadioGroup.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			switch (arg1) {
			case R.id.radio_3_hour:
				houqiSel=3;
				//ToastUtils.show(houqiSel+"");
				break;
			case R.id.radio_3_h_hour:
				houqiSel=4;
				//ToastUtils.show(houqiSel+"");
				break;
			case R.id.radio_4_hour:
				houqiSel=5;
				//ToastUtils.show(houqiSel+"");
				break;
			}
		}
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) 
		{
		case R.id.btn_syn:
			onSyn();
			break;

		case R.id.img_nurse_intake_arrow:
			if(isShowPop)
			{
				if(popupWindow!=null)
				{
					popupWindow.dismiss();
					isShowPop=false;
				}
			}else
			{
				onShowPopupWindow();
			}
			break;
		case R.id.btn_save_update:
			onSave();
			break;
		case R.id.edit_nurse_start_time:
			onStartTimePick();
			break;
		case R.id.edit_nurse_end_time:
			onEndTimePick();
			break;
		}
	}

	//开始时间选择
	   private void onStartTimePick() {
			// TODO Auto-generated method stub
			Calendar calendar=Calendar.getInstance();
			int hour=calendar.HOUR_OF_DAY;
			int minute=calendar.MINUTE;
		    TimePickerDialog timePickerDialog=new TimePickerDialog(getActivity(), new OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker arg0, int arg1, int arg2) {
					// TODO Auto-generated method stub
					String str=String.format("%d:%d",arg1,arg2);
					etNurseStartTime.setText(str);
				}
			}, hour, minute, true);
		    timePickerDialog.show();
	   }

	//结束时间选择
	   private void onEndTimePick() {
			// TODO Auto-generated method stub
		   Calendar calendar=Calendar.getInstance();
			int hour=calendar.HOUR_OF_DAY;
			int minute=calendar.MINUTE;
		    TimePickerDialog timePickerDialog=new TimePickerDialog(getActivity(), new OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker arg0, int arg1, int arg2) {
					// TODO Auto-generated method stub
					String str=String.format("%d:%d",arg1,arg2);
					etNurseEndTime.setText(str);
				}
			}, hour, minute, true);
		    timePickerDialog.show();
		}

	//弹出窗口
	private void onShowPopupWindow() {
		// TODO Auto-generated method stub
		layoutIntake=(LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_popup_water_intake,null);
		listIntake=(ListView)layoutIntake.findViewById(R.id.list_pup);
		
		listIntake.setAdapter(new PopItemIntegerAdapter(getActivity(), items));
		popupWindow=new PopupWindow();
		popupWindow.setWidth(tvNurseIntake.getWidth());
		popupWindow.setHeight(200);
		popupWindow.setOutsideTouchable(true);
	    popupWindow.setFocusable(false);
		// 设置所在布局
	    popupWindow.setContentView(layoutIntake);
	    popupWindow.showAsDropDown(tvNurseIntake,0,0);
	    isShowPop=true;
	    listIntake.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				tvNurseIntake.setText(items.get(arg2)+"ML");
				intakeSel=arg2;
				if(popupWindow!=null)
				{
					popupWindow.dismiss();
					isShowPop=false;
				}
			}
		});
		
	}

	private void initPopItem() {
		// TODO Auto-generated method stub
		items=new ArrayList<Integer>();
		for(int i=1;i<=16;i++)
		{
			items.add(i*10);
		}
	}

	//更新UI
	Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			if(msg.what==UPDATE_AVE_TEMP)
			{
				txtAveTemp.setText(aveTemp+"");
			}
			
			
		};
	};

	//同步
	private void onSyn() {
		//连接
		Intent intent=new Intent(Constants.BROADCAST_SNY);
		getActivity().sendBroadcast(intent);
	}

	 private void onSave() {
		 if(!preferences.getBoolean("isStart",false))
		 {
			 if(houqiSel!=0)
			 {
				 editor.putInt("nurse_alert_time",houqiSel);
				 if(editor.commit())
				 {
					 //开启闹铃
					  AlertUtils.startAlertService(getActivity());
					 ToastUtils.show("闹铃保存成功!");
				 }
			 }else
			 {
				 
			 }
			 
		 }else
		 {
			 ToastUtils.show("闹铃保存失败!");
		 }
		 //aveTemp=35.6f;
		 if(aveTemp==0)
		 {
			 ToastUtils.show("还没有饮水记录无法保存");
			 return;
		 }

		 //计算开始时间跟结束时间差
		 String[] startStr=etNurseStartTime.getText().toString().split(":");
		 int startHour=Integer.parseInt(startStr[0]);
		 int startMinute=Integer.parseInt(startStr[1]);
		 
		 String[] endStr=etNurseEndTime.getText().toString().split(":");
		 int endHour=Integer.parseInt(endStr[0]);
		 int endMinute=Integer.parseInt(endStr[1]);
		 
		 int lastTime=(endHour*60+endMinute)-(startHour*60+startMinute);
		 //ToastUtils.show(""+lastTime);
		 if(lastTime<0)
		 {
			 ToastUtils.show("开始时间不得晚于结束时间！");
			 return;
		 }


		 //保存日期
		    DateTable dateTable=new DateTable();
		    dateTable.setDate(tvNurseStartDate.getText().toString());
		    dateTable.setMilkPowder(etMilkPower.getText().toString());
		    dateTable.setWeek(TimeUtils.getWeekNum());
		    long index=dateTableDao.insertDate(dateTable);
		    int intake=items.get(intakeSel); 
		    int dateId=0;
		    if(index==0)
		    {
		    	dateId=dateTableDao.QueryIdByDate(tvNurseStartDate.getText().toString());
		    }else
		    {
		    	dateId=(int)index;
		    }
		    
		DataTable dataTable=new DataTable();
		dataTable.setDateId(dateId);
		dataTable.setIntake(intake);
		dataTable.setTemp(aveTemp);
		dataTable.setTime(etNurseStartTime.getText().toString());
		dataTable.setType(1); //1代表奶瓶
		long index2=dataTableDao.insertData(dataTable);
		 if(index2>0)
		 {
			 ToastUtils.show("保存成功");
		 }else
		 {
			 ToastUtils.show("保存失败");
		 }
		 //保存事件
		 Event event=new Event();
		 event.setDot(0);//0代表喂奶
		 String month=tvNurseStartDate.getText().toString().substring(5, 7);
		 String day=tvNurseStartDate.getText().toString().substring(8, 10);
		 String time=etNurseStartTime.getText().toString();
		 event.setTime(month+"-"+day+" "+time);
		 event.setDes("喂宝宝喝奶"+intake+"ml,"+"用了"+lastTime+"分钟");
		 new EventTableDao(getActivity()).insertEvent(event);
	}
}
