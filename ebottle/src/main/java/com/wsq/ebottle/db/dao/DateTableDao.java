package com.wsq.ebottle.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wsq.ebottle.bean.DateTable;
import com.wsq.ebottle.db.EbottleDB;

import java.util.ArrayList;

public class DateTableDao {

	private Context context;
	ArrayList<DateTable> dates=new ArrayList<DateTable>();
	
	public DateTableDao(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	public long insertDate(DateTable dateTable)
	{
		SQLiteDatabase db=EbottleDB.getInstance().getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("date", dateTable.getDate());
		values.put("week", dateTable.getWeek());
		values.put("milkPowder",dateTable.getMilkPowder());
		
		String sql="select * from dateTable where date='"+dateTable.getDate()+"'";
		Cursor cursor=db.rawQuery(sql, null);
		long index=0;
		if(cursor.getCount()==0)
		{
			cursor.close();
			index=db.insert("dateTable",null, values);
		}
		db.close();
		return index;
	}
	
	public int QueryIdByDate(String date)
	{
		int id=0;
		SQLiteDatabase db=EbottleDB.getInstance().getReadableDatabase();
		String sql="select id from dateTable where date='"+date+"'";
		Cursor cursor=db.rawQuery(sql,null);
		while(cursor.moveToNext())
		{
			id=cursor.getInt(0);
		}
		cursor.close();
		db.close();
		return id;
	}
	
	public ArrayList<DateTable> getAllDate()
	{
		SQLiteDatabase db=EbottleDB.getInstance().getReadableDatabase();
		Cursor cursor=db.query(true, "dateTable",new String[]{"id","date","week","milkPowder"},null,null, 
				null,null,null,null,null);
		while(cursor.moveToNext())
		{
			DateTable dateTable=new DateTable();
			dateTable.setId(cursor.getInt(0));
			dateTable.setDate(cursor.getString(1));
			dateTable.setWeek(cursor.getInt(2));
			dateTable.setMilkPowder(cursor.getString(3));
			dates.add(dateTable);
		}
		
		return dates;
		
	}
	
}
