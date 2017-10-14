package com.wsq.ebottle.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wsq.ebottle.bean.DataTable;
import com.wsq.ebottle.db.EbottleDB;

import java.util.ArrayList;

public class DataTableDao {

	private Context context;
	ArrayList<DataTable> datas=new ArrayList<DataTable>();
	public DataTableDao(Context context)
	{
		this.context=context;
	}
	
	public long insertData(DataTable data)
	{
		SQLiteDatabase db=EbottleDB.getInstance().getWritableDatabase();
	    ContentValues values=new ContentValues();
	    values.put("dateId", data.getDateId());
	    values.put("time", data.getTime());
	    values.put("intake",data.getIntake());
	    values.put("temp",data.getTemp());
	    values.put("type",data.getType());
	    long index=db.insert("dataTable",null,values);
	    db.close();
	    return index;
	}
	
	
	public ArrayList<DataTable> getAllData()
	{
		SQLiteDatabase db=EbottleDB.getInstance().getReadableDatabase();
		Cursor cursor=db.query(true,"dataTable", new String[]{"id","dateId","time","intake","temp","type"},null, 
				null, null, null, null, null,null);
		while(cursor.moveToNext())
		{
			DataTable dataTable=new DataTable();
			dataTable.setId(cursor.getInt(0));
			dataTable.setDateId(cursor.getInt(1));
			dataTable.setTime(cursor.getString(2));
			dataTable.setIntake(cursor.getInt(3));
			dataTable.setTemp(cursor.getFloat(4));
			dataTable.setType(cursor.getInt(5));
			datas.add(dataTable);
			
		}
		db.close();
		cursor.close();
		return datas;
	}
}
