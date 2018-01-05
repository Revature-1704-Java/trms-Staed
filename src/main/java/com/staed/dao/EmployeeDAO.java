package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.staed.beans.Employee;
import com.staed.stores.ColumnNames;

/**
 * A DAO variant working with Employee objects
 * @see DAO
 * @see Employee
 */
public class EmployeeDAO extends DAO<Employee> {
	private final static Logger logger = Logger.getLogger(EmployeeDAO.class);

	@Override
	Employee extractRow(ResultSet rs) {
		try {
			String email = rs.getString(ColumnNames.EMPLOYEEIDENTIFIER);
			String pass = rs.getString(ColumnNames.EMPLOYEEPASSWORD);
			String name = rs.getString(ColumnNames.NAME);
			int typeId = rs.getInt(ColumnNames.EMPLOYEETYPEID);
			String suEm = rs.getString(ColumnNames.SUPERVISOR);
			String hdEm = rs.getString(ColumnNames.DEPTHEAD);
			String bcEm = rs.getString(ColumnNames.BENCO);
			
			return new Employee(email, pass, name, typeId, suEm, hdEm, bcEm);
		} catch (SQLException ex) {
			logger.error("Exception in extractRow - Either param types or null column names", ex);
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
		} catch (SQLException ex) {
			logger.error("Exception in prepareInsert - Check your SQL params", ex);
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
		String sql = "SELECT * FROM EMPLOYEE WHERE " + ColumnNames.EMPLOYEEIDENTIFIER 
				+ " = ? AND " + ColumnNames.EMPLOYEEPASSWORD + " = ?";
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
			logger.error("Exception in loginInfo - Check your Email/Password params", ex);
		}
		return null;
	}
	
	/**
	 * Retrieves the Employee with that email
	 * @param String - The employee's email
	 * @return Either the Employee desired or null
	 */
	public Employee getEmployee(String email) {
		String sql = "SELECT * FROM EMPLOYEE WHERE " + ColumnNames.EMPLOYEEIDENTIFIER + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(1, email);
			
			List<Employee> empIter = preparedIterator(ps);
			return empIter.isEmpty() ? null : empIter.get(0);
		} catch (SQLException ex) {
			logger.error("Exception in getEmployee - Check your Email params", ex);
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
		String sql = "SELECT * FROM EMPLOYEE WHERE "
				+ ColumnNames.SUPERVISOR + " = ? "
				+ "OR " + ColumnNames.DEPTHEAD + " = ? OR " + ColumnNames.BENCO + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(1, managerEmail);
			ps.setString(2, managerEmail);
			ps.setString(3, managerEmail);
			return preparedIterator(ps);
		} catch (SQLException ex) {
			logger.error("Exception in getManaged - Could be SQL params, but more likely to be preparedIterator issues", ex);
		}
		
		return new ArrayList<>();
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
			logger.error("Exception in addEmployee - Some issue with prepareInsert", ex);
			return 0;
		}
	}
	
	/**
	 * Attempts to delete an employee
	 * @param String - The email of the employee to be deleted
	 * @return The number of rows affected
	 */
	public int deleteEmployee(String email) {
		String sql = "DELETE FROM EMPLOYEE WHERE " + ColumnNames.EMPLOYEEIDENTIFIER + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(1, email);
			return ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Exception in deleteEmployee - Check your SQL params", ex);
			return 0;
		}
	}
}