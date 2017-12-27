package com.staed.delegate;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.staed.beans.Request;
import com.staed.services.EmployeeService;
import com.staed.services.RequestService;

public class Interpreter {
	RequestService reqServ;
	EmployeeService empServ;
	
	public Interpreter() {
		reqServ = new RequestService();
		empServ = new EmployeeService();
	}
	
	/**
	 * Attempts to login with the passed in parameters
	 * @return String indicating success or failure
	 */
	public String login() {
		// TODO Get email and pass
		String email = "", pass = "";
		
		if (empServ.login(email, pass))
			return "Logged in";
		else
			return "Incorrect email or password";
	}
    
	/**
	 * Fetch all the requests made by employees subordinate to the current user
	 * @return A JSON String
	 */
    public String display() {
    	Gson gson = new Gson();
    	List<Request> list = new ArrayList<>();
    	
    	// TODO Get manager's email
    	String managerEmail = "";
    	
    	empServ.getSubordinates(managerEmail).forEach(email -> {
    		reqServ.displayAll(email).forEach(request -> {
    			list.add(request);
    		});
    	});
    	
    	return gson.toJson(list);
    }
    
    /**
     * Attempts to insert a new Request to the DB
     * @return String indicating success or failure
     */
    public String submit() {
    	// TODO Retrieve fields
    	String email = null;
    	int evtTypeId = 0;
    	int formatId = 0;
		int state = 0;
    	float cost = 0;
    	LocalDate evtDate = null;
		Period timeMissed = null;
		LocalDate lastReviewed = null;
		
    	if (reqServ.add(email, evtTypeId, formatId, state, cost, evtDate, timeMissed, lastReviewed))
    		return "Added reimbursement request successfully";
    	else
    		return "Failed to add reimbursement request";
    }
    
    /**
     * Attempts to change an existing Request
     * @return String indicating success or failure
     */
    public String update() {
    	reqServ.modifyRequest();
    	return null;
    }
    
    /**
     * Approves the request with the current user's level of power
     */
    public void approveRequest() {
    	// TODO Check Power for Approving
    	reqServ.modifyRequest();
    	
    	// TODO Notify if last Power
    	notifyEmployee();
    }
    
    /**
     * Rejects the request with the current user's level of power
     * @return String with the reason for rejection 
     */
    public String rejectRequest() {
    	// TODO Check Power for Rejection
    	reqServ.modifyRequest();
    	
    	notifyEmployee();
    	
    	return null;
    }
    
    /**
     * Sends an email out to the employee who requested the reimbursement
     * indicating approval or rejection
     */
    public void notifyEmployee() {
    }
}