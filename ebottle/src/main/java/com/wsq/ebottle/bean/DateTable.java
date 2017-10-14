package com.wsq.ebottle.bean;

public class DateTable {

	int id;
	String date;
	int week; //1是星期天···7是星期六
	String milkPowder;//奶粉
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public String getMilkPowder() {
		return milkPowder;
	}
	public void setMilkPowder(String milkPowder) {
		this.milkPowder = milkPowder;
	}
	
	
}
