package com.wsq.ebottle.bean;

public class Event {

	
	int id;
	int dot; //0代表棕色点（喂奶），1代表红色点（饮水），2代表灰色（闹铃提醒）
	String time;
	String des;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDot() {
		return dot;
	}
	public void setDot(int dot) {
		this.dot = dot;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}

	
}
