package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.staed.beans.Info;

public class InfoDAO extends DAO<Info>{

	@Override
	Info extractRow(ResultSet rs) {
		try {
			int requestId = rs.getInt("REQUESTID");
			String description = rs.getString("DESCRIPTION");
			String location = rs.getString("LOCATION");
			String justification = rs.getString("JUSTIFICATION");
			
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
	
	public Info getInfo(int requestId) {
		String sql = "SELECT * FROM INFO WHERE REQUESTID = ?";
		List<Info> list = preparedIterator(prepareStatement(sql));
		return list.isEmpty() ? null : list.get(0);
	}
	
	public int addInfo(Info t) {
		PreparedStatement ps = prepareInsert(t);
		try {
			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	public int deleteInfo(int requestId) {
		String sql = "DELETE FROM INFO WHERE REQUESTID = ?";
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
