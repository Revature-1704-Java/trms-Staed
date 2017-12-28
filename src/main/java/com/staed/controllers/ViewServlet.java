package com.staed.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.staed.delegate.Interpreter;

public class ViewServlet extends HttpServlet {
	Interpreter interpreter = new Interpreter(); 
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    
    //protected void doGet(...)
}