package org.werti;

import java.io.*;

import java.util.Map;

import java.util.logging.*;

import javax.servlet.*;

import javax.servlet.http.*;

/**
 * It will, according to its arguments, call the appropriate classes that
 * This is the base web interface class of WERTi.
 * are needed to fulfill a specific task.
 */

public class Interface extends HttpServlet {
	static final long serialVersionUID = 0;

	private static final Logger log = Logger.getLogger("org.werti");

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		log.setLevel(Level.ALL);
		final Map<String,String[]> args = request.getParameterMap();
		for (final Map.Entry<String,String[]> e: args.entrySet()) {
		     final String[] vs = e.getValue();
		     String r = "";
		     for (String v: vs) {
			  r += v + ", ";
		     }
		     log.info(e.getKey() + " = " + r);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		log.info("Requested" + request.getRequestURI());
		doGet(request, response);
	}
}
