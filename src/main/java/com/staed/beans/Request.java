package com.staed.beans;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;

/**
 * A Request Object that maps to the row type of a Request Table
 * in a PL/SQL database
 */
public class Request {
	
    private int requestId;

    private int employeeId;
    private LocalDate eventDate;
    private String location;
    private String description;
    private float cost;
    private String format;
    private SimpleEntry<String, Integer> event;
    private String justification;

    private String approvalEmail; // This might be better as an attached file
    private Boolean okdBySuper = false;
    private Boolean okdByHead = false;
    private Boolean okdByBenCo = false;

    private int gradeCutoff = 70;
    private String status = "Open";
    private Boolean urgent = false;

    /** Empty Constructor for Modifying */
    public Request() {
        this.employeeId = 0;
        this.eventDate = LocalDate.now(); 
        this.location = "";
        this.description = "";
        this.cost = 0;
        this.format = "Grade";
        this.event = new SimpleEntry<>("Other", 6);
        this.justification = "";
        this.approvalEmail = "";
    }

    /** Only RequestFactory should access to this */
    public Request (int employeeID, LocalDate eventDate, String location, 
    String description, Float cost, String format, SimpleEntry<String, Integer> event, 
    String justification, String approvalEmail, int gradeCutoff, 
    Boolean urgent) {
        this.employeeId = employeeID;
        this.eventDate = eventDate;
        this.location = location;
        this.description = description;
        this.cost = cost;
        this.format = format;
        this.event = event;
        this.justification = justification;
        this.approvalEmail = approvalEmail;
        this.gradeCutoff = gradeCutoff;
        this.urgent = urgent;
    }

    /**
     * Full constructor used for reconstructions of a Request entry in the SQL DB
     * Only RequestDAO should access to this
     * @param int requestId - Unique Request ID
     * @param int employeeId - Unique employee ID
     * @param Date eventDate - Date of the event to be reimbursed
     * @param String location - Location of the event
     * @param String description - Description
     * @param float cost - Amount of reimbursement requested
     * @param String format - The method by which the event is graded
     * @param SimpleEntry&lt;String, Integer&gt; - Pair of event name and id, determines reimbursement amount
     * @param String justification - Work-related justification for the request
     * @param String approvalEmail - An email detailing prior approval for this request
     * @param Boolean okdBySuper - Did the Direct Supervisor approve the request
     * @param Boolean okdByHead - Did the Department Head approve the request
     * @param Boolean okdByBenCo - Did the Benefits Coordinator approve the request
     * @param int gradeCutoff - The grade cutoff for passing
     * @param String status - Where the request is in the process
     * @param Boolean urgent - Whether this request is urgent
     */
    public Request (int requestId, int employeeId, LocalDate eventDate,
    String location, String description, float cost, String format,
    SimpleEntry<String, Integer> event, String justification, String approvalEmail,
    Boolean okdBySuper, Boolean okdByHead, Boolean okdByBenCo,
    int gradeCutoff, String status, Boolean urgent) {
        this.requestId = requestId;
        this.employeeId = employeeId;
        this.eventDate = eventDate;
        this.location = location;
        this.description = description;
        this.cost = cost;
        this.format = format;
        this.event = event;
        this.justification = justification;
        this.approvalEmail = approvalEmail;
        this.okdBySuper = okdBySuper;
        this.okdByHead = okdByHead;
        this.okdByBenCo = okdByBenCo;
        this.gradeCutoff = gradeCutoff;
        this.status = status;
        this.urgent = urgent;

    }
    
    public int getRequestId() {
    	return requestId;
    }
    
    public void setRequestId(int requestId) {
    	this.requestId = requestId;
    }
    
    public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public SimpleEntry<String, Integer> getEvent() {
		return event;
	}

	public void setEvent(SimpleEntry<String, Integer> other) {
		this.event = other;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getApprovalEmail() {
		return approvalEmail;
	}

	public void setApprovalEmail(String approvalEmail) {
		this.approvalEmail = approvalEmail;
	}

	public Boolean getOkdBySuper() {
		return okdBySuper;
	}

	public void setOkdBySuper(Boolean okdBySuper) {
		this.okdBySuper = okdBySuper;
	}

	public Boolean getOkdByHead() {
		return okdByHead;
	}

	public void setOkdByHead(Boolean okdByHead) {
		this.okdByHead = okdByHead;
	}

	public Boolean getOkdByBenCo() {
		return okdByBenCo;
	}

	public void setOkdByBenCo(Boolean okdByBenCo) {
		this.okdByBenCo = okdByBenCo;
	}

	public int getGradeCutoff() {
		return gradeCutoff;
	}

	public void setGradeCutoff(int gradeCutoff) {
		this.gradeCutoff = gradeCutoff;
	}

	public Boolean getUrgent() {
		return urgent;
	}

	public void setUrgent(Boolean urgent) {
		this.urgent = urgent;
	}

	public String getStatus() {
		return status;
	}
	
    public void setStatus(String status) {
        switch(status.toUpperCase()) {
            case "OPEN": this.status = "OPEN"; break;
            case "CLOSED": this.status = "CLOSED"; break;
            case "PROCESSING": this.status = "PROCESSING"; break;
            default: break;
        }
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("{ Request ID: " + requestId + ", ");
        res.append("Employee ID: " + employeeId + ", ");
        res.append("Event Time: " + eventDate.toString() + ", ");
        res.append("Event Location: " + location + ", ");
        res.append("Description: " + description + ", ");
        res.append("Reimbursement Cost: " + Float.toString(cost) + ", ");
        res.append("Grading Format: " + format.toString() + ", ");
        res.append("EventName: " + event.getKey() + ", ");
        res.append("Work-Related Justification: " + justification + ", ");
        res.append("Approval Email: " + approvalEmail + ", ");
        res.append("Approved By the Direct Supervisor: " + okdBySuper + ", ");
        res.append("Approved By the Department Head: " + okdByHead + ", ");
        res.append("Approved by the Benefits Coordinator: " + okdByBenCo.toString() + ", ");
        res.append("Grade Cutoff: " + Integer.toString(gradeCutoff) + ", ");
        res.append("Status: " + status + ", ");
        res.append("Urgent: " + urgent.toString() + " }\n");
        return res.toString();
    }
}