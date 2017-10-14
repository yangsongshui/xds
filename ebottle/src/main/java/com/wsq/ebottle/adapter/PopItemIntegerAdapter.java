package com.wsq.ebottle.adapter;

import java.util.ArrayList;

import com.wsq.ebottle.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PopItemIntegerAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<Integer> items;
	LayoutInflater inflater;
	
	public PopItemIntegerAdapter(Context context,ArrayList<Integer> items)
	{
		this.context=context;
		this.items=items;
		inflater=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return items.get(arg0);
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
			holder=new ViewHolder();
			arg1=inflater.inflate(R.layout.row_popup_item, null);
			holder.tv_item=(TextView)arg1.findViewById(R.id.txt_intake);
			arg1.setTag(holder);
		}else
		{
			holder=(ViewHolder) arg1.getTag();
		}
		
		holder.tv_item.setText(items.get(arg0)+"ML");
		
		return arg1;
	}
	
	class ViewHolder
	{
		TextView tv_item;
	}

}
