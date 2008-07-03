package org.werti;

import java.io.*;

import java.util.logging.*;

import javax.servlet.*;

import javax.servlet.http.*;

import lib.html.ErrorPage;
import lib.html.Net;

import org.werti.uima.Config;
import org.werti.uima.Dispatcher;

public class RequestURL extends HttpServlet {
	static final long serialVersionUID = 0;
	private static final Logger log = Logger.getLogger("org.werti");

	/**
	 * Fetch the article.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		final String article = request.getParameter("url");
		final String baseurl = Net.find_baseurl(article);
		final PrintWriter out = response.getWriter();
		final BufferedReader in = Net.fetch(article);

		response.setContentType("text/html");

		log.info("Fetched " + article);
		log.info("baseurl: " + baseurl);

		if (!in.ready()) {
			log.severe("Input stream to " + article + " not ready!");
		}

		try {
			final Dispatcher d = new Dispatcher(new Config(
						"/home/aleks/src/werti/desc/testers/PTBTest.xml", baseurl), out);
			d.process(in);
			out.print(d.get_results());
		} catch (org.werti.uima.UnrecoverableUIMAException uuimae) {
			ErrorPage.show(out, uuimae);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		log.info("Fetching " + request.getParameter("url"));
		doGet(request, response);
	}
}
