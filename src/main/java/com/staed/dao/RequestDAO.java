package com.staed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Date;

import com.staed.beans.*;
import com.staed.enums.EventType;
import com.staed.enums.GradingFormat;
//import com.staed.factory.*;
import com.staed.*;

public class RequestDAO {
    //private static RequestFactory rf = new RequestFactory();

    public Request getRequest(int rId) {
        PreparedStatement ps = null;
        Request request = null;
        ResultSet rs = null;

        try (Connection conn = ConnectionUtil.getConnection()) {
            StringBuilder sqlBuild = new StringBuilder();
            sqlBuild.append("SELECT * FROM REQUEST R ");
            sqlBuild.append("INNER JOIN EVENTTYPE E ");
            sqlBuild.append("ON R.EVENTTYPEID = E.EVENTTYPEID ");

            sqlBuild.append("INNER JOIN GRADINGFORMAT G ");
            sqlBuild.append("ON R.GRADINGFORMATID = G.GRADINGFORMATID ");

            sqlBuild.append("INNER JOIN STATUS S ");
            sqlBuild.append("ON R.STATUSID = S.STATUSID ");

            sqlBuild.append("WHERE REQUESTID = ?");

            String sql = sqlBuild.toString();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, rId);
            rs = ps.executeQuery();
            while(rs.next()) {
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

                request = new Request(rId, employeeId, evtTime, evtLoc,
                    requestDesc, cost, format, evtType, justification, email,
                    okdSuper, okdHead, okdBenCo, cutoff, status, urgent);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.getMessage();
        }

        return request;
    }
}