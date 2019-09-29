package com.bbs.dao;

import java.util.HashMap;

import com.bbs.beans.Admin;
import com.bbs.beans.Available;
import com.bbs.beans.Bus;
import com.bbs.beans.User;

public interface DaoAdmin {
	public Boolean createBus(Bus bus);
	public Boolean updateBus(Bus bus);
	public Bus searchBus(int bus_id);
	public Boolean deletebus(int bus_id);
	public HashMap<Integer, Bus> busBetween(String source,String destination);
	public Admin searchAdmin(int adminId);
	
	public Boolean adminLogin(int admin_id, String password);
	public Boolean addAvailability(Available available);
}
