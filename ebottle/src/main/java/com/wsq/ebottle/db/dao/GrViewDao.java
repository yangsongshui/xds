package com.wsq.ebottle.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wsq.ebottle.bean.GrView;
import com.wsq.ebottle.db.EbottleDB;

import java.util.ArrayList;

/**
 *获取成长记录资料
 */
public class GrViewDao {

	private Context context;
	ArrayList<GrView> grViews=new ArrayList<GrView>();
	public GrViewDao(Context context)
	{
		this.context=context;
	}
	
	public ArrayList<GrView> getAllGr()
	{
		SQLiteDatabase db=EbottleDB.getInstance().getReadableDatabase();
		Cursor cursor=db.query(true,"gr_view",new String[]{"id","time","intake","temp","date","week","milkPowder","type","dateId"}, 
				null,null,null,null,null,null,null);
	   //String sql="SELECT * FROM gr_view AS A  WHERE (dateId IN (SELECT dateId FROM gr_view AS B WHERE A.ID <> B.ID));";
	  // Cursor cursor=db.rawQuery(sql, null);
		while(cursor.moveToNext())
		{
			GrView grView=new GrView();
			grView.setId(cursor.getInt(0));
			grView.setTime(cursor.getString(1));
			grView.setIntake(cursor.getInt(2));
			grView.setTemp(cursor.getFloat(3));
			grView.setDate(cursor.getString(4));
			grView.setWeek(cursor.getInt(5));
			grView.setMilkPowder(cursor.getString(6));
			grView.setType(cursor.getInt(7));
			grView.setDateId(cursor.getInt(8));
			grViews.add(grView);
		}
		return grViews;
	}
}
