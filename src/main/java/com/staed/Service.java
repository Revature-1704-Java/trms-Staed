package com.staed;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.List;

import com.staed.beans.Employee;
import com.staed.beans.Request;
import com.staed.dao.EmployeeDAO;
import com.staed.dao.EventTypeDAO;
import com.staed.dao.RequestDAO;
import com.staed.factory.RequestFactory;
import com.staed.stores.EventType;

// Handle the SQLExceptions in this class
public class Service {
    private static RequestDAO requestDAO = new RequestDAO();
    private static EventTypeDAO eventDAO = new EventTypeDAO();
    private static EmployeeDAO employeeDAO = new EmployeeDAO();
    
    private RequestFactory rf;
    private Request currentRequest;
    private Employee currentUser;
    
    public Service() {
        this.rf = new RequestFactory();
        this.currentRequest = null;
        this.currentUser = null;
        
        try {
			eventDAO.populateEventTypes();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void close() {
    	requestDAO.close();
    	eventDAO.close();
    }

    public Request current() {
        return currentRequest;
    }
    
    public EventType getTypes() {
    	return eventDAO.getTypes();
    }
    
    public Employee getUser() {
    	return currentUser;
    }
    
    public List<String> getPossibleFields() {
    	try {
    		return requestDAO.getRequestColumns();
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
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
    
    public boolean login(String username, String password) {
    	try {
    		currentUser = employeeDAO.login(username, password);
    		if (currentUser == null) 
    			return true;
    		else
    			return true;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return false;
    }

    public void generateRequest(int eId, LocalDate date, String loc, String desc,
    float cost, String format, String type, String justify, String email,
    boolean superOk, boolean headOk, boolean benCoOk, int cutoff, String status,
    boolean isUrgent) {
    	SimpleEntry<String, Integer> event;
    	if (eventDAO.getTypes().getKeys().contains(type))
    		event = new SimpleEntry<>(type, eventDAO.getTypes().getValue(type));
    	else
    		event = new SimpleEntry<>("Other", eventDAO.getTypes().getValue("Other"));
    	
        Request req;
        if (isUrgent)
            req = rf.newUrgentInstance(eId, date, loc, desc, cost, format, event, justify, email, cutoff);
        else
            req = rf.newInstance(eId, date, loc, desc, cost, format, event, justify, email, cutoff);

        req.setOkdBySuper(superOk);
        req.setOkdByHead(headOk);
        req.setOkdByBenCo(benCoOk);
        req.setStatus(status);
        
        req.setRequestId(0);

        currentRequest = req;
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