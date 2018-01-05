package com.staed.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestsServlet extends Servlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HttpSession session = request.getSession(true);
		//System.out.println("email: " + request.getParameter("email"));
		
		if (request.getParameter("email") != null && request.getParameter("valid") != null && request.getParameter("valid").toString().equals("true")) {
			String email = request.getParameter("email").toString();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(gson.toJson(interpret.display(email).get("content").getAsString()));
		}
	}
}
