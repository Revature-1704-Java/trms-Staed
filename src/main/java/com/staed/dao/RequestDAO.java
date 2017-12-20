package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Date;

import com.staed.beans.*;
import com.staed.enums.EventType;

public class RequestDAO extends DAO<Request> {
    private String joinedTables = "FROM REQUEST R INNER JOIN EVENTTYPE EVT ON EVT.EVENTTYPEID = R.EVENTTYPEID INNER JOIN EMPLOYEE E ON E.EMPLOYEEID = R.EMPLOYEEID INNER JOIN DEPARTMENT D ON D.DEPARTMENTID = E.DEPARTMENTID INNER JOIN TITLE T ON T.TITLEID = E.TITLEID";

    public Request getRequest(int id) throws SQLException {
        String sql = "SELECT * " + joinedTables + " WHERE REQUESTID = ?";
        PreparedStatement ps = prepareStatement(sql);
        ps.setInt(1, id);
        List<Request> rI = resultIterator(ps);
        return rI.isEmpty() ? null : rI.get(0);
    }

    public List<Request> getAllRequest() throws SQLException {
        String sql = "SELECT * " + joinedTables;
        return resultIterator(prepareStatement(sql));
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

    @Override
    Request extractRow(ResultSet rs) throws SQLException {
        int requestId = rs.getInt("REQUESTID");
        int employeeId = rs.getInt("EMPLOYEEID");
        Date evtTime = rs.getDate("EVENTTIME");
        String evtLoc = rs.getString("EVENTLOCATION");
        String requestDesc = rs.getString("REQUESTDESCRIPTION");
        float cost = rs.getFloat("REIMBURSEMENTCOST");
        String format = rs.getString("GRADINGFORMAT");

        int evtTypeId = rs.getInt("EVENTTYPEID");
        EventType evtType;
        switch (rs.getString("EVENTTYPENAME").toLowerCase()) {
            case "university course":
                evtType = EventType.UniversityCourse;
                break;
            case "seminar":
                evtType = EventType.Seminars;
                break;
            case "certification preparation course":
                evtType = EventType.CertificationPreparation;
                break;
            case "certification":
                evtType = EventType.Certification;
                break;
            case "technical training":
                evtType = EventType.TechnicalTraining;
                break;
            default:
                evtType = EventType.Other;
                break;
        }
        
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
            requestDesc, cost, format, evtTypeId, evtType, justification, email,
            okdSuper, okdHead, okdBenCo, cutoff, status, urgent);
    }

	@Override
	PreparedStatement packageObj(Request obj) throws SQLException {
        String sql = "INSERT INTO REQUEST VALUES (0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = prepareStatement(sql);
        ps.setInt(1, 0);
        ps.setInt(2, obj.getEmployeeId());
        ps.setDate(3, obj.getDate());
        ps.setString(4, obj.getLocation());
        ps.setString(5, obj.getDescription());
        ps.setFloat(6, obj.getCost());
        ps.setString(7, obj.getFormat());
        ps.setInt(8, obj.getEventType().getKey());
        ps.setString(9, obj.getJustification());
        ps.setString(10, obj.getApprovalEmail());
        ps.setInt(11, obj.okdBySuper() ? 1 : 0);
        ps.setInt(12, obj.okdByHead() ? 1 : 0);
        ps.setInt(13, obj.okdByBenCo() ? 1 : 0);
        ps.setInt(14, obj.getCutoff());
        ps.setString(15, obj.getStatus());
        ps.setInt(16, obj.isUrgent() ? 1 : 0);
        return ps;
	}
}