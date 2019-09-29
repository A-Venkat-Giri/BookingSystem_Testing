package com.bbs.dao;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.bbs.beans.Available;
import com.bbs.beans.Booking;
import com.bbs.beans.Bus;
import com.bbs.beans.Ticket;
import com.bbs.beans.User;
import com.bbs.exception.AddUserException;
import com.bbs.exception.BookingFailedException;
import com.bbs.exception.CancelFailedException;
import com.bbs.exception.CustomException;
import com.bbs.exception.DeleteFailedException;
import com.bbs.exception.LoginException;
import com.bbs.exception.TicketRetrievalFailedException;
import com.bbs.exception.UpdateFailedException;
import com.bbs.repo.UserRepository;
import com.bbs.services.ServiceUser;
import com.bbs.services.UserServiceImpl;

public class DaoUserImpl implements DaoUser {
	/**Making the Object of Repostitory**/
	UserRepository repo = new UserRepository();
	/**Calling HashMaps From Repository**/
	HashMap<Integer, User> map = repo.db;
	HashMap<Integer, Bus> busDb = repo.busDb;
	HashMap<Integer, Available> availableDb = repo.availableDb;
	HashMap<Integer, Ticket> ticketDb = repo.ticketDb;
	HashMap<Integer, Booking> bookingDb = repo.bookingDb;

	/**Used for creating the user registration**/
	@Override
	public Boolean createUser(User user) {
		try {
			map.put(user.getUserId(), user);
			return true;
		} catch (Exception e) {
			throw new AddUserException("Add User Exception Provide Proper Data");
		}
	}

	/**Updating the user password**/
	@Override
	public Boolean updateUser(Integer userId, String password, String newPassword) {
		try {
			User user = searchUser(userId);
			if (user.getPassword().equals(password)) {

				user.setPassword(newPassword);
				map.put(userId, user);
				return true;
			} else {
				UpdateFailedException exception = new UpdateFailedException(
						"UpdateFailedException:Password Provided is Incorrect");
				exception.getMessage();
			}
		} catch (UpdateFailedException e) {

		}
		return false;
	}

	/**Deleting the user account**/
	@Override
	public Boolean deleteUser(int userId, String password) {
		try {
			/** Searching User by userId**/
			User user = searchUser(userId);
			if (user.getPassword().equals(password)) {
				map.remove(userId);
				return true;
			} else {
				DeleteFailedException exception = new DeleteFailedException(
						"DeleteFailedException:Provide Proper Password");
				exception.getMessage();
			}

		} catch (Exception e) {

		}
		return false;
	}

	/**Used for logging the user**/
	@Override
	public Boolean loginUser(int userId, String password) {
		try {
			User user = searchUser(userId);
		/**Checking for password**/
			if (user.getPassword().equals(password)) {
				return true;
			} else {
				throw new LoginException("LoginException:Wrong password Given");
			}
		}

		catch (Exception e) {

		}
		return false;
	}

	/**Search the user weather the user account is already exists or not**/
	@Override
	public User searchUser(int userId) {
		try {
			User user = null;
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				if (pair.getKey().equals(userId)) {
					return (User) pair.getValue();
				}
			}
			return user;
		} catch (Exception e) {
			throw new CustomException("CantFindUser Provide Proper UserId");
		}
	}

	/**Booking Ticket For User**/
	@Override
	public Booking bookTicket(Ticket ticket) {
		String source = ticket.getSource();
		String destination = ticket.getDestination();
		Integer busId = ticket.getBusId();
		String date1 = ticket.getDate();
		java.util.Date date2;
		/**Generating Random Number For Booking Id**/
		Random randomNumGenerator = new Random();
		Integer bookingId = randomNumGenerator.nextInt(1000);
		try {
			/**Converting to Date From String**/
			date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
			Date date = new Date(date2.getTime());
			Bus bus = busDb.get(busId);
			Booking booking = new Booking();
			/**Checking For Buses for Source And Destination**/
			if (source.equalsIgnoreCase(bus.getSource()) && destination.equalsIgnoreCase(bus.getDestination())) {
				/**Checking Availability **/
				Integer available = checkAvailability(busId, date);
				if (available >= ticket.getNumberOfSeats()) {
					/**Reducting Available Seats**/
					available = available - ticket.getNumberOfSeats();
					Available available1 = availableDb.get(bus.getBusId());
					available1.setAvailSeats(available);
					/**Setting Booking Info in Booking Object**/
					booking.setBusId(ticket.getBusId());
					booking.setDate(ticket.getDate());
					booking.setSource(ticket.getSource());
					booking.setDestination(ticket.getDestination());
					booking.setNumOfSeats(ticket.getNumberOfSeats());
					booking.setBookingId(bookingId);
					booking.setUserId(ticket.getUserId());
					bookingDb.put(booking.getBookingId(), booking);
					return bookingDb.get(booking.getBookingId());
				} else {
					System.out.println("No Tickets Available");
				}

			}
		} catch (Exception e) {
			throw new BookingFailedException("Booking Failed Enter PROPER Details");

		}

		return null;
	}

	/**Cancelling Tickets**/
	@Override
	public Boolean cancelTicket(Booking booking) {
		try {
			/**Search For Tickets On Booking Id**/

			/**Cancelling Ticket i.e Removing Booking Info**/
			booking = bookingDb.remove(booking.getBookingId());
			if (booking != null) {
			/**Adding No. OF Seats After Cancellation**/
				Available available = availableDb.get(booking.getBusId());
				available.setAvailSeats(available.getAvailSeats() + booking.getNumOfSeats());
				return true;

			}
		} catch (Exception e) {
			throw new CancelFailedException("Cnt Cancel Provide Proper Booking Id");
		}

		return null;
	}

	/**View Booked Ticket**/
	@Override
	public Booking getTicket(int bookingId, int userId) {
		try {
			Iterator it = bookingDb.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				if (pair.getKey().equals(bookingId)) {
					Booking booking = (Booking) pair.getValue();
					if (booking.getUserId() == userId) {
						return booking;
					}

				}
			}

			return null;
		} catch (Exception e) {
			throw new TicketRetrievalFailedException("Ticket Cancelled Failed Provide Proper bookingId");
		}
	}

	/**Checking Availability**/
	@Override
	public Integer checkAvailability(int busId, Date date) {
		try {
			Available available = availableDb.get(busId);
			Booking bookingInfo = new Booking();
			if (date.toString().equals(available.getJourneyDate())) {
				return available.getAvailSeats();

			}
		} catch (Exception e) {
			CustomException exception = new CustomException("Cant get Availability Provide Proper Details");
			exception.getMessage();
		}
		return 0;
	}

	@Override
	public HashMap<Integer, Bus> getAllBus() {
		return repo.busDb;
	}

	@Override
	public Bus searchBus(int bus_id) {
		Bus bus = null;

		try {
			for (Map.Entry<Integer, Bus> pair : busDb.entrySet()) {
				if (pair.getKey().equals(bus_id)) {
					return pair.getValue();
				}
			}

		} catch (Exception e) {
		}
		return null;

	}

	@Override
	public List<Bus> searchBus(String source, String destination, Date date) {
		List<Bus> buses = new ArrayList<Bus>();
		int i = 0;
		for (Map.Entry pair : busDb.entrySet()) {
			Bus bus = (Bus) pair.getValue();

			if (bus.getSource().equalsIgnoreCase(source) && bus.getDestination().equalsIgnoreCase(destination)) {

				try {
					Available available = availableDb.get(bus.getBusId());
					java.util.Date date2;
					date2 = new SimpleDateFormat("yyyy-MM-dd").parse(available.getJourneyDate());
					Date date1 = new Date(date2.getTime());
					if (date1.equals(date)) {
						buses.add(i, bus);
						i++;
					}

				}

				catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return buses;
	}

	@Override
	public User searchUser(String userName) {
		for (Map.Entry pair : map.entrySet()) {
			User user = (User) pair.getValue();
			if (user.getEmail().equalsIgnoreCase(userName)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public List<Booking> getAllTickets(int userId) {
		List<Booking> bookings = new ArrayList<>();
		int i = 0;
		for(Map.Entry pair : bookingDb.entrySet())
		{
			Booking booking = (Booking) pair.getValue();
			bookings.add(i, booking);
			i++;
		}
		return bookings;
	}
}
