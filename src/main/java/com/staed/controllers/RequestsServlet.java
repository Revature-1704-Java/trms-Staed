package com.staed.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestsServlet extends Servlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("email"));
		//TODO: The Servlet cannot retrieve values from Angular requests for some reason, fix this
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(gson.toJson(interpret.display("lure@them.all").get("content").getAsString()));
		
		/*
		try {
			if (session.getAttribute("email") != null) {
				String email = session.getAttribute("email").toString();
				String json = gson.toJson(interpret.display(email).get("content"));
				
				response.getWriter().write(json);
			} else {	
				response.getWriter().write(badResponse("Please login"));
			}
		} catch (IOException | IllegalStateException ex) {
			ex.printStackTrace();
			response.getWriter().write(badResponse("Exception encountered in RequestsServlet"));
		}*/
	}
}
