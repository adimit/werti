package org.werti;

/**
 * This is the base web interface class of WERTi.
 * It will, according to its arguments, call the appropriate classes that
 * are needed to fulfill a specific task.
 */

import java.io.*;
import java.util.logging.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.werti.html.*;

public class Interface extends HttpServlet {
	static final long serialVersionUID = 0;

	private static final Logger log = Logger.getLogger("org.werti");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		out.print(HTML.preamble("Welcome to WERTi"));
		out.print(HTML.element("h1", "Welcome to WERTi"));
		out.print(HTML.element("p"
			, "This is a preliminary site, which will eventually be replaced"
			+ " by a nicer interface. Until then, you may query for an HTTP"
			+ " address (URI) with the form below, or search for a term with"
			+ " the other form. Note that not everything is already" 
			+ " implemented ;-)"));
		out.print(HTML.element("h4", "Query specific URI"));
		final HTML.Input[] e = { new HTML.Input("text", "htmlquery", "20") };
		out.print(HTML.form("RequestURL", "POST", e));
		out.print(HTML.element("h4", "Query English Wikipedia"));
		final HTML.Input[] w = { new HTML.Input("text", "wikiquery", "20") };
		out.print(HTML.form("RequestWiki", "POST", w));
		out.print(HTML.element("h4", "Term Query"));
		final HTML.Input[] t = { new HTML.Input("text", "termquery", "20") };
		out.print(HTML.form("RequestTerm", "POST", t));

		for (Handler h:log.getHandlers()) {
			h.toString();
		}

		out.print(HTML.footer());
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		//doGet(request, response);
		log.severe("Requested" + request.toString());
	}
}
