package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.staed.beans.Info;

/**
 * A DAO variant working with Info objects
 * @see DAO
 * @see Info
 */
public class InfoDAO extends DAO<Info>{

	@Override
	Info extractRow(ResultSet rs) {
		try {
			int requestId = rs.getInt(names.requestIdentifier);
			String description = rs.getString(names.infoDesc);
			String location = rs.getString(names.location);
			String justification = rs.getString(names.justification);
			
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
			ps.setInt(2, t.getRequestId());
			ps.setString(3, t.getDescription());
			ps.setString(4, t.getLocation());
			ps.setString(5, t.getJustification());
			
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
		String sql = "SELECT * FROM INFO WHERE " + names.requestIdentifier + " = ?";
		List<Info> list = preparedIterator(prepareStatement(sql));
		return list.isEmpty() ? null : list.get(0);
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
		String sql = "DELETE FROM INFO WHERE " + names.requestIdentifier + " = ?";
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
