package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.staed.beans.EventType;
import com.staed.stores.ColumnNames;

public class EventTypeDAO extends DAO<EventType> {
	private final static Logger logger = Logger.getLogger(EventTypeDAO.class);
	
	@Override
	EventType extractRow(ResultSet rs) {
		try {
			int id = rs.getInt(ColumnNames.EVENTTYPEID);
			String type = rs.getString(ColumnNames.EVENTTYPENAME);
			int compensation = rs.getInt(ColumnNames.COMPENSATION);
			return new EventType(id, type, compensation);
		} catch (SQLException ex) {
			logger.error("Exception in extractRow - Check your SQL params", ex);
			return null;
		}
	}

	@Override
	PreparedStatement prepareInsert(EventType eventType) {
		String sql = "INSERT INTO GRADINGFORMAT (EVENTTYPENAME, COMPENSATION) VALUES (?,?)";
		PreparedStatement ps = prepareStatement(sql);
		
		try {
			ps.setString(1, eventType.getName());
			ps.setInt(2, eventType.getCompensation());
			return ps;
		} catch (SQLException ex) {
			logger.error("Exception in prepareInsert - Check your SQL params", ex);
			return null;
		}
	}
	
	public int idFromName(String name) {
		String sql = "SELECT * FROM EVENTTYPE WHERE " + ColumnNames.EVENTTYPENAME + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		
		try {
			ps.setString(1, name);
			List<EventType> fmtIter = preparedIterator(ps);
			if (fmtIter.isEmpty())
				return 0;
			else
				return fmtIter.get(0).getId();
		} catch (SQLException ex) {
			logger.error("Exception in idFromName - Check your SQL params, did you pass in a String?", ex);
			return 0;
		}
	}
}
