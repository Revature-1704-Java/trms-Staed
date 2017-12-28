package com.staed.beans;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.staed.stores.FieldValueWrapper;

/**
 * A BEAN representing only the essential information pertaining to a Request.
 * It contains a reference to the email of the employee who submitted the
 * request, the event type, grading method, and other details.
 */
public class Request extends Bean {;
	
	private int id;
    private String employeeEmail;
    private int evtTypeId;
    private int formatId;
    private int state;
    private float cost;
    private LocalDate evtDate;
    private Period timeMissed;
    private LocalDate lastReviewed;

    public Request(int id, String employeeEmail, int evtTypeId, int formatId,
    		int state, float cost, LocalDate evtDate, Period timeMissed, 
    		LocalDate lastReviewed) {
    	this.id = id;
    	this.employeeEmail = employeeEmail;
    	this.evtTypeId = evtTypeId;
    	this.formatId = formatId;
    	this.state = state;
    	this.cost = cost;
    	this.evtDate = evtDate;
    	this.timeMissed = timeMissed;
    	this.lastReviewed = lastReviewed;
    }
    
    @Override
    public List<FieldValueWrapper> toFieldValueWrappers() {
    	List<FieldValueWrapper> list = new ArrayList<>();
    	list.add(new FieldValueWrapper(names.requestIdentifier, id));
    	list.add(new FieldValueWrapper(names.employeeIdentifier, employeeEmail));
    	list.add(new FieldValueWrapper(names.formatId, formatId));
    	list.add(new FieldValueWrapper(names.state, state));
    	list.add(new FieldValueWrapper(names.cost, cost));
    	list.add(new FieldValueWrapper(names.eventDate, evtDate));
    	list.add(new FieldValueWrapper(names.workMissed, timeMissed));
    	list.add(new FieldValueWrapper(names.lastReviewed, lastReviewed));
    	return list;
    }

    public String toString() {
        return "Request [ Id: " + id + ", Employee Email: " + employeeEmail
             + ", Event Type Id: " + evtTypeId + ", Grade Format Id: "
        	 + formatId + ", State: " + state + ", Cost: " + cost
        	 + ", Event Date: " + evtDate + ", Work Time Missed: "
        	 + timeMissed + ", Date of Last Review: " + lastReviewed;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return employeeEmail;
    }
    public void setEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public int getEventTypeId() {
        return evtTypeId;
    }
    public void setEventTypeId(int evtTypeId) {
        this.evtTypeId = evtTypeId;
    }

    public int getFormatId() {
        return formatId;
    }
    public void setFormatId(int formatId) {
        this.formatId = formatId;
    }

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

    public float getCost() {
        return cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }

    public LocalDate getEventDate() {
        return evtDate;
    }
    public void setEventDate(LocalDate evtDate) {
        this.evtDate = evtDate;
    }

    public Period getTimeMissed() {
        return timeMissed;
    }
    public void setTimeMissed(Period timeMissed) {
        this.timeMissed = timeMissed;
    }

    public LocalDate getLastReviewed() {
        return lastReviewed;
    }
    public void setLastReviewed(LocalDate lastReviewed) {
        this.lastReviewed = lastReviewed;
    }
}