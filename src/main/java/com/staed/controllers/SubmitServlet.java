package com.staed.controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SubmitServlet extends Servlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        if (session.getAttribute("email") != null) {
            HashMap<String, String> parsedRequest = new HashMap<>();

            parsedRequest.put("email", session.getAttribute("email").toString());
            parsedRequest.put("eventType", request.getParameter("event type"));
            parsedRequest.put("format", request.getParameter("format"));
            parsedRequest.put("state", request.getParameter("state"));
            parsedRequest.put("cost", request.getParameter("cost"));
            parsedRequest.put("eventDate", request.getParameter("event date"));
            parsedRequest.put("workMissed", request.getParameter("work time missed"));
            parsedRequest.put("lastReviewed", request.getParameter("last reviewed date"));
            
            parsedRequest.put("description", request.getParameter("description"));
            parsedRequest.put("location", request.getParameter("event location"));
            parsedRequest.put("justification", request.getParameter("work-related justification"));

            // TODO: Handle attachments
            String[] attachments = request.getParameterValues("attachment");
            // filename
            //approvedatstate
            //filedescription
            
            interpret.submit(parsedRequest, null);
        }
    }
}