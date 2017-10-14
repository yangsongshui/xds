package com.wsq.ebottle.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wsq.ebottle.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlueToothListAdapter extends BaseAdapter{
	
	
	private ArrayList<BluetoothDevice> bleDevices;
	private LayoutInflater inflater;
	private Map<String, Integer> map=new HashMap<String, Integer>();
	Context context;
	SharedPreferences.Editor editor;
	public BlueToothListAdapter(Context context)
	{
        super();
        bleDevices=new ArrayList<BluetoothDevice>();
		inflater=LayoutInflater.from(context);
		this.context=context;
		editor=context.getSharedPreferences("device_data",Context.MODE_PRIVATE).edit();
	}

	
	 //添加设备进列表中
    public void addDevice(BluetoothDevice device) {
      if (!bleDevices.contains(device)) 
      {
        bleDevices.add(device);
      }
    }
  //获取子项中对应的设备
    public BluetoothDevice getDevice(int position) {
      return bleDevices.get(position);
    }
	
    //清空列表的数据
    public void clear() 
    {
      bleDevices.clear();
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bleDevices.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bleDevices.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Viewholder holder=null;
		if(convertView==null)
		{
			convertView=inflater.inflate(R.layout.row_bluetooth_device, null);
			holder=new Viewholder();
			holder.tv=(TextView)convertView.findViewById(R.id.txt_bluetooth_name);
			holder.tv_id=(TextView)convertView.findViewById(R.id.txt_bluetooth_id);
			holder.tv_bind=(TextView)convertView.findViewById(R.id.txt_bind);
			convertView.setTag(holder);
		}else
		{
			holder=(Viewholder)convertView.getTag();
		}
		
		Log.i("wsq",bleDevices.get(position).getName());
		holder.tv.setText(bleDevices.get(position).getName());
	/*	if(bleDevices.get(position).getName().equals("eBottle-B10")||bleDevices.get(position).getName().equals("eBottle-A10"))
		{
		    holder.tv.setText(bleDevices.get(position).getName());
		}else
		{
			holder.tv.setText("未知");
		}*/
		
		String[] type=bleDevices.get(position).getName().split("-");
		String[] str=bleDevices.get(position).getAddress().split(":");

		String nurseId="";
		String waterId="";
		if(type[1].contains("B"))
		{
			int bma1=Integer.parseInt(str[3], 16);
			int bma2=Integer.parseInt(str[4], 16);
			int bma3=Integer.parseInt(str[5], 16);
			nurseId=(bma1+"")+(bma2+"")+(bma3+"02");
			holder.tv_id.setText("编码："+nurseId);
			

		}else if(type[1].contains("A"))
		{
			
			int bma1=Integer.parseInt(str[3], 16);
			int bma2=Integer.parseInt(str[4], 16);
			int bma3=Integer.parseInt(str[5], 16);
			waterId=(bma1+"")+(bma2+"")+(bma3+"");
			holder.tv_id.setText("编码："+waterId);
		}
		
		String address=context.getSharedPreferences("device_data",Context.MODE_PRIVATE).getString("address","");
        if(address.equals(bleDevices.get(position).getAddress()))
        {
        	holder.tv_bind.setVisibility(View.VISIBLE);
        }else
        {
        	holder.tv_bind.setVisibility(View.INVISIBLE);
        }
		return convertView;
	}

	
	class Viewholder
	{
		TextView tv;
		TextView tv_id;
		TextView tv_bind;
	}
	
}
