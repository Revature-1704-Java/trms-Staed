package com.staed;

import com.staed.beans.Request;
import com.staed.dao.RequestDAO;

// Handle the SQLExceptions in this class
public class Service {
    private static RequestDAO requestDAO;
    private Request currentRequest;

    public Service() {
        this.requestDAO = new RequestDAO();
        this.currentRequest = null;
    }

    // Add new request
    public boolean addRequest() {
        return requestDAO.addRequest() > 0 ? true : false;
    }

    // Check old request

    // Approve old requests

    // Pass the buck
}