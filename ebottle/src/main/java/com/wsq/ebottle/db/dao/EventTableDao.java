package com.wsq.ebottle.db.dao;

import java.util.ArrayList;

import com.wsq.ebottle.bean.Event;
import com.wsq.ebottle.db.EbottleDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class EventTableDao {
	
	private Context context;
	private ArrayList<Event> events=new ArrayList<Event>();
	
	public EventTableDao(Context context)
	{
		this.context=context;
	}
	
	public long insertEvent(Event event)
	{
		SQLiteDatabase db=EbottleDB.getInstance().getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("dot", event.getDot());
		values.put("des", event.getDes());
		values.put("time", event.getTime());
		long index=db.insert("eventTable",null, values);
		return index;
	}
	
	
	public ArrayList<Event> getAllEvent()
	{
		Log.i("wsq","getAllEvent");
		SQLiteDatabase db=EbottleDB.getInstance().getReadableDatabase();
		Cursor cursor=db.query(true, "eventTable", new String[]{"id","dot","des","time"}, 
				null,null,null,null,null,null,null);
		while(cursor.moveToNext())
		{
			Event event=new Event();
			event.setId(cursor.getInt(0));
			event.setDot(cursor.getInt(1));
			event.setDes(cursor.getString(2));
			event.setTime(cursor.getString(3));
			events.add(event);
		}
		cursor.close();
		
		return events;
		
	}
	
	

}
