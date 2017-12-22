package com.staed.factory;

import java.time.LocalDate;

import com.staed.beans.Employee;

public class EmployeeFactory {
	public Employee newInstance(String pass,
			String firstName, String lastName, String email, float awarded,
			int titleId, int departmentId, int superId) {
		return new Employee(pass, firstName, lastName, email,
				awarded, titleId, departmentId, superId);
	}
	
	public Employee newInstance(int eId, String pass,
			String fName, String lName, String email, float awarded,
			LocalDate lastAwarded, int titleId, int departmentId, int superId) {
		return new Employee(eId, pass, fName, lName, email, awarded,
							lastAwarded, titleId, departmentId, superId);
	}
}
