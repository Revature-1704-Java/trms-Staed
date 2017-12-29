package com.staed.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.staed.delegate.Interpreter;

public class ViewServlet extends HttpServlet {
	Interpreter interpreter = new Interpreter();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		
		String email = request.getSession().getAttribute("email").toString();
		
		// TODO Switch here for different commands
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String command = request.getParameter("command");
    	
    	String email = request.getParameter("user");
    	String pass = request.getParameter("pass");
    	
    	// TODO Switch here for different commands
    	//		Also Handle saving possible file attachments here
    }
}