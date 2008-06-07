package org.werti;

import java.io.*;

import java.net.MalformedURLException;

import java.util.Enumeration;

import java.util.logging.*;

import javax.servlet.*;

import javax.servlet.http.*;

import lib.html.Net;

public class RequestWiki extends HttpServlet {
	static final long serialVersionUID = 0;
	private static final Logger log = Logger.getLogger("org.werti");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		log.setLevel(Level.FINEST);
		response.setHeader("host", "en.wikipedia.org");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		final String article = request.getParameter("wikiquery");
		log.info("Fetched " + article);
		try {
			logResponseHeader(response);
			logRequestHeader(request);
			String b = "";
			final BufferedReader in = Net.fetch(wikify(article));
			while (in.ready()) {
				b += (in.readLine() + "\n"
						).replaceAll("href=\"/", "href=\"" + "http://en.wikipedia.org/"
						).replaceAll("src=\"/", "src=\"" + "http://en.wikipedia.org/"
						).replaceAll("@import\\s*\"/", "@import \"" + "http://en.wikipedia.org/"
						).replaceAll("\"/", "\"" + "http://en.wikipedia.org/")
						;
			}
			out.print(b);
		} catch (MalformedURLException mue) {
			log.log(Level.SEVERE, "Malformed URL", mue);
		} catch (IOException ioe) {
			log.log(Level.SEVERE, "Wrong content?", ioe);
		}
	}

	private static void logRequestHeader(HttpServletRequest r) {
		log.fine("ContextPath: " + r.getContextPath());
		final Enumeration<String> e = r.getHeaderNames();
		while (e.hasMoreElements()) {
			final String s = e.nextElement();
			log.fine("Header: " + s + "\n" + r.getHeader(s));
		}


	}

	private static void logResponseHeader(HttpServletResponse r) {

	}

	private static String wikify(String term) {
		return "http://en.wikipedia.org/wiki/" + term.replace(" ", "_");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		response.setHeader("host", "en.wikipedia.org");
		log.info("Fetching " + request.getParameter("wikiquery"));
		doGet(request, response);
	}
}
