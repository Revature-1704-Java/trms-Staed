package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.staed.beans.Employee;

/**
 * A DAO variant working with Employee objects
 * @see DAO
 * @see Employee
 */
public class EmployeeDAO extends DAO<Employee> {

	@Override
	Employee extractRow(ResultSet rs) {
		try {
			String email = rs.getString(names.employeeIdentifier);
			String pass = rs.getString(names.pass);
			String name = rs.getString(names.name);
			int typeId = rs.getInt(names.employeeTypeId);
			
			String suEm = rs.getString(names.supervisor);
			String hdEm = rs.getString(names.deptHead);
			String bcEm = rs.getString(names.benCo);
			
			return new Employee(email, pass, name, typeId, suEm, hdEm, bcEm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	PreparedStatement prepareInsert(Employee t) {
		String sql = "INSERT INTO EMPLOYEE VALUES (?,?,?,?)";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(1,  t.getEmail());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getName());
			ps.setInt(4, t.getTypeId());
			
			return ps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns the Employee that matches that email and password.
	 * If a non-null result is returned, it means they exist.
	 * @param String - The Employee's email
	 * @param String - The Employee's password
	 * @return Either the Employee matching the input or null
	 */
	public Employee loginInfo(String email, String pass) {
		String sql = "SELECT * FROM EMPLOYEE WHERE " + names.employeeIdentifier 
				+ " = ? AND " + names.pass + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(1, email);
			ps.setString(2, pass);
			
			List<Employee> empIter = preparedIterator(ps);
			if (empIter.isEmpty())
				return null;
			else
				return empIter.get(0);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Retrieves the Employee with that email
	 * @param String - The employee's email
	 * @return Either the Employee desired or null
	 */
	public Employee getEmployee(String email) {
		String sql = "SELECT * FROM EMPLOYEE WHERE " + names.employeeIdentifier + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(1, email);
			
			List<Employee> empIter = preparedIterator(ps);
			return empIter.isEmpty() ? null : empIter.get(0);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Retrieves a list of employees that have this email as
	 * their supervisor, department head, or benefits coordinator
	 * @param String - The email of this manager
	 * @return A list of employees
	 */
	public List<Employee> getManaged(String managerEmail) {
		String sql = "SELECT " + names.employeeIdentifier + " FROM EMPLOYEE WHERE "
				+ names.supervisor + " = ? "
				+ "OR " + names.deptHead + " = ? OR " + names.benCo + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(1, managerEmail);
			ps.setString(2, managerEmail);
			ps.setString(3, managerEmail);
			return preparedIterator(ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Attempts to add an Employee to the database
	 * @param Employee - The Employee to be inserted
	 * @return The number of rows affected
	 */
	public int addEmployee(Employee employee) {
		PreparedStatement ps = prepareInsert(employee);
		try {
			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Attempts to delete an employee
	 * @param String - The email of the employee to be deleted
	 * @return The number of rows affected
	 */
	public int deleteEmployee(String email) {
		String sql = "DELETE FROM EMPLOYEE WHERE " + names.employeeIdentifier + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(1, email);
			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
}