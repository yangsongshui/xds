package com.wsq.ebottle.db.dao;

import java.util.ArrayList;

import com.wsq.ebottle.bean.Diary;
import com.wsq.ebottle.db.EbottleDB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DiaryTableDao {

	private Context context;
	private ArrayList<Diary> diaries=new ArrayList<Diary>();
	
	public DiaryTableDao(Context context)
	{
		this.context=context;
	}
	public long insertDiary(Diary diary)
	{
		
		SQLiteDatabase db=EbottleDB.getInstance().getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("date", diary.getDate());
		values.put("week", diary.getWeek());
		values.put("time", diary.getTime());
		values.put("weight",diary.getWeight());
		values.put("mood", diary.getMood());
		values.put("describe", diary.getDescribe());
		long index=db.insert("diaryTable", null, values);
		return index;

	}
	@SuppressLint("NewApi") 
	public ArrayList<Diary> getAllDiary()
	{
		SQLiteDatabase db=EbottleDB.getInstance().getReadableDatabase();
		Cursor cursor=db.query(true, "diaryTable",new String[]{"date","week","time","weight","mood","describe"}, null, null,
				null, null, null, null, null);
		while(cursor.moveToNext())
		{
			Diary diary=new Diary();
			diary.setDate(cursor.getString(0));
			diary.setWeek(cursor.getString(1));
			diary.setTime(cursor.getString(2));
			diary.setWeight(cursor.getString(3));
			diary.setMood(cursor.getString(4));
			diary.setDescribe(cursor.getString(5));
			diaries.add(diary);
		}
		cursor.close();
		return diaries;
		
	}
}
