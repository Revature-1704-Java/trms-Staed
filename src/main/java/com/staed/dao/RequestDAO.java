package com.staed.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.staed.beans.Request;
import com.staed.stores.ColumnNames;
import com.staed.stores.FieldValueWrapper;

/**
 * A DAO variant working with Request objects
 * @see DAO
 * @see Request
 */
public class RequestDAO extends DAO<Request> {
	private static final String SELECT = "SELECT * FROM REQUEST WHERE ";
	private static final String TARGET_AND = "=? AND";
	
	@Override
	Request extractRow(ResultSet rs) {
		try {
			int id = rs.getInt(ColumnNames.REQUESTIDENTIFIER);
			String email = rs.getString(ColumnNames.EMPLOYEEIDENTIFIER);
			int evtTypeId = rs.getInt(ColumnNames.EVENTTYPEID);
			int formatId = rs.getInt(ColumnNames.FORMATID);
			int state = rs.getInt(ColumnNames.STATE);
			float cost = rs.getFloat(ColumnNames.COST);
			LocalDate evtDate = rs.getDate(ColumnNames.EVENTDATE).toLocalDate();
			Period timeMissed = Period.parse(rs.getString(ColumnNames.WORKMISSED));
			LocalDate lastReviewed = rs.getDate(ColumnNames.LASTREVIEWED).toLocalDate();
			
			return new Request(id, email, evtTypeId, formatId, state, cost, evtDate, timeMissed, lastReviewed);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}

	@Override
	PreparedStatement prepareInsert(Request t) {
		String sql = "INSERT INTO REQUEST VALUES (?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(2, t.getEmail());
			ps.setInt(3, t.getEventTypeId());
			ps.setInt(4, t.getFormatId());
			ps.setInt(5, t.getState());
			ps.setFloat(6, t.getCost());
			ps.setDate(7, Date.valueOf(t.getEventDate()));
			ps.setString(8, t.getTimeMissed().toString());
			ps.setDate(9, Date.valueOf(t.getLastReviewed()));
			
			return ps;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Attempts to retrieve a request regardless of user permission
	 * @param int - The id of the request
	 * @return Either the request desired or null
	 */
	public Request getRequest(int id) {
		String sql = SELECT + ColumnNames.REQUESTIDENTIFIER + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setInt(1,  id);
			List<Request> reqIter = preparedIterator(ps);
			return reqIter.isEmpty() ? null : reqIter.get(0);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Attempt to retrieve the specified request only if it exists
	 * and the user was the employee that created it
	 * @param int - The id of the request
	 * @param String - The email of the requester
	 * @return Either the Request desired or null
	 */
	public Request getRequest(int id, String email) {
		String sql = SELECT + ColumnNames.REQUESTIDENTIFIER + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setInt(1, id);
			List<Request> reqIter = preparedIterator(ps);
			
			if (!reqIter.isEmpty() && reqIter.get(0).getEmail() == email)
				return reqIter.get(0);
			else
				return null;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Given the details of a request, excluding the request id,
	 * find the corresponding request id.
	 * @param Request - The details of the request
	 * @return The request id
	 */
	public int getAddedRequestId(Request request) {
		String sql = SELECT + ColumnNames.EMPLOYEEIDENTIFIER + TARGET_AND 
			+ ColumnNames.STATE + TARGET_AND + ColumnNames.COST + TARGET_AND + ColumnNames.EVENTDATE
			+ TARGET_AND + ColumnNames.LASTREVIEWED + "=?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(1, request.getEmail());
			ps.setInt(2, request.getState());
			ps.setFloat(3, request.getCost());
			ps.setDate(4, Date.valueOf(request.getEventDate()));
			ps.setDate(5, Date.valueOf(request.getLastReviewed()));

			List<Request> reqIter = preparedIterator(ps);
			return !reqIter.isEmpty() ? reqIter.get(0).getId() : 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Shows the user all the requests they created
	 * @param email - The email of the user
	 * @return A list of the requests returned
	 */
	public List<Request> getAllRequest(String email) {
		List<Request> list = new ArrayList<>();
		
		String sql = SELECT + ColumnNames.EMPLOYEEIDENTIFIER + " = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(1, email);
			list = preparedIterator(ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Attempts to insert a Request into the database
	 * @param Request - The request to insert
	 * @return The number of rows affected
	 */
	public int addRequest(Request request) {
		PreparedStatement ps = prepareInsert(request);
		try {
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Attempt to update the specified Request with a given list of name,
	 * value, and type of fields
	 * @param int - The id of the request to update
	 * @param List&lt;FieldValueWrapper&gt; - The list of field name, value, and type
	 * @return The number of rows affected
	 */
	public int updateRequest(int id, List<FieldValueWrapper> fields) {
		StringBuilder sql = new StringBuilder("UPDATE REQUEST SET ");
		for (FieldValueWrapper field : fields) {
			sql.append(field.get().getKey() + " = ?,");
		}
		sql = sql.deleteCharAt(sql.length() - 1);
		sql.append(" WHERE ID = ?");

		fields.add(new FieldValueWrapper("ID", id));
		
		return super.update(sql.toString(), fields);
	}
	
	/**
	 * Attempts to delete a specified request, validity is not checked
	 * @param int - The id of the request to be deleted
	 * @return The number of rows affected
	 */
	public int deleteRequest(int id) {
		String sql = "DELETE FROM REQUEST WHERE " + ColumnNames.REQUESTIDENTIFIER + " = ?";
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