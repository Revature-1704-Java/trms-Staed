package com.staed.services;

import java.util.ArrayList;
import java.util.List;

import com.staed.beans.Employee;
import com.staed.dao.EmployeeDAO;

/**
 * A Service that handles Employees and all the objects associated with them.
 * It handles the messy details of actually providing the services required.
 */
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
	
	/**
	 * Checks to see if an employee with this email and password exists in the
	 * database
	 * @param String - The employee's email
	 * @param String - The employee's password
	 * @return A boolean indicating success or failure
	 */
	public boolean login(String email, String pass) {
		user = empDAO.loginInfo(email, pass);
		
		if (user != null)
			return true;
		else
			return false;
	}
	
	/**
	 * Returns all the employees that have the passed in email
	 * as a supervisor, department head, or benefits coordinator
	 * @param String - The email of the manager
	 * @return - A list of emails of the employees under this manager
	 */
	public List<String> getSubordinates(String email) {
		List<String> list = new ArrayList<>();
		
		empDAO.getManaged(email).forEach(emp -> {
			list.add(emp.getEmail());
		});
		
		return list;
	}
}