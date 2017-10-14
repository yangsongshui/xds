package com.wsq.ebottle.adapter;

import java.util.ArrayList;
import java.util.Map;

import com.wsq.ebottle.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuListAdapter extends BaseAdapter{
	private static final String TAG="MenuListAdapter";
	

	private LayoutInflater inflater;
	private ArrayList<Map<String, Integer>> menus;
	private ViewHolder holder;

	private int[] names=new int[]{
			R.string.menu_main,
			R.string.menu_baby_data,
			R.string.menu_grow_record,
			R.string.menu_baby_diary,
			R.string.menu_alert_setting,
			R.string.menu_tem_setting,
			R.string.menu_device_manager
	};

	private int[] icons=new int[]{
			R.drawable.icon_main,
			R.drawable.icon_baby_data,
			R.drawable.icon_grow_record,
			R.drawable.icon_baby_diary,
			R.drawable.icon_alert_setting,
			R.drawable.icon_tem_setting,
			R.drawable.icon_device_manager,

	};
	
	int temp_pos;
	boolean flag=false;

	public MenuListAdapter(Context context,ArrayList<Map<String, Integer>> menus)
	{
		this.menus=menus;
		inflater=LayoutInflater.from(context);
	}
	public MenuListAdapter(Context context )
	{

		inflater=LayoutInflater.from(context);

	}
	public void changeState(int position,boolean flag)
	{
		temp_pos=position;
		this.flag=flag;
		notifyDataSetChanged();
	}
	
	

	@Override
	public int getCount() {
		return  names==null?0:  names.length;
	}

	@Override
	public Object getItem(int arg0) {
		return names[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {


		Log.i(TAG, "getView: postion="+position);
		if(view==null)
		{
			view=inflater.inflate(R.layout.item_menu,arg2,false);
			holder=new ViewHolder();
			holder.iv=(ImageView)view.findViewById(R.id.img_menu);
			holder.tv=(TextView)view.findViewById(R.id.txt_menu);
			view.setTag(holder);
		}else
		{
			holder=(ViewHolder)view.getTag();
		}


		holder.iv.setImageResource(icons[position]);
		holder.tv.setText(names[position]);

//		holder.iv.setImageResource(menus.get(position).get("img"));
//		holder.tv.setText(menus.get(position).get("name"));
		
		if(flag&&temp_pos==position)
		{
			view.setBackgroundResource(R.drawable.item_bg);

		}else
		{
			view.setBackgroundColor(Color.TRANSPARENT);
		}

		return view;
	}
	
	class ViewHolder
	{
		TextView tv;
		ImageView iv;
		
	}

}
