package com.bbs.services;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bbs.beans.Booking;
import com.bbs.beans.Bus;
import com.bbs.beans.Ticket;
import com.bbs.beans.User;
import com.bbs.dao.DaoUser;
import com.bbs.dao.DaoUserImpl;
import com.bbs.exception.CustomException;

public class UserServiceImpl implements ServiceUser {
	DaoUser dao=new DaoUserImpl();

	@Override
	public Boolean createUser(User user) {
		return dao.createUser(user);
	}

	@Override
	public Boolean updateUser(Integer userId, String password, String newPassword) {
		return dao.updateUser(userId, password , newPassword );
	}

	@Override
	public Boolean deleteUser(int userId, String password) {
		return dao.deleteUser(userId, password);
	}

	@Override
	public Boolean loginUser(int userId, String password) {
		return dao.loginUser(userId, password);
	}

	@Override
	public User searchUser(int userId) {
		return dao.searchUser(userId);
	}



	@Override
	public Booking bookTicket(Ticket ticket) {
		return dao.bookTicket(ticket);
	}

	@Override
	public Boolean cancelTicket(Booking booking) {
		return dao.cancelTicket(booking);
	}

	@Override
	public Booking getTicket(int bookingId,int userId) {
		return dao.getTicket(bookingId,userId);
	}

	@Override
	public Integer checkAvailability(int busId, Date date) {
		return dao.checkAvailability(busId, date);
	}

	@Override
	public String checkUserIdAndBookinIdAndBusId(String number) {
		Pattern pat = Pattern.compile("\\d+");       // represents any number
		Matcher mat = pat.matcher(number);
		if(mat.matches())
		{
			return number;
		}
		else
		{
			return null;
		}
	}

	@Override
	public String checkContact(String contact) {
		Pattern pat = Pattern.compile("\\d{10,10}");
		Matcher mat =  pat.matcher(contact);
		if(mat.matches())
		{
			return contact;
		}
		else
		{
			return null;
		}

	}

	@Override
	public String checkEmail(String email) {
		Pattern pattern=Pattern.compile("\\w+@\\w+.\\w+");
		Matcher matcher=pattern.matcher(email);
		if(matcher.matches())
		{
			return email;
		}
		else
		{
			CustomException exception = new CustomException("EmailException:ProvideProperEmail");
			exception.getMessage();
			
		}
		return null;
	}

	@Override
	public HashMap<Integer, Bus> getAllBus() {
		return dao.getAllBus();
	}

	@Override
	public String checkDate(String date) {
      Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
      Matcher matcher = pattern.matcher(date);
      if(matcher.matches())
      {
    	  return date;
      }
      else
      {
    	  CustomException exception = new CustomException("CustomException:Provide Date in proper Format");
    	  exception.getMessage();
      }
      return null;
		
	}

	@Override
	public Bus searchBus(int bus_id) {
		return dao.searchBus(bus_id);
	}

	@Override
	public List<Bus> searchBus(String source, String destination, Date date) {
		return dao.searchBus(source, destination, date);
	}

	@Override
	public User searchUser(String userName) {
		return dao.searchUser(userName);
	}

	@Override
	public List<Booking> getAllTickets(int userId) {
		return dao.getAllTickets(userId);
	}
}



