package org.werti;
import java.io.*;
import java.util.logging.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.werti.html.*;


/**
 * It will, according to its arguments, call the appropriate classes that
 * This is the base web interface class of WERTi.
 * are needed to fulfill a specific task.
 */

public class Interface extends HttpServlet {
	static final long serialVersionUID = 0;

	private static final Logger log = Logger.getLogger("org.werti");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		response.setContentType("text/html");
		response.setHeader("host", "en.wikipedia.org");
		PrintWriter out = response.getWriter();
		out.print(HTML.preamble("Welcome to WERTi"));
		out.print(HTML.element("h1", "Welcome to WERTi"));
		out.print(HTML.element("p"
			, "This is a preliminary site, which will eventually be replaced"
			+ " by a nicer interface. Until then, you may query for an HTTP"
			+ " address (URI) with the form below, or search for a term with"
			+ " the other form. Note that not everything is already" 
			+ " implemented ;-)"));
		out.print(HTML.element("h4", "Query Reuters News"));
		final HTML.Input[] e = { new HTML.Input("text", "url", "20") };
		out.print(HTML.form("RequestReuters", "POST", e));
		out.print(HTML.element("h4", "Query English Wikipedia"));
		final HTML.Input[] w = { new HTML.Input("text", "wikiquery", "20") };
		out.print(HTML.form("/werti/RequestWiki", "POST", w));
		out.print(HTML.element("h4", "Term Query"));
		final HTML.Input[] t = { new HTML.Input("text", "termquery", "20") };
		out.print(HTML.form("RequestTerm", "POST", t));

		out.print(HTML.hline());

		out.print(HTML.element("h2", "System status"));

		log.setLevel(Level.FINEST);
		try {
			LogManager.getLogManager().checkAccess();
			out.print("Logger status: OK" + HTML.BR);
		} catch (SecurityException se) {
			out.print("Logger status: Broken" + HTML.BR);
		}

		out.print(HTML.footer());
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		response.setHeader("host", "en.wikipedia.org");
		log.info("Requested" + request.getRequestURI());
	}
}
