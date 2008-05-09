package org.werti;

import java.io.*;
import java.util.logging.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.uima.cas.*;
import org.apache.uima.analysis_component.*;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

import org.apache.uima.cas.text.Language;

public class UIMAtest extends HttpServlet {
	static final long serialVersionUID = 0;

	private static final Logger log = Logger.getLogger("org.werti");

	static final Language de = new Language("German");

	public void doGet(HttpServletRequest req, HttpServletResponse res) 
		throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		log.info("Printing HTML now");

		out.print(html_preamble("UIMAtest"));
		out.print(html_element("h1", "UIMAtest"));
		out.print(html_element("p", "This is a short test of UIMAs functionality."));
		out.print(html_element("p", de.toString()));
		out.print(html_footer());
	}

	private String html_preamble(String title) {
		return "<html><header><title>" 
			+ title 
			+ "</title></header><body>";
	}

	private String html_footer() {
		return "</body></html>";
	}

	private String html_element(String e, String p) {
		return html_element(e,p,"");
	}

	private String html_element(String e, String p, String css_name) {
		return "<" + e + " class=\""
			+ css_name
			+ "\"> " + p + "</"+ e +">";
	}
}
