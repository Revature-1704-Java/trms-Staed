package com.staed.delegate;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
	private RequestService reqServ;
	private EmployeeService empServ;
	private Gson gson;

	private static final int lastPower = 3;
	
	public Interpreter() {
		reqServ = new RequestService();
		empServ = new EmployeeService();
		gson = new Gson();
	}
	
	/**
	 * Attempts to login with the passed in parameters
	 * @return A JsonObject of the form {state: ..., content: ...}
	 */
	public JsonObject login(String email, String pass) {
		JsonObject res = new JsonObject();
		if (empServ.login(email, pass)) {
			addState(res, true);
			addContent(res, "Logged in");
		} else {
			addState(res, false);
			addContent(res, "Incorrect email or password");
		}
		return res;
	}
    
	/**
	 * Fetch all the requests made by employees subordinate to the current user
	 * @return A JsonObject of the form {state: ..., content: ...}
	 */
    public JsonObject display(String managerEmail) {
		JsonObject res = new JsonObject();
    	List<Request> list = new ArrayList<>();
    	
    	list.addAll(reqServ.displayAll(managerEmail));
    	
    	empServ.getSubordinates(managerEmail).forEach(email -> {
    		reqServ.displayAll(email).forEach(request -> {
    			list.add(request);
    		});
    	});
		
		addContent(res, gson.toJsonTree(list).getAsJsonObject());
		addState(res, true);
    	return res;
    }
    
    /**
     * Attempts to insert a new Request to the DB
     * @return A JsonObject of the form {state: ..., content: ...}
     */
	public JsonObject submit(HashMap<String, String> requestMap, List<HashMap<String, String>> attachmentMaps) {
		String employeeEmail = requestMap.containsKey("email") ? requestMap.get("email") : null;
		int evtTypeId = requestMap.containsKey("event type") ? reqServ.eventTypeNameToId(requestMap.get("event type")) : null;
		int formatId = requestMap.containsKey("grading format") ? reqServ.gradingFormatNameToId(requestMap.get("grading format")) : null;
		int state = requestMap.containsKey("state") ? Integer.parseInt(requestMap.get("state")) : null;
		float cost = requestMap.containsKey("cost") ? Float.parseFloat(requestMap.get("cost")) : null;
		LocalDate evtDate = requestMap.containsKey("event date") ? LocalDate.parse(requestMap.get("event date")) : null;
		Period timeMissed = requestMap.containsKey("work time missed") ? Period.parse(requestMap.get("work time missed")) : null;
		LocalDate lastReviewed = requestMap.containsKey("last reviewed date") ? LocalDate.parse(requestMap.get("last reviewed date")) : null;

		Request request = new Request(employeeEmail, evtTypeId, formatId, state, cost, evtDate, timeMissed, lastReviewed);

		String description = requestMap.containsKey("description") ? requestMap.get("description") : null;
		String location = requestMap.containsKey("event location") ? requestMap.get("event location") : null;
		String justification = requestMap.containsKey("work-related justification") ? requestMap.get("work-related justification") : null;

		Info info = new Info(description, location, justification);

		List<Attachment> attachments = new ArrayList<>();
		for (HashMap<String, String> attachmentMap : attachmentMaps) {
			String filename = attachmentMap.containsKey("filename") ? attachmentMap.get("filename") : null;
			int approvedAtState = attachmentMap.containsKey("approval for state") ? Integer.parseInt(attachmentMap.get("approval for state")) : 0;
			String fileDescription = attachmentMap.containsKey("description") ? attachmentMap.get("description") : null;

			attachments.add(new Attachment(filename, 0, approvedAtState, fileDescription));
		}

		JsonObject res = new JsonObject();
		if (reqServ.add(request, info, attachments)) {
			addState(res, true);
			addContent(res, "Added reimbursement request successfully");
		} else {
			addState(res, false);
			addContent(res, "Failed to add reimbursement request");
		}
		return res;
	}

	public JsonObject submitNotes(int requestId, List<HashMap<String, String>> noteMaps) {
		List<Note> notes = new ArrayList<>();
		for (HashMap<String, String> noteMap : noteMaps) {
			String email = noteMap.containsKey("manager email") ? noteMap.get("manager email") : null;
			LocalDate timeActedOn = noteMap.containsKey("time of action") ? LocalDate.parse(noteMap.get("time of action")) : null;
			float newAmount = noteMap.containsKey("changed amount") ? Float.parseFloat(noteMap.get("changed amount")) : -1;
			String reason = noteMap.containsKey("reason") ? noteMap.get("reason") : null;
			
			notes.add(new Note(0, requestId, email, timeActedOn, newAmount, reason));
		}

		JsonObject res = new JsonObject();
		if (reqServ.addNotes(requestId, notes)) {
			addState(res, true);
			addContent(res, "Added note(s) to reimbursement request successfully");
		} else {
			addState(res, false);
			addContent(res, "Failed to add note(s)");
		}
		return res;
	}
    
    /**
     * Attempts to change an existing Request
     * @param int - The request Id
     * @return A JsonObject of the form {state: ..., content: ...}
     */
    public JsonObject update(int id, List<FieldValueWrapper> fields) {
		JsonObject res = new JsonObject();

    	if (reqServ.modifyRequest(id, fields)) {
			addState(res, true);
			addContent(res, "Successfully updated reimbursement");
		} else {
			addState(res, false);
			addContent(res, "Failed to update reimbursement");
		}
		return res;
    }
    
    /**
     * Approves the request with the current user's level of power
     * @param int - Request id
     */
    public void approveRequest(int id) {
    	String userEmail = empServ.getUser().getEmail();
    	int curPower = reqServ.approve(id, empServ.getPowerLevel(),
    			empServ.getSubordinates(userEmail));
    	
    	if (curPower >= lastPower)
    		notifyEmployee(true);
    }
    
    /**
     * Rejects the request with the current user's level of power
     * @param int - Request id
     * @return A JsonObject of the form {state: ..., content: ...}
     */
    public JsonObject rejectRequest(int id) {
		JsonObject res = new JsonObject();

    	String userEmail = empServ.getUser().getEmail();
    	boolean result = reqServ.reject(id, empServ.getPowerLevel(),
    			empServ.getSubordinates(userEmail));
    	
    	notifyEmployee(false);
		
		
    	if (result) {
			addState(res, true);
    		addContent(res, "Rejected request successfully");
		} else { 
			addState(res, false);
			addContent(res, "Failed to reject the request. Perhaps due to improper permission.");
		}
		return res;
    }
    
    /**
     * Sends an email out to the employee who requested the reimbursement
     * indicating approval or rejection
     */
    @SuppressWarnings("unused")
	public void notifyEmployee(boolean result) {
    	String email = empServ.getUser().getEmail();
    	String contents = "Your reimbursement request has been ";
    	
    	if (result)
    		contents += "approved.";
    	else 
    		contents += "rejected.";
    	
    	// Use some method here to send an email to that address with this content
	}
	
	/**
	 * @param JsonObject - The object to add the property to
	 * @param Boolean - Whether or not it was a success
	 */
	private void addState(JsonObject obj, boolean flag) {
		obj.addProperty("success", flag);
	}

	/**
	 * @param JsonObject - The object to add the property to
	 * @param JsonObject - The object to put in the contents
	 */
	private void addContent(JsonObject obj, JsonObject value) {
		obj.add("content", value);
	}

	/**
	 * @param JsonObject - The object to add the property to
	 * @param String - The string to put in the contents
	 */
	private void addContent(JsonObject obj, String value) {
		obj.addProperty("content", value);
	}
}