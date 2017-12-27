package com.staed.services;

import java.util.ArrayList;
import java.util.List;

import com.staed.beans.Employee;
import com.staed.dao.EmployeeDAO;

public class EmployeeService extends Service {
	private static EmployeeDAO empDAO;
	private Employee user;
	
	public EmployeeService() {
		empDAO = new EmployeeDAO();
		user = null;
	}
	
	@Override
	public void close() {
		empDAO.close();
		user = null;
	}
	
	public Employee getUser() {
		return user;
	}
	
	public boolean login(String email, String pass) {
		user = empDAO.loginInfo(email, pass);
		
		if (user != null)
			return true;
		else
			return false;
	}
	
	public List<String> getSubordinates(String email) {
		List<String> list = new ArrayList<>();
		
		empDAO.getManaged(email).forEach(emp -> {
			list.add(emp.getEmail());
		});
		
		return list;
	}
}