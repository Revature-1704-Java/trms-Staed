package com.staed.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class FrontController extends Servlet {
    private static final long serialVersionUID = 1L;

    private enum HandledPaths {
        SLASH("/#"),
        LOGIN("/login"),
        SUBMIT("/submit");

        private final String text;

        private HandledPaths(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        try {
            response.sendRedirect("RequestsServlet");
        } catch (IOException | IllegalStateException ex) {
            ex.printStackTrace();
        }
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    String requestUrl = request.getRequestURI().substring(request.getContextPath().length());

        HandledPaths p = HandledPaths.SLASH;
        for (HandledPaths i : HandledPaths.values()) {
            if (requestUrl.startsWith(i.toString())) {
                p = i;
                break;
            }
        }

        try {
            switch (p) {
                case LOGIN:
                    response.sendRedirect("LoginServlet");
                    break;
                case SUBMIT:
                    response.sendRedirect("SubmitServlet");
                    break;
                default:
                    break;
            }
        } catch (IOException | IllegalStateException ex) {
            ex.printStackTrace();
        }
	}

}