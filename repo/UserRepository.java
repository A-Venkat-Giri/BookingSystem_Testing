package com.bbs.repo;

import java.util.HashMap;

import com.bbs.beans.Admin;
import com.bbs.beans.Available;
import com.bbs.beans.Booking;
import com.bbs.beans.Bus;
import com.bbs.beans.Ticket;
import com.bbs.beans.User;

public class UserRepository {

	public static HashMap<Integer, User> db=new HashMap<>();
	public static HashMap<Integer, Bus> busDb=new HashMap<>();
	public static HashMap<Integer, Ticket> ticketDb=new HashMap<>();
	public static HashMap<Integer, Available> availableDb= new HashMap<>();
	public static HashMap<Integer, Booking> bookingDb= new HashMap<>();
	public static HashMap<Integer, Admin> adminDb= new HashMap<>();
	
	public UserRepository() {
		User user1=new User();
		user1.setUserId(1);
		user1.setUserName("venkat");
		user1.setEmail("venkat@gmail.com");
		user1.setPassword("7381");
		user1.setContact(1234554321);
		db.put(1,user1);
		
		User user2=new User();
		user2.setUserId(2);
		user2.setUserName("giri");
		user2.setEmail("giri@gmail.com");
		user2.setPassword("8181");
		user2.setContact(987655678);
		db.put(2,user2);
		
		User user3=new User();
		user3.setUserId(2);
		user3.setUserName("venkatgiri");
		user3.setEmail("venkatgiri@gmail.com");
		user3.setPassword("2050");
		user3.setContact(1232123);
		db.put(3,user3);
		
		Bus bus1 = new Bus();
		bus1.setBusId(101);
		bus1.setBusName("BMW");
		bus1.setBusType("Deluxe");
		bus1.setSource("Mumbai");
		bus1.setDestination("Delhi");
		bus1.setTotalSeats(20);
		bus1.setPrice(1500);
		busDb.put(bus1.getBusId(), bus1);
		
		Bus bus2 = new Bus();
		bus2.setBusId(102);
		bus2.setBusName("Mercedes");
		bus2.setBusType("NON-AC");
		bus2.setSource("Delhi");
		bus2.setDestination("Punjab");
		bus2.setTotalSeats(35);
		bus2.setPrice(500);
		busDb.put(bus2.getBusId(), bus2);

		Available availBus1=new Available();
		availBus1.setAvailId(111);
		availBus1.setAvailSeats(20);
		availBus1.setBusId(101);
		availBus1.setJourneyDate("2019-06-01");
		availableDb.put(availBus1.getBusId(), availBus1 );
		
		Available availBus2=new Available();
		availBus2.setAvailId(222);
		availBus2.setAvailSeats(35);
		availBus2.setBusId(102);
		availBus2.setJourneyDate("2019-07-07");
		availableDb.put(availBus2.getBusId(), availBus1 );
		
		Admin admin =new Admin();
		admin.setAdminId(7777);
		admin.setPassword("1234");
		adminDb.put(admin.getAdminId(), admin);
		

}
}
