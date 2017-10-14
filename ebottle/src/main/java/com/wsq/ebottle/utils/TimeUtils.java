package com.wsq.ebottle.utils;

import java.util.Calendar;

public class TimeUtils {


	//获取当前秒秒数
	public static long getCurrentMill()
	{
		Calendar calendar=Calendar.getInstance();
		return calendar.getTimeInMillis();
	}

	//获取当天星期几
	public static String getWeekDay()
	{
		String str="";
		Calendar calendar=Calendar.getInstance();
		String way = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
		if("1".equals(way))
		{
			str="天";
		}else if("2".equals(way))
		{
			str="一";
		}else if("3".equals(way))
		{
			str="二";
		}else if("4".equals(way))
		{
			str="三";
		}else if("5".equals(way))
		{
			str="四";
		}else if("6".equals(way))
		{
			str="五";
		}else if("7".equals(way))
		{
			str="六";
		}
		return "星期"+str;
	}


	//获取星期几
	public static int getWeekNum()
	{
		Calendar calendar=Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
}
