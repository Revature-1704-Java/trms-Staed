package com.staed.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class SubmitServlet extends Servlet {
	private static final Logger logger = Logger.getLogger(SubmitServlet.class);
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (request.getParameter(EMAIL) != null) {
            HashMap<String, String> parsedRequest = new HashMap<>();

            parsedRequest.put(EMAIL, request.getParameter(EMAIL).toString());
            parsedRequest.put("event type", request.getParameter("event type"));
            parsedRequest.put("grading format", request.getParameter("grading format"));
            parsedRequest.put("state", request.getParameter("state"));
            parsedRequest.put("cost", request.getParameter("cost"));
            parsedRequest.put("event date", request.getParameter("event date"));
            parsedRequest.put("work time missed", request.getParameter("work time missed"));
            parsedRequest.put("last reviewed date", request.getParameter("last reviewed date"));
            
            parsedRequest.put(DESC, request.getParameter(DESC));
            parsedRequest.put("event location", request.getParameter("event location"));
            parsedRequest.put("work-related justification", request.getParameter("work-related justification"));

            List<HashMap<String, String>> attachments = new ArrayList<>();
            JsonParser parser = new JsonParser();
            String[] attachmentJsons = request.getParameterValues("attachment");

            try {
            	if (attachmentJsons != null) {
	                for (String attachmentJson : attachmentJsons) {
	                    JsonObject obj = parser.parse(attachmentJson).getAsJsonObject();
	                    if (obj.get(FILENAME) == null)
	                        continue;
	                    
	                    HashMap<String, String> attachment = new HashMap<>();
	                    attachment.put(FILENAME, obj.get(FILENAME).getAsString());
	                    attachment.put("approved for state", obj.get("approval for state").getAsString());
	                    attachment.put("description", obj.get("description").getAsString());
	                    attachments.add(attachment);
	                }
            	}
            } catch (JsonParseException ex) {
            	logger.error("Exception in doPost - Likely in the Json parsing bit", ex);
            }
            
            writeResults(response, session, interpret.submit(parsedRequest, attachments), "submit");
        }
        
        
        //TODO: Handle submit notes
    }
}