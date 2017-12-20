package com.staed.beans;

import java.sql.Date;
import java.util.AbstractMap.SimpleEntry;

import com.staed.enums.*;

/**
 * A Request Object that maps to the row type of a Request Table
 * in a PL/SQL database
 */
public class Request {
    private int requestId;

    private int employeeId;
    private Date eventDate;
    private String location;
    private String description;
    private float cost;
    private String format;
    private SimpleEntry<Integer, EventType> eventType;
    private String justification;

    private String approvalEmail; // This might be better as an attached file
    private Boolean okdBySuper = false;
    private Boolean okdByHead = false;
    private Boolean okdByBenCo = false;

    private int gradeCutoff;
    private String status = "Open";
    private Boolean urgent;

    /** Only RequestFactory should access to this */
    public Request (int employeeID, Date eventDate, String location, 
                    String description, Float cost, String format,
                    int eventTypeId, EventType eventTypeEnum, String justification,
                    String approvalEmail, int gradeCutoff, Boolean urgent) {
        this.employeeId = employeeID;
        this.eventDate = eventDate;
        this.location = location;
        this.description = description;
        this.cost = cost;
        this.format = format;
        this.eventType = new SimpleEntry<>(eventTypeId, eventTypeEnum);
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
     * @param EventType eventType - Type of event, determines reimbursement amount
     * @param String justification - Work-related justification for the request
     * @param String approvalEmail - An email detailing prior approval for this request
     * @param Boolean okdBySuper - Did the Direct Supervisor approve the request
     * @param Boolean okdByHead - Did the Department Head approve the request
     * @param Boolean okdByBenCo - Did the Benefits Coordinator approve the request
     * @param int gradeCutoff - The grade cutoff for passing
     * @param String status - Where the request is in the process
     * @param Boolean urgent - Whether this request is urgent
     */
    public Request (int requestId, int employeeId, Date eventDate,
                    String location, String description, float cost,
                    String format, int eventTypeId, EventType eventTypeEnum,
                    String justification, String approvalEmail,
                    Boolean okdBySuper, Boolean okdByHead, Boolean okdByBenCo,
                    int gradeCutoff, String status, Boolean urgent) {
        this.requestId = requestId;
        this.employeeId = employeeId;
        this.eventDate = eventDate;
        this.location = location;
        this.description = description;
        this.cost = cost;
        this.format = format;
        this.eventType = new SimpleEntry<>(eventTypeId, eventTypeEnum);
        this.justification = justification;
        this.approvalEmail = approvalEmail;
        this.okdBySuper = okdBySuper;
        this.okdByHead = okdByHead;
        this.okdByBenCo = okdByBenCo;
        this.gradeCutoff = gradeCutoff;
        this.status = status;
        this.urgent = urgent;

    }

    /** Getters and Setters */
    public int getRequestId() { return requestId; }
    public int getEmployeeId() { return employeeId; }
    public Date getDate() { return eventDate; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public float getCost() { return cost; }
    public String getFormat() { return format; }
    public SimpleEntry<Integer, EventType> getEventType() { return eventType; }
    public String getJustification() { return justification; }
    public String getApprovalEmail() { return approvalEmail; }
    public boolean okdBySuper() { return okdBySuper; }
    public boolean okdByHead() { return okdByHead; }
    public boolean okdByBenCo() { return okdByBenCo; }
    public int getCutoff() { return gradeCutoff; }
    public String getStatus() { return status; }
    public boolean isUrgent() { return urgent; }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("{\nRequest ID: " + requestId + ",\n");
        res.append("Employee ID: " + employeeId + ",\n");
        res.append("Event Date: " + eventDate.toString() + ",\n");
        res.append("Event Location: " + location + ",\n");
        res.append("Description: " + description + ",\n");
        res.append("Reimbursement Cost: " + Float.toString(cost) + ",\n");
        res.append("Grading Format: " + format.toString() + ",\n");
        res.append("Type of Event: " + eventType.getValue().toString() + ",\n");
        res.append("Work-Related Justification: " + justification + ",\n");
        res.append("Approval Email: " + approvalEmail + ",\n");
        res.append("Approved By the Direct Supervisor: " + okdBySuper + ",\n");
        res.append("Approved By the Department Head: " + okdByHead + ",\n");
        res.append("Approved by the Benefits Coordinator: " + okdByBenCo.toString() + ",\n");
        res.append("Grade Cutoff: " + Integer.toString(gradeCutoff) + ",\n");
        res.append("Status: " + status + ",\n");
        res.append("Urgent: " + urgent.toString() + "\n}");
        return res.toString();
    }
}