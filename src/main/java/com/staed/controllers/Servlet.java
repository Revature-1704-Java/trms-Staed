package com.staed.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.staed.delegate.Interpreter;

public abstract class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected static Interpreter interpret = new Interpreter();
	protected static Gson gson = new Gson();
	protected static JsonParser parser = new JsonParser();

	protected static final String EMAIL = "email";
	protected static final String DESC = "description";
	protected static final String FILENAME = "filename";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Do Nothing
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Do Nothing
	}
	
	Interpreter getInterpreter() {
		return interpret;
	}
}
