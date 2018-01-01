package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeTypeDAO extends DAO<Integer> {
	
	/**
	 * Fetches the Power level associated with that employee type
	 * @param int - The id of the employee type
	 * @return The Power level
	 */
	public int getPower(int typeId) {
		String sql = "SELECT * FROM EMPLOYEETYPE WHERE " 
				   + names.employeeTypeId + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		
		List<Integer> list = preparedIterator(ps);
		return list.isEmpty() ? 0 : list.get(0);
	}

	@Override
	Integer extractRow(ResultSet rs) {
		try {
			return rs.getInt("POWER");
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Users should not be able to insert new roles, so this only returns null
	 * @param Integer
	 * @return Null
	 */
	@Override
	PreparedStatement prepareInsert(Integer t) {
		// TODO Auto-generated method stub
		return null;
	}

}