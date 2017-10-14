package com.wsq.ebottle.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

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
import com.wsq.ebottle.utils.TimeUtils;
import com.wsq.ebottle.utils.ToastUtils;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class WaterFragment extends Fragment implements OnClickListener{

	private Button btnSyn;//同步
	private Button btnSave;//保存
	private TextView txtWaterStartDate;
	private EditText editWaterStartTime;
	private ToggleButton tbWaterStartTime;
	
	private TextView txtWaterEndDate;
	private EditText editWaterEndTime;
	private ToggleButton tbWaterEndTime;
	private TextView txtAveTemp;
	
	private ImageButton ibtnWaterIntake;
	private ListView listIntake;
	private LinearLayout layoutIntake;
	private ArrayList<Integer> items;
	PopupWindow popupWindow;
	boolean isShowPop=false;

	//饮水量
	private TextView tvWaterIntake;
	private static float aveTemp=0;
	int intakeSel=1;

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
		
	OnCheckedChangeListener changeListener1=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			// TODO Auto-generated method stub
			if(arg1)
			{
				
				editWaterStartTime.setEnabled(false);

			}else
			{
				editWaterStartTime.setEnabled(true);
			}
			
		}
	};
	
	OnCheckedChangeListener changeListener2=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			// TODO Auto-generated method stub
			if(arg1)
			{
				editWaterEndTime.setEnabled(false);
			}else
			{
				editWaterEndTime.setEnabled(true);
			}
		}
	};
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_water, container, false);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//注册广播
		getActivity().registerReceiver(receiver, makeGattUpdateIntentFilter());
		initView();
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		btnSyn=(Button)getActivity().findViewById(R.id.btn_syn);
		btnSyn.setOnClickListener(this);
		txtWaterStartDate=(TextView)getActivity().findViewById(R.id.txt_water_date);
		editWaterStartTime=(EditText)getActivity().findViewById(R.id.edit_water_time);
		editWaterStartTime.setInputType(InputType.TYPE_NULL);
		editWaterStartTime.setEnabled(false);
		editWaterStartTime.setOnClickListener(this);

		//设置日期
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=new Date(TimeUtils.getCurrentMill());
		String strDate=dateFormat.format(date);
		String[] strTemp=strDate.split("-");
		txtWaterStartDate.setText(strTemp[0]);
		editWaterStartTime.setText(strTemp[1]);
		
		tbWaterStartTime=(ToggleButton)getActivity().findViewById(R.id.toggle_water_time);
		tbWaterStartTime.setChecked(true);
		tbWaterStartTime.setOnCheckedChangeListener(changeListener1);
		
		txtWaterEndDate=(TextView)getActivity().findViewById(R.id.txt_water_end_date);
		editWaterEndTime=(EditText)getActivity().findViewById(R.id.edit_water_end_time);
		editWaterEndTime.setInputType(InputType.TYPE_NULL);
		editWaterEndTime.setEnabled(false);
		editWaterEndTime.setOnClickListener(this);

		txtWaterEndDate.setText(strTemp[0]);
		editWaterEndTime.setText(strTemp[1]);
		tbWaterEndTime=(ToggleButton)getActivity().findViewById(R.id.toggle_water_end_time);
		tbWaterEndTime.setChecked(true);
		tbWaterEndTime.setOnCheckedChangeListener(changeListener2);
		
		txtAveTemp=(TextView)getActivity().findViewById(R.id.txt_ave_temp);
		
		ibtnWaterIntake=(ImageButton)getActivity().findViewById(R.id.img_water_intake_arrow);
		ibtnWaterIntake.setOnClickListener(this);
		tvWaterIntake=(TextView)getActivity().findViewById(R.id.txt_water_intake);

		//初始化饮水量数据
		initPopItem();
		//保存
		btnSave=(Button)getActivity().findViewById(R.id.btn_save_update);
		btnSave.setOnClickListener(this);

		//数据库
		dateTableDao=new DateTableDao(getActivity());
		dataTableDao=new DataTableDao(getActivity());
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) 
		{
		case R.id.btn_syn:
			onSyn();//同步
			break;

		case R.id.btn_save_update:
			onSave();
			break;
		case R.id.img_water_intake_arrow:
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
		case R.id.edit_water_time:
			onStartTimePick();
			break;
		case R.id.edit_water_end_time:
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
				editWaterStartTime.setText(str);
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
				editWaterEndTime.setText(str);
			}
		}, hour, minute, true);
	    timePickerDialog.show();
	}

	//弹出窗口
	@SuppressLint("InflateParams") 
	private void onShowPopupWindow() {
		// TODO Auto-generated method stub
		layoutIntake=(LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_popup_water_intake,null);
		listIntake=(ListView)layoutIntake.findViewById(R.id.list_pup);
		
		listIntake.setAdapter(new PopItemIntegerAdapter(getActivity(), items));
		popupWindow=new PopupWindow();
		popupWindow.setWidth(tvWaterIntake.getWidth());
		popupWindow.setHeight(200);
		popupWindow.setOutsideTouchable(true);
	    popupWindow.setFocusable(false);
		// 设置所在布局
	    popupWindow.setContentView(layoutIntake);
	    popupWindow.showAsDropDown(tvWaterIntake,0,0);
	    isShowPop=true;
	    listIntake.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				tvWaterIntake.setText(items.get(arg2)+"ML");
				intakeSel=arg2;
				if(popupWindow!=null)
				{
					popupWindow.dismiss();
					isShowPop=false;
				}
			}
		});
		
	}

	public void initPopItem()
	{
		items=new ArrayList<Integer>();
		for(int i=1;i<=16;i++)
		{
			items.add(i*10);
		}
	}


	//同步操作
	private void onSyn() {
		//连接
	   Intent intent=new Intent(Constants.BROADCAST_SNY);
	   getActivity().sendBroadcast(intent);
	}

	//保存
	private void onSave() {
		  //aveTemp=41.3f;
		 if(aveTemp==0)
		 {
			 ToastUtils.show("还没有饮水记录无法保存");
			 return;
		 }

		//计算开始时间跟结束时间差
		 String[] startStr=editWaterStartTime.getText().toString().split(":");
		 int startHour=Integer.parseInt(startStr[0]);
		 int startMinute=Integer.parseInt(startStr[1]);
		 
		 String[] endStr=editWaterEndTime.getText().toString().split(":");
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
		    dateTable.setDate(txtWaterStartDate.getText().toString());
		    dateTable.setWeek(TimeUtils.getWeekNum());
		    long index=dateTableDao.insertDate(dateTable);
		    int intake=items.get(intakeSel); 
		    int dateId=0;
		    if(index==0)
		    {
		    	dateId=dateTableDao.QueryIdByDate(txtWaterStartDate.getText().toString());
		    }else
		    {
		    	dateId=(int)index;
		    }
		 
		    DataTable dataTable=new DataTable();
	    	dataTable.setDateId(dateId);
	    	dataTable.setIntake(intake);
	    	dataTable.setTemp(aveTemp);
	    	dataTable.setTime(editWaterStartTime.getText().toString());
	    	dataTable.setType(2); //2������ƿ
	    	long index2=dataTableDao.insertData(dataTable);
		if(index2>0)
		{
			ToastUtils.show("保存成功");

		}else
		{
			ToastUtils.show("保存失败");
		}
	    	Log.i("wsq","index="+index+";intake="+intake);



		//保存事件
		Event event=new Event();
		event.setDot(1);//1代表喂奶
		String month=txtWaterStartDate.getText().toString().substring(5, 7);
		String day=txtWaterStartDate.getText().toString().substring(8, 10);
		String time=editWaterStartTime.getText().toString();
		event.setTime(month+"-"+day+" "+time);
		event.setDes("喂宝宝饮水"+intake+"ml,"+"用了"+lastTime+"分钟");
		new EventTableDao(getActivity()).insertEvent(event);
	}

}
