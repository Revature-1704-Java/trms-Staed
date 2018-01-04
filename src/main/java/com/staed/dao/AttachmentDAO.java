package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.staed.beans.Attachment;
import com.staed.stores.ColumnNames;

/**
 * A DAO variant working with Attachment objects
 * @see DAO
 * @see Attachment
 */
public class AttachmentDAO extends DAO<Attachment> {

	@Override
	Attachment extractRow(ResultSet rs) {
		try {
			String filename = rs.getString(ColumnNames.ATTACHMENTIDENTIFIER);
			int requestId = rs.getInt(ColumnNames.REQUESTIDENTIFIER);
			int approvedAtState = rs.getInt(ColumnNames.APPROVEDATSTATE);
			String description = rs.getString(ColumnNames.ATTACHMENTDESC);
			
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
	
	/**
	 * Retrieves all the attachments associated with a specified request
	 * @param int - The id of the request
	 * @return The list of attachments
	 */
	public List<Attachment> getAttachments(int requestId) {
		List<Attachment> list = new ArrayList<>();
		
		String sql = "SELECT * FROM ATTACHMENT WHERE " + ColumnNames.REQUESTIDENTIFIER + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setInt(1, requestId);
			list = preparedIterator(ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Attempts to insert an Attachment
	 * @param Attachment The Attachment to be inserted
	 * @return The number of rows affected
	 */
	public int addAttachment(Attachment attachment) {
		PreparedStatement ps = prepareInsert(attachment);
		try {
			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Attempts to delete an Attachment
	 * @param String - The filename of the file associated with this Attachment
	 * @return The number of rows affected
	 */
	public int deleteAttachment(int id) {
		String sql = "DELETE FROM ATTACHMENT WHERE " + ColumnNames.ATTACHMENTIDENTIFIER + " = ?";
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
