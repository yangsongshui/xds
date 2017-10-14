package com.wsq.ebottle.fragment;

import java.util.ArrayList;

import com.wsq.ebittle.common.Constants;
import com.wsq.ebottle.R;
import com.wsq.ebottle.adapter.LoveAdapter;
import com.wsq.ebottle.bean.Event;
import com.wsq.ebottle.broadcast.CountDownReceiver;
import com.wsq.ebottle.db.dao.EventTableDao;
import com.wsq.ebottle.widget.MarqueeText;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class LoveFragment extends Fragment{

	private ListView listLove;
	private LoveAdapter loveAdapter;
	private ArrayList<Event> events;
	private TextView tvRemindTime; //剩余时间
	private static int UPDATE_UI_TEXT=1001;
	private static int UPDATE_UI_GONE=1002;
	String remindTime;
	private MarqueeText marqueeText;
	
	SharedPreferences.Editor editor;
	
	EventTableDao dao;
	
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			String action=arg1.getAction();
			if(Constants.BROADCAST_ONTICKING.equals(action))
			{
				remindTime=arg1.getStringExtra("time");
				handler.sendEmptyMessage(UPDATE_UI_TEXT);
				Log.i("wsq","time");
				
				
			}else if(Constants.BROADCAST_ONTICK_FINISH.equals(action))
			{
				
				handler.sendEmptyMessage(UPDATE_UI_GONE);
				if(editor!=null)
				{
					editor.putBoolean("isStart",false);
					editor.commit();
				}
			}
		}
	};
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_love,container,false);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getActivity().registerReceiver(receiver, makeGattUpdateIntentFilter());
		initView();
	}

	@SuppressLint("InflateParams") 
	private void initView() {
		editor=getActivity().getSharedPreferences(Constants.COUNT_DOWN_TIME,Context.MODE_PRIVATE).edit();
		
		listLove=(ListView)getView().findViewById(R.id.list_love);
		View view=LayoutInflater.from(getActivity()).inflate(R.layout.love_list_head_item, null);
		listLove.addHeaderView(view);
		
		marqueeText=(MarqueeText) getActivity().findViewById(R.id.txt_marquee);
		marqueeText.setVisibility(View.GONE);

		//发一次广播更新时间
		Intent intent=new Intent(getActivity(),CountDownReceiver.class);
		intent.setAction("com.wsq.ebottle");
		getActivity().sendBroadcast(intent);

		//数据库
		dao=new EventTableDao(getActivity());

		 initEvents();
	}
	
	private static IntentFilter makeGattUpdateIntentFilter() 
	{
		IntentFilter filter=new IntentFilter();
		filter.addAction(Constants.BROADCAST_ONTICKING);
		filter.addAction(Constants.BROADCAST_ONTICK_FINISH );
		return filter;
	}


	//初始化事件
	private void initEvents() {

		events=dao.getAllEvent();
		loveAdapter=new LoveAdapter(getActivity(), events);
		listLove.setAdapter(loveAdapter);
		
	}
	
	Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			if(msg.what==UPDATE_UI_TEXT)
			{
				marqueeText.setVisibility(View.VISIBLE);
				marqueeText.setText("距离喂奶的时间还剩"+remindTime);
				
			}else if(msg.what==UPDATE_UI_GONE)
			{
				marqueeText.setVisibility(View.GONE);
			}
			
		};
	};




	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(receiver);
	}
	
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		if(!hidden)
		{
			events.clear();
			initEvents();
		}
		super.onHiddenChanged(hidden);
	}
	
	
	
}
