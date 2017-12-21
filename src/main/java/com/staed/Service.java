package com.staed;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Iterator;
import com.staed.beans.Request;
import com.staed.dao.RequestDAO;
import com.staed.factory.RequestFactory;

// Handle the SQLExceptions in this class
public class Service {
    private static RequestDAO requestDAO = new RequestDAO();
    private RequestFactory rf;
    private Request currentRequest;

    public Service() {
        this.rf = new RequestFactory();
        this.currentRequest = null;
    }

    public Request current() {
        return currentRequest;
    }

    // Should this be allowed?
    public Iterator<Request> getAllRequest() {
        try {
            return requestDAO.getAllRequest().iterator();
        } catch (SQLException ex) {
            return null;
        }
    }

    public boolean getRequest(int id) {
        try {
            currentRequest = requestDAO.getRequest(id);
            return currentRequest != null ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Request generateRequest(int eId, Date date, String loc, String desc,
    float cost, String format, String type, String justify, String email,
    boolean superOk, boolean headOk, boolean benCoOk, int cutoff, String status,
    boolean isUrgent) {
        Request req;
        if (isUrgent)
            req = rf.createRequestUrgent(eId, date, loc, desc, cost, format, type, justify, email, cutoff);
        else
            req = rf.createRequest(eId, date, loc, desc, cost, format, type, justify, email, cutoff);

        req.setSuperAppro(superOk);
        req.setHeadAppro(headOk);
        req.setBenCoAppro(benCoOk);
        req.setStatus(status);

        return req;
    }

    public boolean pushRequest() {
        try {
            return requestDAO.addRequest(currentRequest) > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // TODO: Check old request
    public boolean checkRequest() {
        return false;
    }

    // TODO: Approve old requests
    public boolean approve() {
        return false;
    }

    // TODO: Pass the buck
    public boolean passUpstream() {
        return false;
    }
}