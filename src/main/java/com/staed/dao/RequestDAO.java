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

import stores.FieldValueWrapper;

public class RequestDAO extends DAO<Request> {
	
	@Override
	Request extractRow(ResultSet rs) {
		try {
			int id = rs.getInt("ID");
			String email = rs.getString("EMPLOYEEEMAIL");
			int evtTypeId = rs.getInt("EVENTTYPEID");
			int formatId = rs.getInt("GRADEFORMATID");
			int state = rs.getInt("STATE");
			float cost = rs.getFloat("COST");
			LocalDate evtDate = rs.getDate("EVENTDATE").toLocalDate();
			Period timeMissed = Period.parse(rs.getString("WORKTIMEMISSED"));
			LocalDate lastReviewed = rs.getDate("LASTREVIEWED").toLocalDate();
			
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

	public Request getRequest(int id, String email) {
		String sql = "SELECT * FROM REQUEST WHERE ID = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setInt(1, id);
			List<Request> reqIter = preparedIterator(ps);
			
			if (!reqIter.isEmpty())
				return reqIter.get(0);
			else if (reqIter.get(0).getEmail() != email) {
				System.out.println("You don't permission to view that");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public int getAddedRequestId(Request request) {
		String sql = "SELECT * FROM REQUEST WHERE EMPLOYEEEMAIL=? AND "
			+ "STATE=? AND COST=? AND EVENTDATE=? AND LASTREVIEWED=?";
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
	
	public List<Request> getAllRequest(String email) {
		List<Request> list = new ArrayList<>();
		
		String sql = "SELECT * FROM REQUEST WHERE EMPLOYEEEMAIL = ?";
		PreparedStatement ps = prepareStatement(sql);
		try {
			ps.setString(1, email);
			list = preparedIterator(ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public int addRequest(Request Obj) {
		PreparedStatement ps = prepareInsert(Obj);
		try {
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int updateRequest(int id, List<FieldValueWrapper> fields) {
		String sql = "UPDATE REQUEST SET ";
		for (FieldValueWrapper field : fields) {
			sql += field.get().getKey() + " = ?,";
		}
		sql = sql.substring(0, sql.lastIndexOf(','));
		sql += " WHERE ID = ?";
		
		return super.update(sql, fields);
	}
	
	public int deleteRequest(int id) {
		String sql = "DELETE FROM REQUEST WHERE ID = ?";
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