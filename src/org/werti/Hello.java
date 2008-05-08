package org.werti;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;

public class Hello extends HttpServlet {

	static final long serialVersionUID = 0;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.printf("Hello, World!\n");
	}
}
