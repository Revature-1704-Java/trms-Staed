package com.staed.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.staed.beans.Employee;
import com.staed.factory.EmployeeFactory;

public class EmployeeDAO extends DAO<Employee> {
	EmployeeFactory ef = new EmployeeFactory();
	
	public Employee getEmployee(int id) throws SQLException {
		String sql = "SELECT * FROM EMPLOYEE WHERE EMPLOYEEID = ?";
		PreparedStatement ps = prepareStatement(sql);
		ps.setInt(1, id);
		List<Employee> empIter = preparedIterator(ps);
		return empIter.isEmpty() ? null : empIter.get(0);
	}
	
	public Employee login(String username, String pass) throws SQLException {
		String sql = "SELECT * FROM EMPLOYEE WHERE USERNAME = ? AND PASS = ?";
		PreparedStatement ps = prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, pass);
		List<Employee> empIter = preparedIterator(ps);
		return empIter.isEmpty() ? null : empIter.get(0);
	}

	@Override
	Employee extractRow(ResultSet rs) throws SQLException {
		int eId = rs.getInt("EMPLOYEEID");
		String pass = rs.getString("PASS");
		String fName = rs.getString("FIRSTNAME");
		String lName = rs.getString("LASTNAME");
		String email = rs.getString("EMAIL");
		float awarded = rs.getFloat("AWARDED");
		LocalDate lastAwarded = rs.getDate("LASTAWARDED").toLocalDate();
		int titleId = rs.getInt("TITLEID");
		int departmentId = rs.getInt("DEPARTMENTID");
		int superId = rs.getInt("DIRECTSUPERVISORID");
		
		return ef.newInstance(eId, pass, fName, lName, email, awarded,
							lastAwarded, titleId, departmentId, superId);
	}

	@Override
	PreparedStatement packageObj(Employee t) throws SQLException {
		String sql = "INSERT INTO EMPLOYEE VALUES (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = prepareStatement(sql);
		ps.setInt(1, t.getId());
		ps.setString(2, t.getPassword());
		ps.setString(3, t.getFirstname());
		ps.setString(4, t.getLastname());
		ps.setString(5, t.getEmail());
		ps.setFloat(6, t.getAwarded());
		ps.setDate(7, Date.valueOf(t.getLastAwarded()));
		ps.setInt(8, t.getTitleId());
		ps.setInt(9, t.getDepartmentId());
		ps.setInt(10, t.getSuperId());
		return ps;
	}

}
