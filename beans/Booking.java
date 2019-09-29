package com.bbs.beans;

public class Booking {
	private int bookingId;
	private int userId;
	private int busId;
	private String source;
	private String destination;
	private String date;
	private int numOfSeats;
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getNumOfSeats() {
		return numOfSeats;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setNumOfSeats(int numOfSeats) {
		this.numOfSeats = numOfSeats;
	}
	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", busId=" + busId + ", source=" + source + ", destination="
				+ destination + ", date=" + date + ", numOfSeats=" + numOfSeats + "]";
	}

      
	

}
