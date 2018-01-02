package com.staed.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.staed.delegate.Interpreter;

@WebServlet("/requests")
public class Requests extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Interpreter interpreter = new Interpreter();
		
		String email = "";
		Object oEmail = request.getSession().getAttribute("email");
		if (oEmail != null) {
			email = oEmail.toString();
			interpreter.display(email);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
