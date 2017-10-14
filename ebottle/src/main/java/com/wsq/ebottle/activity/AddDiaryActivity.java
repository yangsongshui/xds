package com.wsq.ebottle.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wsq.ebittle.common.Constants;
import com.wsq.ebottle.R;
import com.wsq.ebottle.bean.Diary;
import com.wsq.ebottle.db.dao.DiaryTableDao;
import com.wsq.ebottle.utils.BlueToothUtils;
import com.wsq.ebottle.utils.TimeUtils;
import com.wsq.ebottle.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AddDiaryActivity extends Activity implements OnClickListener{

	private ImageView imgBack;
	private ImageView imgOk;
	private TextView txtTitle;
	private ImageView imgLy;
	private ImageView imgDc;
	
	private TextView txtDate;
	private TextView txtWeek;
	private TextView txtTime;
	
	private EditText editWeight;
	private EditText editMood;
	private EditText editDes;
	//数据插入标志
	long flag=0;

	SharedPreferences preferences;
	
	//广播
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			if(BlueToothUtils.isOpenBlueTooth(AddDiaryActivity.this))
			{
				imgLy.setImageResource(R.drawable.ly);

			}else
			{
				imgLy.setImageResource(R.drawable.ly2);
			}
		}
	};
	
	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_diary);
		
		//String str=getIntent().getStringExtra("wsq");
		//Log.i("wsq",str);
		
		initView();
	}
    

	private void initView() {

		preferences=getSharedPreferences("device_data",Context.MODE_PRIVATE);
		imgBack=(ImageView)findViewById(R.id.img_back);
		imgBack.setOnClickListener(this);
		
		imgOk=(ImageView)findViewById(R.id.img_ok);
		imgOk.setOnClickListener(this);
		txtTitle=(TextView)findViewById(R.id.txt_title);
		txtTitle.setText(R.string.mom_write_diary);
		imgLy=(ImageView)findViewById(R.id.img_ly);
		if(BlueToothUtils.isOpenBlueTooth(AddDiaryActivity.this))
		{
			imgLy.setImageResource(R.drawable.ly);

		}else
		{
			imgLy.setImageResource(R.drawable.ly2);
		}
		imgDc=(ImageView)findViewById(R.id.img_dc);
		int battery=preferences.getInt("battery",0);
		if(battery>=0&&battery<30)
		{
			imgDc.setImageResource(R.drawable.dc1);
		}else if(battery>=30&&battery<80)
		{
			imgDc.setImageResource(R.drawable.dc2);
			
		}else if(battery>=80&&battery<=100)
		{
			imgDc.setImageResource(R.drawable.dc3);
			
		}
		
		//设置日期
		txtDate=(TextView)findViewById(R.id.txt_date);
		txtWeek=(TextView)findViewById(R.id.txt_week);
		txtTime=(TextView)findViewById(R.id.txt_time);
		SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日");
		Date date=new Date(TimeUtils.getCurrentMill());
		txtDate.setText(format.format(date));
		txtWeek.setText(TimeUtils.getWeekDay());
		//设置时区
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		SimpleDateFormat format2=new SimpleDateFormat("HH:mm");
		Date date2=new Date(TimeUtils.getCurrentMill());
		txtTime.setText(format2.format(date2));
		
		editWeight=(EditText)findViewById(R.id.edit_weight);
		editMood=(EditText)findViewById(R.id.edit_mood);
		editDes=(EditText)findViewById(R.id.edit_diary_content);
		registerBroadcast();
		
	}

	public void registerBroadcast()
	{
		IntentFilter filter=new IntentFilter();
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(receiver, filter);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) 
		{
		case R.id.img_back:
			onBackPressed();
			finish();
			break;

		case R.id.img_ok:
			onSaveData();
			break;
		}
	}

	//保存资料
	private void onSaveData()
	{
		// TODO Auto-generated method stub
	  if(TextUtils.isEmpty(editWeight.getText().toString().trim()))
	  {
		  
		   ToastUtils.show(Constants.ERROR_WEIGHT,Gravity.CENTER);
		   return;
	  }
	  
	  if(Integer.parseInt(editWeight.getText().toString().trim())>20||Integer.parseInt(editWeight.getText().toString().trim())<0)
	  {
	      ToastUtils.show(Constants.ERROR_WEIGHT,Gravity.CENTER);
	      return;
	  }
	  if(TextUtils.isEmpty(editDes.getText().toString().trim()))
	  {
		  ToastUtils.show(Constants.ERROR_DES_EMPTY,Gravity.CENTER);
		  return;
	  }
	  
	  Diary diary=new Diary();
	  diary.setDate(txtDate.getText().toString());
	  diary.setWeek(txtWeek.getText().toString());
	  diary.setTime(txtTime.getText().toString());
	  diary.setWeight(editWeight.getText().toString());
	  diary.setMood(editMood.getText().toString());
	  diary.setDescribe(editDes.getText().toString());
	  
	  DiaryTableDao dao=new DiaryTableDao(this);
	  flag=dao.insertDiary(diary);
	  if(flag>0)
	  {
		  finish();
	  }

	}
	
	@Override
	public void finish() 
	{
		if(flag>0)
		   setResult(1);
		else
		   setResult(0);
		super.finish();
	}
	
}
