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
		String res = "Please login";

		if (session.getAttribute("email") != null) {
			String email = session.getAttribute("email").toString();
			res = gson.toJson(interpret.display(email).get("content"));
		}

		try {
			response.getWriter().append(res);
		} catch (IOException | IllegalStateException ex) {
			ex.printStackTrace();
		}
	}
}
