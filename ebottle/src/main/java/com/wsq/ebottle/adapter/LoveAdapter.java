package com.wsq.ebottle.adapter;

import java.util.ArrayList;

import com.wsq.ebottle.R;
import com.wsq.ebottle.bean.Event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LoveAdapter extends BaseAdapter{

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Event> events;
	public LoveAdapter(Context context,ArrayList<Event> events)
	{
		this.context=context;
		inflater=LayoutInflater.from(context);
		this.events=events;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return events.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return events.get(arg0);
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
			arg1=inflater.inflate(R.layout.row_love_item,null);
			holder=new ViewHolder();
			holder.iv_dot=(ImageView)arg1.findViewById(R.id.img_dot);
			holder.iv_ring=(ImageView)arg1.findViewById(R.id.img_ring);
			holder.tv_time=(TextView)arg1.findViewById(R.id.txt_time);
			holder.tv_des=(TextView)arg1.findViewById(R.id.txt_des);
			arg1.setTag(holder);
					
		}else
		{
			holder=(ViewHolder)arg1.getTag();
		}
		
		if(events.get(arg0).getDot()==0)
		{
			holder.iv_dot.setImageResource(R.drawable.love_dot_b);
			holder.iv_ring.setVisibility(View.INVISIBLE);
		}else if(events.get(arg0).getDot()==1)
		{
			holder.iv_dot.setImageResource(R.drawable.love_dot_r);
			holder.iv_ring.setVisibility(View.INVISIBLE);
			
		}else if(events.get(arg0).getDot()==2)
		{
			holder.iv_dot.setImageResource(R.drawable.love_dot_g);
			holder.iv_ring.setVisibility(View.VISIBLE);
		}
		
			
		
			
		
		
		holder.tv_time.setText(events.get(arg0).getTime());
		holder.tv_des.setText(events.get(arg0).getDes());
		
		return arg1;
	}
	
	class ViewHolder
	{
		TextView tv_time;
		ImageView iv_dot;
		TextView tv_des;
		ImageView iv_ring;
	}

}
