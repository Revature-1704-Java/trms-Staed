package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.staed.beans.Employee;

public class EmployeeDAO extends DAO<Employee> {

	@Override
	Employee extractRow(ResultSet rs) {
		try {
			String email = rs.getString("EMAIL");
			String pass = rs.getString("PASSWORD");
			String name = rs.getString("NAME");
			int typeId = rs.getInt("EMPLOYEETYPEID");
			
			String suEm = rs.getString("SUPER");
			String hdEm = rs.getString("HEAD");
			String bcEm = rs.getString("BENCO");
			
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
	
	public Employee loginInfo(String email, String pass) {
		String sql = "SELECT * FROM EMPLOYEE WHERE USERNAME = ? AND PASS = ?";
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
	
	public Employee getEmployee(String email) {
		String sql = "SELECT * FROM EMPLOYEE WHERE EMAIL = ?";
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
	
	public List<Employee> getManaged(String managerEmail) {
		String sql = "SELECT EMAIL FROM EMPLOYEE WHERE SUPERVISOR = ? "
				+ "OR HEAD = ? OR BENCO = ?";
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
}