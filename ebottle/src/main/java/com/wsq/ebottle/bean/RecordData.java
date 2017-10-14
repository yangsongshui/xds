package com.wsq.ebottle.bean;


/**
 * 临时记录类
 */
public class RecordData {
	int ml;
	String time;
	float temp;
	int select;
	
	
	public RecordData(int ml,String time,float temp,int select)
	{
		this.ml=ml;
		this.time=time;
		this.temp=temp;
		this.select=select;
	}
	
	public int getMl() {
		return ml;
	}
	public void setMl(int ml) {
		this.ml = ml;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public float getTemp() {
		return temp;
	}
	public void setTemp(float temp) {
		this.temp = temp;
	}
	public int getSelect() {
		return select;
	}
	public void setSelect(int select) {
		this.select = select;
	}
	
	
	

}
