package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Date;

import com.staed.beans.*;
import com.staed.enums.EventType;
import com.staed.enums.GradingFormat;

public class RequestDAO extends DAO {
    private String joinedTables = "FROM REQUEST R INNER JOIN EVENTTYPE EVT ON EVT.EVENTTYPEID = R.EVENTTYPEID INNER JOIN GRADINGFORMAT G ON G.GRADINGFORMATID = R.GRADINGFORMATID INNER JOIN STATUS S ON S.STATUSID = R.STATUSID INNER JOIN EMPLOYEE E ON E.EMPLOYEEID = R.EMPLOYEEID INNER JOIN DEPARTMENT D ON D.DEPARTMENTID = E.DEPARTMENTID INNER JOIN TITLE T ON T.TITLEID = E.TITLEID";

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
        CallableStatement cs = prepareCallable(sql);
        cs.setInt(1, id);
        resultIterator(cs);
        return cs.getInt(1); // TODO: Might be invalid here
    }

    Request extractRow(ResultSet rs) throws SQLException {
        int requestId = rs.getInt("REQUESTID");
        int employeeId = rs.getInt("EMPLOYEEID");
        Date evtTime = rs.getDate("EVENTTIME");
        String evtLoc = rs.getString("EVENTLOCATION");
        String requestDesc = rs.getString("REQUESTDESCRIPTION");
        float cost = rs.getFloat("REIMBURSEMENTCOST");
        
        GradingFormat format = GradingFormat.Grade;
        if (rs.getString("GRADINGFORMATNAME").toLowerCase().equals("presentation")) {
            format = GradingFormat.Presentation;
        }

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
        String status = rs.getString("STATUSNAME");
        
        Boolean urgent = false;
        if (rs.getInt("URGENT") > 0) {
            urgent = true;
        }

        return new Request(requestId, employeeId, evtTime, evtLoc,
            requestDesc, cost, format, evtType, justification, email,
            okdSuper, okdHead, okdBenCo, cutoff, status, urgent);
    }
}