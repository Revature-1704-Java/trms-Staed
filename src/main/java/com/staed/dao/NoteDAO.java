package com.staed.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.staed.beans.Note;
import com.staed.stores.ColumnNames;

/**
 * A DAO variant working with Note objects
 * @see DAO
 * @see Note
 *
 */
public class NoteDAO extends DAO<Note> {
	public final static Logger logger = Logger.getLogger(NoteDAO.class);

	@Override
	Note extractRow(ResultSet rs) {
		try {
			int id = rs.getInt(ColumnNames.NOTEIDENTIFIER);
			int requestId = rs.getInt(ColumnNames.REQUESTIDENTIFIER);
			String managerEmail = rs.getString(ColumnNames.MANAGEREMAIL);
			LocalDate timeActedOn = rs.getDate(ColumnNames.TIMEACTEDON).toLocalDate();
			float newAmount = rs.getFloat(ColumnNames.NEWAMOUNT);
			String reason = rs.getString(ColumnNames.NOTEREASON);
			
			return new Note(id, requestId, managerEmail, timeActedOn, newAmount, reason);
		} catch (SQLException ex) {
			logger.error("Exception in extractRow - Check your SQL params", ex);
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
			logger.error("Exception in prepareInsert - Check your SQL params", ex);
		}
		return null;
	}

	/**
	 * Retrieve all the notes associated with the specified request
	 * @param int - The id of the request
	 * @return A list of notes
	 */
	public List<Note> getNotes(int requestId) {
		List<Note> list = new ArrayList<>();
		String sql = "SELECT * FROM NOTE WHERE " + ColumnNames.REQUESTIDENTIFIER + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setInt(1, requestId);
			list = preparedIterator(ps);
		} catch (SQLException ex) {
			logger.error("Exception in getNotes - Likely some issue with preparedIterator", ex);
		}
		
		return list;
	}
	
	/**
	 * Attempts to add a Note to the database
	 * @param Note - The Note to be inserted
	 * @return The number of rows affected
	 */
	public int addNote(Note note) {
		PreparedStatement ps = prepareInsert(note);
		try {
			return ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Exception in addNote - Some issue with prepareInsert", ex);
			return 0;
		}
	}
	
	/**
	 * Attempts to delete the specified Note
	 * @param int - The id of the Note to be deleted
	 * @return The number of rows affected
	 */
	public int deleteNote(int id) {
		String sql = "DELETE FROM NOTE WHERE " + ColumnNames.NOTEIDENTIFIER + " = ?";
		PreparedStatement ps = prepareCallable(sql);
		try {
			ps.setInt(1, id);
			return ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Exception in deleteNote - Some issue with prepareCallable from DAO (abstract parent)", ex);
			return 0;
		}
	}
}
