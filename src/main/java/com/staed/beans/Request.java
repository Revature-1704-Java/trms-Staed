package com.staed.beans;

import java.sql.Time;

import com.staed.enums.*;

/**
 * A Request Object that maps to the row type of a Request Table
 * in a PL/SQL database
 */
public class Request {
    private int requestId;

    private int employeeId;
    private Time eventTime;
    private String location;
    private String description;
    private float cost;
    private GradingFormat format;
    private EventType eventType;
    private String justification;

    private String approvalEmail; // This might be better as an attached file
    private Boolean okdBySuper = false;
    private Boolean okdByHead = false;
    private Boolean okdByBenCo = false;

    private int gradeCutoff;
    private String status = "Open";
    private Boolean urgent;

    public Request (int employeeID, Time eventTime, String location, 
                    String description, Float cost, GradingFormat format,
                    EventType eventType, String justification,
                    String approvalEmail, int gradeCutoff, Boolean urgent) {
        this.employeeId = employeeID;
        this.location = location;
        this.description = description;
        this.cost = cost;
        this.format = format;
        this.eventType = eventType;
        this.justification = justification;
        this.approvalEmail = approvalEmail;
        this.gradeCutoff = gradeCutoff;
        this.urgent = urgent;
    }

    /** Getters and Setters */
    public Integer getEmployeeID() {
        return employeeId;
    }
    
    // TODO: Add all the getters and setters
}