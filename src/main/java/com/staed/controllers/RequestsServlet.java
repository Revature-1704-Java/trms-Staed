package com.staed.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestsServlet extends Servlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("email") != null) {
			String email = session.getAttribute("email").toString();
			String json = gson.toJson(interpret.display(email).get("content"));
			response.getWriter().append(json);
		} else {
			response.getWriter().append("Please login");
		}
	}
}
