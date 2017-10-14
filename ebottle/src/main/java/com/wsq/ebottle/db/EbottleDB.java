package com.wsq.ebottle.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wsq.ebottle.MyApplication;

public class EbottleDB extends SQLiteOpenHelper{

	private static String db_name="ebottleDB";
	private static int db_version=1;
	static Context context=MyApplication.getInstance();
	
	public static EbottleDB ebottleDB=null;
	
	public EbottleDB() {
		
		super(context,db_name,null, db_version);
	}
	
	public static synchronized EbottleDB getInstance()
	{
		if(ebottleDB==null)
		{
			ebottleDB=new EbottleDB();
		}
		return ebottleDB;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建日记表
		String sql=" CREATE TABLE IF NOT EXISTS diaryTable ( "
						    + "id INTEGER  PRIMARY  KEY,"
						    + "date TEXT,"
							+ "week  TEXT,"
							+ "time  TEXT,"
							+ "mood TEXT,"
							+ "weight  TEXT,"
							+ "describe  TEXT);";

		db.execSQL(sql);


		//日期表
		sql="CREATE TABLE IF NOT EXISTS dateTable ("
				+"id INTEGER PRIMARY KEY,"
				+"date INTEHER,"
				+"week TEXT,"
				+"milkPowder TEXT"
				+");";
		db.execSQL(sql);

		//数据表
		sql="create table if not exists dataTable ("
				+"id integer primary key,"
				+"dateId integer,"
				+"time text,"
				+"intake integer,"
				+"temp real,"
				+"type integer"
		        +");";
		db.execSQL(sql);

		//创建视图
		sql="create view gr_view as "
				+"SELECT"
				+	" dataTable.id,"
				+	"dataTable.time,"
				+	"dataTable.intake,"
				+	"dataTable.temp,"
				+	"dateTable.date,"
				+	"dateTable.week,"
				+	"dateTable.milkPowder,"
				+	"dataTable.type,"
				+	"dataTable.dateId"
				+	" FROM"
				+	" dataTable,"
				+	"dateTable"
				+	" WHERE"
				+	" dataTable.dateId = dateTable.id";
		db.execSQL(sql);
		
		
		
		//事件表
		sql="CREATE TABLE IF NOT EXISTS eventTable ("
				+"id INTEGER PRIMARY KEY,"
				+"dot INTEGER,"
				+"time TEXT," 
				+"des TEXT"
				+");";
		db.execSQL(sql);
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		String sql="DROP TABLE IF EXISTS diaryTable";
		db.execSQL(sql);
		
		sql="DROP TABLE IF EXISTS dateTable";
		db.execSQL(sql);
		
		sql="DROP TABLE IF EXISTS dataTable";
		db.execSQL(sql);
		
		sql="DROP VIEW IF EXISTS gr_view";
		db.execSQL(sql);
		
		sql="DROP TABLE IF EXISTS eventTable";
		db.execSQL(sql);
	}

}
