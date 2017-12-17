package com.staed.factory;

import java.sql.Time;

import com.staed.beans.*;
import com.staed.enums.EventType;
import com.staed.enums.GradingFormat;

public class RequestFactory {
    /**
     * Creates a URGENT reimbursement request evaluated by Grades
     * param {int} eId Unique employee ID
     * param {Time} eTime Date of the event to be reimbursed
     * param {String} loc Location of the event
     * param {String} desc Description
     * param {float} cost Amount of reimbursement requested
     * param {EventType} type Type of event, determines reimbursement amount
     * param {String} justification Work-related justification for the request
     * param {String} email An email detailing prior approval for this request
     * param {int} cutoff The minimum grade necessary to recieve reimbursement
     * return {Request} The created Request object
     */
    public Request createUGRequest(int eId, Time eTime, String loc, 
        String desc, float cost, EventType type, String justification,
        String email, int cutoff) 
    {                                   
        Request r = new Request(eId, eTime, loc, desc, cost, 
            GradingFormat.Grade, type, justification, email, cutoff, true);
        return r;
    }

    /**
     * Creates a normal reimbursement request evaluated by Grades
     * param {int} eId Unique employee ID
     * param {Time} eTime Date of the event to be reimbursed
     * param {String} loc Location of the event
     * param {String} desc Description
     * param {float} cost Amount of reimbursement requested
     * param {EventType} type Type of event, determines reimbursement amount
     * param {String} justification Work-related justification for the request
     * param {String} email An email detailing prior approval for this request
     * param {int} cutoff The minimum grade necessary to recieve reimbursement
     * return {Request} The created Request object
     */
    public Request createNGRequest(int eId, Time eTime, String loc, 
        String desc, float cost, EventType type, String justification,
        String email, int cutoff)
    {
        Request r = new Request(eId, eTime, loc, desc, cost,
            GradingFormat.Grade, type, justification, email, cutoff, false);
        return r;
    }

    /**
     * Creates a URGENT reimbursement request evaluated by Presentations
     * param {int} eId Unique employee ID
     * param {Time} eTime Date of the event to be reimbursed
     * param {String} loc Location of the event
     * param {String} desc Description
     * param {float} cost Amount of reimbursement requested
     * param {EventType} type Type of event, determines reimbursement amount
     * param {String} justification Work-related justification for the request
     * param {String} email An email detailing prior approval for this request
     * return {Request} The created Request object
     */
    public Request createUPRequest(int eId, Time eTime, String loc, 
        String desc, float cost, EventType type, String justification,
        String email) 
    {                                   
        Request r = new Request(eId, eTime, loc, desc, cost, 
            GradingFormat.Presentation, type, justification, email, -1, true);
        return r;
    }

    /**
     * Creates a normal reimbursement request evaluated by Presentations
     * param {int} eId Unique employee ID
     * param {Time} eTime Date of the event to be reimbursed
     * param {String} loc Location of the event
     * param {String} desc Description
     * param {float} cost Amount of reimbursement requested
     * param {EventType} type Type of event, determines reimbursement amount
     * param {String} justification Work-related justification for the request
     * param {String} email An email detailing prior approval for this request
     * return {Request} The created Request object
     */
    public Request createNPRequest(int eId, Time eTime, String loc, 
        String desc, float cost, EventType type, String justification,
        String email, int cutoff)
    {
        Request r = new Request(eId, eTime, loc, desc, cost,
            GradingFormat.Presentation, type, justification, email, -1, false);
        return r;
    }
}