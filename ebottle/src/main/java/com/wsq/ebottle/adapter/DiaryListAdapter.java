package com.wsq.ebottle.adapter;

import java.util.ArrayList;

import com.wsq.ebottle.R;
import com.wsq.ebottle.adapter.MenuListAdapter.ViewHolder;
import com.wsq.ebottle.bean.Diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DiaryListAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<Diary> diaries;
	private LayoutInflater inflater;
	
	public DiaryListAdapter(Context context,ArrayList<Diary> diaries)
	{
		this.context=context;
		this.diaries=diaries;
		inflater=LayoutInflater.from(context);
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return diaries.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return diaries.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(arg1==null)
		{
			arg1=inflater.inflate(R.layout.row_diary,null);
			holder=new ViewHolder();
			holder.tv_date=(TextView)arg1.findViewById(R.id.txt_date);
			holder.tv_week=(TextView)arg1.findViewById(R.id.txt_week);
			holder.tv_time=(TextView)arg1.findViewById(R.id.txt_time);
			holder.tv_weight=(TextView)arg1.findViewById(R.id.txt_weight);
			holder.tv_mood=(TextView)arg1.findViewById(R.id.txt_mood);
			holder.tv_des=(TextView)arg1.findViewById(R.id.txt_des);
			arg1.setTag(holder);
		}else
		{
			holder=(ViewHolder)arg1.getTag();
		}
		
		holder.tv_date.setText(diaries.get(arg0).getDate());
		holder.tv_week.setText(diaries.get(arg0).getWeek());
		holder.tv_time.setText(diaries.get(arg0).getTime());
		holder.tv_weight.setText(context.getText(R.string.diary_baby_weight)+":"+diaries.get(arg0).getWeight()+"Kg");
		holder.tv_mood.setText(context.getText(R.string.baby_mood)+":"+diaries.get(arg0).getMood());
		holder.tv_des.setText(diaries.get(arg0).getDescribe());

		return arg1;
	}
	
	class ViewHolder
	{
		TextView tv_date;
		TextView tv_week;
		TextView tv_time;
		TextView tv_weight;
		TextView tv_mood;
		TextView tv_des;		
	}

}
