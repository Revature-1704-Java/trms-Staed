package com.staed.delegate;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.staed.beans.Attachment;
import com.staed.beans.Info;
import com.staed.beans.Note;
import com.staed.beans.Request;
import com.staed.services.EmployeeService;
import com.staed.services.RequestService;
import com.staed.stores.FieldValueWrapper;

/**
 * Passes along the given inputs to the appropriate service.
 * In a sense, a manager for services. It interprets and returns the results of 
 * actions on the services.
 */
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
    public String display(String managerEmail) {
    	Gson gson = new Gson();
    	List<Request> list = new ArrayList<>();
    	
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
	public String submit(Request request, Info info, List<Attachment> attachmentList, List<Note> noteList) {
		if (reqServ.add(request, info, attachmentList, noteList)) {
    		return "Added reimbursement request successfully";
		} else {
			return "Failed to add reimbursement request";
		}
    }
    
    /**
     * Attempts to change an existing Request
     * @param int - The request Id
     * @return String indicating success or failure
     */
    public String update(int id, List<FieldValueWrapper> fields) {
    	// TODO Handle FieldValueWrappers upstream in the servlet
    	
    	if (reqServ.modifyRequest(id, fields)) {
			return "Successfully updated reimbursement";
		} else {
			return "Failed to update reimbursement";
		}
    }
    
    /**
     * Approves the request with the current user's level of power
     * @param int - Request id
     */
    public void approveRequest(int id, List<FieldValueWrapper> fields) {
    	// TODO Handle FieldValueWrappers upstream in the servlet
    	
    	// TODO Check Power for Approving
    	reqServ.modifyRequest(id, fields);
    	
    	// TODO Notify if last Power
    	notifyEmployee();
    }
    
    /**
     * Rejects the request with the current user's level of power
     * @param int - Request id
     * @return String with the reason for rejection 
     */
    public String rejectRequest(int id, List<FieldValueWrapper> fields) {
    	// TODO Handle FieldValueWrappers upstream in the servlet
    	
    	// TODO Check Power for Rejection
    	reqServ.modifyRequest(id, fields);
    	
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