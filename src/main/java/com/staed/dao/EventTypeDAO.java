package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.staed.beans.EventType;

public class EventTypeDAO extends DAO<EventType> {
	@Override
	EventType extractRow(ResultSet rs) {
		try {
			int id = rs.getInt(names.eventTypeId);
			String type = rs.getString(names.eventTypeName);
			int compensation = rs.getInt(names.compensation);
			return new EventType(id, type, compensation);
		} catch (SQLException ex) {
			ex.printStackTrace();
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
			ex.printStackTrace();
			return null;
		}
	}
	
	public int idFromName(String name) {
		String sql = "SELECT " + names.eventTypeId + " FROM EVENTTYPE WHERE " + names.eventTypeName + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		
		try {
			ps.setString(1, name);
			List<EventType> fmtIter = preparedIterator(ps);
			if (fmtIter.isEmpty())
				return 0;
			else
				return fmtIter.get(0).getId();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
}
