package com.staed.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.staed.beans.Note;

public class NoteDAO extends DAO<Note> {

	@Override
	Note extractRow(ResultSet rs) {
		try {
			int id = rs.getInt("ID");
			int requestId = rs.getInt("REQUESTID");
			String managerEmail = rs.getString("MANAGEREMAIL");
			LocalDate timeActedOn = rs.getDate("TIMEACTEDON").toLocalDate();
			float newAmount = rs.getFloat("NEWAMOUNT");
			String reason = rs.getString("REASON");
			
			return new Note(id, requestId, managerEmail, timeActedOn, newAmount, reason);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	PreparedStatement prepareInsert(Note t) {
		String sql = "INSERT INTO NOTE VALUES (?,?,?,?,?,?)";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setInt(1, t.getId());
			ps.setInt(2, t.getRequestId());
			ps.setString(3, t.getManagerEmail());
			ps.setDate(4, Date.valueOf(t.getTimeActedOn()));
			ps.setFloat(5, t.getNewAmount());
			ps.setString(6, t.getReason());
			
			return ps;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public List<Note> getNotes(int requestId) {
		List<Note> list = new ArrayList<>();
		String sql = "SELECT * FROM NOTE WHERE REQUESTID = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setInt(1, requestId);
			list = preparedIterator(ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return list;
	}
	
	public int addNote(Note t) {
		PreparedStatement ps = prepareInsert(t);
		try {
			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	public int deleteNote(int id) {
		String sql = "DELETE FROM NOTE WHERE ID = ?";
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
