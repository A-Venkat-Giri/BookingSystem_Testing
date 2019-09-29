package com.bbs.services;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bbs.beans.Admin;
import com.bbs.beans.Available;
import com.bbs.beans.Bus;
import com.bbs.dao.DaoAdmin;
import com.bbs.dao.DaoAdminImpl;
import com.bbs.exception.CustomException;

public class AdminServicImpl implements ServiceAdmin {
	DaoAdmin admin=new DaoAdminImpl();

	@Override
	public Boolean createBus(Bus bus) {
		return admin.createBus(bus);
	}

	@Override
	public Boolean updateBus(Bus bus) {
		return admin.updateBus(bus);
	}

	@Override
	public Bus searchBus(int bus_id) {
		return admin.searchBus(bus_id);
	}

	@Override
	public Boolean deletebus(int bus_id) {
		return admin.deletebus(bus_id);
	}


	@Override
	public Boolean adminLogin(int admin_id, String password) {
		return admin.adminLogin(admin_id, password);
	}

	@Override
	public HashMap<Integer, Bus> busBetween(String source, String destination) {
		return admin.busBetween(source, destination);
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
			 CustomException exception = new CustomException("CustomException:BusId ALREADY Exist");
				exception.getMessage();
		 }
		 return null;
	
	}

	@Override
	public Boolean addAvailability(Available available) {
		return admin.addAvailability(available);
	}

	@Override
	public Admin searchAdmin(int adminId) {
		return admin.searchAdmin(adminId);
	}

}
