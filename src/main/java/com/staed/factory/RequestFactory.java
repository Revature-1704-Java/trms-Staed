package com.staed.factory;

import java.sql.Date;
import java.util.AbstractMap.SimpleEntry;

import com.staed.beans.Request;

// This class is currently not really needed
// TODO: Delete this class if it is unnecessary when you are done writing the program
public class RequestFactory {
    /**
     * Creates a URGENT reimbursement request
     * @param int eId - Unique employee ID
     * @param Time eTime - Date of the event to be reimbursed
     * @param String loc - Location of the event
     * @param String desc - Description
     * @param float cost - Amount of reimbursement requested
     * @param String format - How success in the event is evaluated
     * @param SimpleEntry&lt;String, Integer&gt; event - Pair of event type 
     * and id, determines reimbursement amount
     * @param String justification - Work-related justification for the request
     * @param String email - An email detailing prior approval for this request
     * @param int cutoff - The minimum grade necessary to receive reimbursement
     * @return Request
     */
    public Request createRequestUrgent(int eId, Date eTime, String loc, 
        String desc, float cost, String format, SimpleEntry<String, Integer> event, String justification,
        String email, int cutoff) 
    {                                   
        Request r = new Request(eId, eTime, loc, desc, cost, 
            format, event, justification, email, cutoff, true);
        return r;
    }

    /**
     * Creates a normal reimbursement request
     * @param int eId - Unique employee ID
     * @param Time eTime - Date of the event to be reimbursed
     * @param String loc - Location of the event
     * @param String desc - Description
     * @param float cost - Amount of reimbursement requested
     * @param String format - How success in the event is evaluated
     * @param SimpleEntry&lt;String, Integer&gt; event - Pair of event type 
     * and id, determines reimbursement amount
     * @param String justification - Work-related justification for the request
     * @param String email - An email detailing prior approval for this request
     * @param int cutoff - The minimum grade necessary to receive reimbursement
     * @return Request
     */
    public Request createRequest(int eId, Date eTime, String loc, 
        String desc, float cost, String format, SimpleEntry<String, Integer> event, String justification,
        String email, int cutoff)
    {
        Request r = new Request(eId, eTime, loc, desc, cost,
            format, event, justification, email, cutoff, false);
        return r;
    }
}