package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.staed.beans.*;

public class RequestDAO extends DAO<Request> {
    private String joinedTables = "FROM REQUEST R INNER JOIN EVENTTYPE EVT ON EVT.EVENTTYPEID = R.EVENTTYPEID INNER JOIN EMPLOYEE E ON E.EMPLOYEEID = R.EMPLOYEEID INNER JOIN DEPARTMENT D ON D.DEPARTMENTID = E.DEPARTMENTID INNER JOIN TITLE T ON T.TITLEID = E.TITLEID";

    public Request getRequest(int id) throws SQLException {
        String sql = "SELECT * " + joinedTables + " WHERE REQUESTID = ?";
        PreparedStatement ps = prepareStatement(sql);
        ps.setInt(1, id);
        List<Request> rI = preparedIterator(ps);
        return rI.isEmpty() ? null : rI.get(0);
    }

    public List<Request> getAllRequest() throws SQLException {
        String sql = "SELECT * " + joinedTables;
        return preparedIterator(prepareStatement(sql));
    }    

    public int _DeleteRequest(int id) throws SQLException {
        String sql = "DELETE FROM REQUEST WHERE REQUESTID = ?";
        PreparedStatement ps = prepareCallable(sql);
        ps.setInt(1, id);
        return ps.executeUpdate();
    }

    public int addRequest(Request obj) throws SQLException {
        PreparedStatement ps = packageObj(obj);
        return ps.executeUpdate();
    }
    
    public List<String> getRequestColumns() throws SQLException {
    	String sql = "SELECT column_name FROM USER_TAB_COLUMNS WHERE table_name = 'REQUEST'";
    	PreparedStatement ps = prepareStatement(sql);
    	List<String> list = new ArrayList<>();
    	ResultSet rs = ps.executeQuery();
    	while (rs.next()) {
    		list.add(rs.getString("COLUMN_NAME").toUpperCase());
    	}
    	rs.close();
    	ps.close();
    	
    	return list;
    }

    @Override
    Request extractRow(ResultSet rs) throws SQLException {
        int requestId = rs.getInt("REQUESTID");
        int employeeId = rs.getInt("EMPLOYEEID");
        Date evtTime = rs.getDate("EVENTTIME");
        String evtLoc = rs.getString("EVENTLOCATION");
        String requestDesc = rs.getString("REQUESTDESCRIPTION");
        float cost = rs.getFloat("REIMBURSEMENTCOST");
        String format = rs.getString("GRADINGFORMAT");
        
        String evtName = rs.getString("EVENTTYPENAME");
        int evtId = rs.getInt("EVENTTYPEID");
        SimpleEntry<String, Integer> event = new SimpleEntry<>(evtName, evtId);
        
        String justification = rs.getString("WORKJUSTIFICATION");
        String email = rs.getString("APPROVALEMAIL");
        
        Boolean okdSuper = false;
        if (rs.getInt("DIRECTSUPERVISORAPPROVED") > 0)
            okdSuper = true;
        Boolean okdHead = false;
        if (rs.getInt("DEPARTMENTHEADAPPROVED") > 0)
            okdHead = true;
        Boolean okdBenCo = false;
        if (rs.getInt("BENEFITSCOORDINATORAPPROVED") > 0)
            okdBenCo = true;

        int cutoff = rs.getInt("GRADECUTOFF");
        String status = rs.getString("STATUS");
        
        Boolean urgent = false;
        if (rs.getInt("URGENT") > 0) {
            urgent = true;
        }

        return new Request(requestId, employeeId, evtTime, evtLoc,
            requestDesc, cost, format, event, justification, email,
            okdSuper, okdHead, okdBenCo, cutoff, status, urgent);
    }

	@Override
	PreparedStatement packageObj(Request obj) throws SQLException {
        String sql = "INSERT INTO REQUEST(EMPLOYEEID, EVENTTIME, "
        		+ "EVENTLOCATION, REQUESTDESCRIPTION, REIMBURSEMENTCOST, "
        		+ "GRADINGFORMAT, EVENTTYPEID, WORKJUSTIFICATION, "
        		+ "APPROVALEMAIL, DIRECTSUPERVISORAPPROVED, "
        		+ "DEPARTMENTHEADAPPROVED, BENEFITSCOORDINATORAPPROVED, "
        		+ "GRADECUTOFF, STATUS, URGENT) VALUES "
        		+ "(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?)";
        PreparedStatement ps = prepareStatement(sql);
        ps.setInt(1, obj.getEmployeeId());
        ps.setDate(2, obj.getEventDate());
        ps.setString(3, obj.getLocation());
        ps.setString(4, obj.getDescription());
        ps.setFloat(5, obj.getCost());
        ps.setString(6, obj.getFormat());
        //
        ps.setInt(7, obj.getEvent().getValue());
        //
        ps.setString(8, obj.getJustification());
        ps.setString(9, obj.getApprovalEmail());
        ps.setInt(10, obj.getOkdBySuper() ? 1 : 0);
        ps.setInt(11, obj.getOkdByHead() ? 1 : 0);
        ps.setInt(12, obj.getOkdByBenCo() ? 1 : 0);
        ps.setInt(13, obj.getGradeCutoff());
        ps.setString(14, obj.getStatus());
        ps.setInt(15, obj.getUrgent() ? 1 : 0);
        
        System.out.println(sql);
        System.out.println(obj.getEmployeeId() + ", " + obj.getEventDate() + ", "
        		+ obj.getLocation() + ", " + obj.getDescription() + ", "
        		+ obj.getCost() + ", " + obj.getFormat() + ", " + obj.getEvent().getValue() + ", "
        		+ obj.getJustification() + ", " + obj.getApprovalEmail() + ", "
        		+ (obj.getOkdBySuper() ? 1 : 0) + ", " + (obj.getOkdByHead() ? 1 : 0) + ", "
        		+ (obj.getOkdByBenCo() ? 1 : 0) + ", " + obj.getGradeCutoff() + ", "
        		+ obj.getStatus() + ", " + (obj.getUrgent() ? 1 : 0));
        
        return ps;
	}
}