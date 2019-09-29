package com.bbs.beans;

import java.sql.Date;

public class Available {
	private int availId;
	private int busId;
	private int availSeats;
	private String journeyDate;
	public int getAvailId() {
		return availId;
	}
	public void setAvailId(int availId) {
		this.availId = availId;
	}
	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	public int getAvailSeats() {
		return availSeats;
	}
	public void setAvailSeats(int availSeats) {
		this.availSeats = availSeats;
	}
	public String getJourneyDate() {
		return journeyDate;
	}
	public void setJourneyDate(String journeyDate) {
		this.journeyDate = journeyDate;
	}
	@Override
	public String toString() {
		return "Available [availId=" + availId + ", busId=" + busId + ", availSeats=" + availSeats + ", journeyDate="
				+ journeyDate + "]";
	}
	
	
	

}
