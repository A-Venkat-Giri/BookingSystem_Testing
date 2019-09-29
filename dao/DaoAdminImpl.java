package com.bbs.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.bbs.beans.Admin;
import com.bbs.beans.Available;
import com.bbs.beans.Bus;
import com.bbs.beans.User;
import com.bbs.exception.CustomException;
import com.bbs.exception.DeleteFailedException;
import com.bbs.exception.LoginException;
import com.bbs.exception.UpdateFailedException;
import com.bbs.repo.UserRepository;


public class DaoAdminImpl implements DaoAdmin {
	/**Making the Object of Repostitory**/
	UserRepository repo = new UserRepository();
	/**Calling HashMaps From Repository**/
	HashMap<Integer, Bus> busDb = repo.busDb;
	HashMap<Integer, Admin> adminDb=repo.adminDb;
	HashMap<Integer, Available> availableDb= repo.availableDb;
	Scanner sc = new Scanner(System.in);
	Admin admin;
	/**Adding bus to Hashmap**/
	@Override
	public Boolean createBus(Bus bus) {
		try {
			busDb.put(bus.getBusId(), bus);
			return true;
		} catch (Exception e) {
			throw new CustomException("Failed To Add Bus Proper Details");
		}
	}

	/**Updating the bus details**/
	@Override
	public Boolean updateBus(Bus bus) {
		try {
			bus  =	busDb.put(bus.getBusId(), bus);
			if(bus != null)
			{
				return true;
			}
		} catch (Exception e) {
			throw new UpdateFailedException("Failed To Update Provide Proper Details");	
		}
		return null;
	}

	/**Searching the bus **/
	@Override
	public Bus searchBus(int busId) {
		Bus bus=null;

		try {
			for(Map.Entry<Integer, Bus> pair: busDb.entrySet())
			{
				if(pair.getKey().equals(busId))
				{
					return pair.getValue();
				}
			}

			return bus;
		} catch (Exception e) {
			throw new CustomException("Cant Find Bus Provide Proper Bus Id");
		}
	}

	/**Deleting the bus details**/
	@Override
	public Boolean deletebus(int busId) {
		try {
			Bus bus=searchBus(busId);
			bus = busDb.remove(busId);
			if(bus != null)
			{
				return true;
			}else
			{

				return false;
			}
		} catch (Exception e) {

			throw new DeleteFailedException("Delete Failed For this Bus ID");
		}
	}

	/**Admin Login Checks username and password**/
	@Override
	public Boolean adminLogin(int adminId, String password) {
		try {
			admin = adminDb.get(adminId);
			if(admin.getAdminId() == adminId && admin.getPassword().equals(password) )
			{
				return true;
			}
			else
			{
				LoginException exception = new LoginException("LoginException:Wrong password Given");
			}
		} catch (Exception e) {

			throw new LoginException("Login Failed Provide Proper Credentials");
		}
		return false;
	}

	/**Checking the bus route between source and destination**/
	@Override
	public HashMap<Integer, Bus> busBetween(String source, String destination) {
		try {
			Bus bus=null;
			int i= 0;
			HashMap<Integer , Bus> map = new HashMap<>();
			for(Entry<Integer, Bus> entry: busDb.entrySet())
			{
				if(entry.getValue().getSource().equals(source) && entry.getValue().getDestination().equals(destination))
				{
					 bus = (entry.getValue());
					 map.put(i, bus);
				}
				i++;
			}
			return map;
		} catch (Exception e) {
			throw new CustomException("Failed to get Bus");
		}


	}
	/**Add Availablilty while adding Bus**/
	@Override
	public Boolean addAvailability(Available available) {
		try {
			availableDb.put(available.getBusId(), available);
			return true;
		} catch (Exception e) {
			throw new CustomException("Failed To Add Availabliltiy");
		}
	}

	/**Searching AdminId **/
	@Override
	public Admin searchAdmin(int adminId) {
		try {
			Admin admin = null;
			Iterator it = adminDb.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				if (pair.getKey().equals(adminId)) {
					return (Admin) pair.getValue();
				}
			}
			return admin;
		} catch (Exception e) {
			throw new CustomException("LoginException:Provide Proper adminId");
		}
	}

}
