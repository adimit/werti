package org.werti;

import java.io.*;

import java.net.MalformedURLException;

import java.util.logging.*;

import javax.servlet.*;

import javax.servlet.http.*;

import org.werti.html.*;

public class RequestWiki extends HttpServlet {
	static final long serialVersionUID = 0;
	private static final Logger log = Logger.getLogger("org.werti");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		final String article = request.getParameter("wikiquery");
		log.info("Fetched " + article);
		try {
			final Object o = Net.fetch(wikify(article));
			out.write(o.getClass().getCanonicalName());
		} catch (MalformedURLException mue) {
			log.log(Level.SEVERE, "Malformed URL", mue);
		} catch (IOException ioe) {
			log.log(Level.SEVERE, "Wrong content?", ioe);
		}
	}

	private static String wikify(String term) {
		return "http://en.wikipedia.org/wiki/" + term.replace(" ", "_");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		log.info("Fetching " + request.getParameter("wikiquery"));
		doGet(request, response);
	}
}
