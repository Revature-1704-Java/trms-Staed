package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.staed.beans.Info;
import com.staed.stores.ColumnNames;

/**
 * A DAO variant working with Info objects
 * @see DAO
 * @see Info
 */
public class InfoDAO extends DAO<Info>{

	@Override
	Info extractRow(ResultSet rs) {
		try {
			int requestId = rs.getInt(ColumnNames.REQUESTIDENTIFIER);
			String description = rs.getString(ColumnNames.INFODESC);
			String location = rs.getString(ColumnNames.LOCATION);
			String justification = rs.getString(ColumnNames.JUSTIFICATION);
			
			return new Info(requestId, description, location, justification);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	PreparedStatement prepareInsert(Info t) {
		String sql = "INSERT INTO INFO VALUES (?,?,?,?)";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setInt(1, t.getRequestId());
			ps.setString(2, t.getDescription());
			ps.setString(3, t.getLocation());
			ps.setString(4, t.getJustification());
			
			return ps;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Retrieve the info associated with that request
	 * @param int - The request id
	 * @return Either the Info desired or null
	 */
	public Info getInfo(int requestId) {
		String sql = "SELECT * FROM INFO WHERE " + ColumnNames.REQUESTIDENTIFIER + " = ?";
		PreparedStatement ps = prepareStatement(sql);

		try {
			ps.setInt(1, requestId);
			List<Info> list = preparedIterator(ps);
			return list.isEmpty() ? null : list.get(0);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Attempts to insert an Info object into the database
	 * @param Info - The Info to be inserted
	 * @return The number of rows affected
	 */
	public int addInfo(Info info) {
		PreparedStatement ps = prepareInsert(info);
		try {
			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Deletes all Info associated with the specified Request
	 * @param int - The request Id
	 * @return The number of rows affected
	 */
	public int deleteInfo(int requestId) {
		String sql = "DELETE FROM INFO WHERE " + ColumnNames.REQUESTIDENTIFIER + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setInt(1, requestId);
			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
}
