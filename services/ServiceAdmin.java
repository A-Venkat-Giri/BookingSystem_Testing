package com.bbs.services;

import java.util.HashMap;

import com.bbs.beans.Admin;
import com.bbs.beans.Available;
import com.bbs.beans.Bus;

public interface ServiceAdmin {
	public Boolean createBus(Bus bus);
	public Boolean updateBus(Bus bus);
	public Bus searchBus(int bus_id);
	public Boolean deletebus(int bus_id);
	public Boolean adminLogin(int admin_id, String password);	
	public HashMap<Integer, Bus> busBetween(String source,String destination);
	public String checkUserIdAndBookinIdAndBusId(String number);
	public Boolean addAvailability(Available available);
	public Admin searchAdmin(int adminId);


}
