package org.werti;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.logging.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.werti.html.*;

public class RequestReuters extends HttpServlet {
	static final long serialVersionUID = 0;
	private static final Logger log = Logger.getLogger("org.werti");

	/**
	 * Fetch the article, replace some html-elements so the site doesn't display completely
	 * awkward and print it to the screen.
	 *
	 * This could probably be done cleaner with DOM, but using regexps is arguably more 
	 * efficient.
	 *
	 * The Regexps must be refined to not overgenerate. Currently the last two are especially
	 * dangerous. They're going to match a whole lot of stuff that might not be their job.
	 * For example, in the middle of a path something like var='value'/somewhere (although this
	 * is unlikely.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		log.setLevel(Level.FINEST);

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
			String b = "";
			while (in.ready()) {
				final String line = in.readLine();
				b += (line
				      + "\n"
					).replaceAll("href=\"/", "href=\"" + baseurl
					).replaceAll("src=\"/", "src=\"" + baseurl
					).replaceAll("@import\\s*\"/", "@import \"" + baseurl
					//).replaceAll("\"/", "\"" + baseurl // FIXME: this is dirty...
					//).replaceAll("'/", "'" + baseurl // FIXME: this is dirty...
					);
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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		log.info("Fetching " + request.getParameter("url"));
		doGet(request, response);
	}
}
