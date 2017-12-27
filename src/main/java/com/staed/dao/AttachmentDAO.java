package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.staed.beans.Attachment;

public class AttachmentDAO extends DAO<Attachment> {

	@Override
	Attachment extractRow(ResultSet rs) {
		try {
			String filename = rs.getString("FILENAME");
			int requestId = rs.getInt("REQUESTID");
			int approvedAtState = rs.getInt("APPROVEDATSTATE");
			String description = rs.getString("DESCRIPTION");
			
			return new Attachment(filename, requestId, approvedAtState, description);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	PreparedStatement prepareInsert(Attachment t) {
		String sql = "INSERT INTO ATTACHMENT VALUES (?,?,?,?)";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(1, t.getFilename());
			ps.setInt(2, t.getRequestId());
			ps.setInt(3, t.getApprovedAtState());
			ps.setString(4, t.getDescription());
			
			return ps;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public List<Attachment> getAttachments(int requestId) {
		List<Attachment> list = new ArrayList<>();
		
		String sql = "SELECT * FROM ATTACHMENT WHERE REQUESTID = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setInt(1, requestId);
			list = preparedIterator(ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public int addAttachment(Attachment t) {
		PreparedStatement ps = prepareInsert(t);
		try {
			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	public int deleteAttachment(int id) {
		String sql = "DELETE FROM ATTACHMENT WHERE ID = ?";
		PreparedStatement ps = prepareCallable(sql);
		try {
			ps.setInt(1, id);
			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
}
