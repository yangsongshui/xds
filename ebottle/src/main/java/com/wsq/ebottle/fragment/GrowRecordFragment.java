package com.wsq.ebottle.fragment;

import java.util.ArrayList;

import com.wsq.ebottle.R;
import com.wsq.ebottle.bean.GrView;
import com.wsq.ebottle.bean.RecordData;
import com.wsq.ebottle.db.dao.GrViewDao;
import com.wsq.ebottle.widget.RecordBarViewDay;
import com.wsq.ebottle.widget.RecordBarViewWeek;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class GrowRecordFragment extends Fragment{

	
	
	private RadioGroup radioGroupDayWeek;
	int index;
	private HorizontalScrollView hScrollView;
	private LinearLayout linearLayout;
	ArrayList<ArrayList<GrView>> dataLists=new ArrayList<ArrayList<GrView>>();
	ArrayList<GrView> grViews=new ArrayList<GrView>();

	GrViewDao dao;
	
	OnCheckedChangeListener changeListener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			switch (arg1) 
			{
			case R.id.radio_day:
				index=0;
				break;
			case R.id.radio_week:
				index=1;
				break;
			}
			
			if(index==0)
			{
				showDayView();
			}else
			{
				showWeekView();
			}
			
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_grow_record, container, false);
	}
	

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
		initView();
		
		
	}


	private void initView() {
		// TODO Auto-generated method stub
		radioGroupDayWeek=(RadioGroup)getView().findViewById(R.id.radio_group_day_week);
		radioGroupDayWeek.setOnCheckedChangeListener(changeListener);
		hScrollView=(HorizontalScrollView)getView().findViewById(R.id.h_scroll_view);
		linearLayout=(LinearLayout)getView().findViewById(R.id.content_parent);
		//数据库
		dao=new GrViewDao(getActivity());

		//展示数据
		showDayView();
	}
	

	protected void showDayView() {
		linearLayout.removeAllViews();//清空视图
	    ArrayList<RecordData> datas=new ArrayList<RecordData>();
	    datas.clear();
	    ArrayList<ArrayList<GrView>> lists=getAllGrData();//获取数据
	    

		for(int i=0;i<lists.size();i++)
		{
			ArrayList<GrView> grViews=lists.get(i);
			Log.i("wsq","index="+i+";size="+grViews.size());
			int totalIntake=0;
			for(GrView grView:grViews)
			{
				datas.add(new RecordData(grView.getIntake(),grView.getTime(),grView.getTemp(),grView.getType()));
				totalIntake+=grView.getIntake();
			}
			int week=grViews.get(i).getWeek();
			String milkName=grViews.get(i).getMilkPowder();
			String strWeek="";
			if(week==1)
			{
				strWeek="天";
			}else if(week==2)
			{
				strWeek="一";
			}else if(week==3)
			{
				strWeek="二";
			}else if(week==4)
			{
				strWeek="三";
			}else if(week==5)
			{
				strWeek="四";
			}else if(week==6)
			{
				strWeek="五";
			}else if(week==7)
			{
				strWeek="六";
			}
			
			RecordBarViewDay recordBarView=new RecordBarViewDay(getActivity());
			recordBarView.setDegree(datas,grViews.get(i).getDate(),"星期"+strWeek,grViews.size(),totalIntake,milkName);
			linearLayout.addView(recordBarView);	
			
			grViews.clear();
		}
		
		
		
	}
	protected void showWeekView() {
		// TODO Auto-generated method stub
		 linearLayout.removeAllViews();
		 ArrayList<RecordData> datas=new ArrayList<RecordData>();
		 datas.clear();
		 ArrayList<ArrayList<GrView>> lists=getAllGrData();//获取数据
		 for(int i=0;i<lists.size();i++)
		{
			 ArrayList<GrView> grViews=lists.get(i);
			 
			    int nurserTotalIntake=0;
			    int waterTotalIntake=0;
				for(GrView grView:grViews)
				{
					if(grView.getType()==1)//喂奶
					{
						nurserTotalIntake+=grView.getIntake();
					}else if(grView.getType()==2)//饮水
					{
						waterTotalIntake+=grView.getIntake();
					}
				}
				int week=grViews.get(i).getWeek();
				String strWeek="";
				if(week==1)
				{
					strWeek="天";
				}else if(week==2)
				{
					strWeek="一";
				}else if(week==3)
				{
					strWeek="二";
				}else if(week==4)
				{
					strWeek="三";
				}else if(week==5)
				{
					strWeek="四";
				}else if(week==6)
				{
					strWeek="五";
				}else if(week==7)
				{
					strWeek="六";
				}
			RecordBarViewWeek recordBarViewO=new RecordBarViewWeek(getActivity());
			
			recordBarViewO.setParam(grViews.get(i).getDate(),"星期"+strWeek,nurserTotalIntake,waterTotalIntake);
			
			linearLayout.addView(recordBarViewO);
		}
		
	}

	//按日的数据
	public ArrayList<ArrayList<GrView>> getAllGrData()
	{
		
		dataLists.clear();
		grViews.clear();
		
		grViews=dao.getAllGr();//获取所有的记录

		int curDateId=0,lastDateId=0;
		ArrayList<GrView> views = new ArrayList<GrView>();
		Log.i("wsq","Views size="+grViews.size());
		
		for(GrView grView:grViews)
	    {
			curDateId=grView.getDateId();
		    if(curDateId==lastDateId)//是同类
		    {
		    	Log.i("wsq","cur="+curDateId);
		    	if(views!=null)
				    views.add(grView);
		    	continue;
				   
		    }else//不是同类
		    {
		    	views=new ArrayList<GrView>();
		    	views.add(grView);   
		    }
		    if(views!=null)
		    {
		    	 dataLists.add(views);
		    	 //views.clear();
		    }
		    lastDateId=curDateId;
		    
		    Log.i("wsq","curDateId="+curDateId+";lastDateId="+lastDateId);
		}
	
		return dataLists;
	}	
	
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(!hidden)
		{
			showDayView();
		}
	}
	
}
