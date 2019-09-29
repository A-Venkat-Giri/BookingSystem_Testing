package com.bbs.beans;

public class Bus {
	private int busId;
	private String busName;
	private String source;
	private String Destination;
	private String busType;
	private int totalSeats;
	private int price;
	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return Destination;
	}
	public void setDestination(String destination) {
		Destination = destination;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Bus [busId=" + busId + ", busName=" + busName + ", source=" + source + ", Destination=" + Destination
				+ ", busType=" + busType + ", totalSeats=" + totalSeats + ", price=" + price + "]";
	}
	
	
	
	
	

}
