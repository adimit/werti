package org.werti;

import java.io.*;

import java.util.Map;

import java.util.logging.*;

import javax.servlet.*;

import javax.servlet.http.*;

import org.werti.uima.UnrecoverableUIMAException;

/**
 * It will, according to its arguments, call the appropriate classes that
 * are needed to fulfill a specific task.
 */

public class Interface extends HttpServlet {
	static final long serialVersionUID = 0;

	private static final Logger log = Logger.getLogger("org.werti");

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		final String[] tags = request.getParameter("tags").split("\\s*,\\ss*");
		final String tagger = request.getParameter("tagger");
		final String termOrURL = request.getParameter("termOrUrl");
		final String tokenizer = request.getParameter("tokenizer");
		final String enhance = request.getParameter("enhance");

		log.setLevel(Level.ALL);
		final Map<String,String[]> args = request.getParameterMap();
		for (final Map.Entry<String,String[]> e: args.entrySet()) {
		     final String[] vs = e.getValue();
		     String r = "";
		     for (String v: vs) {
			  r += v + ", ";
		     }
		     log.config(e.getKey() + " = " + r);
		}
	}

	private static String getTokenizer(String tok) throws UnrecoverableUIMAException {
		if (tok.equals("ptb")) {
			return "desc/annotators/PTBTokenizer.xml";
		} else if (tok.equals("lgp")) {
			return "desc/annotators/LingPipeTokenizer.xml";
		} else if (tok.equals("own")) {
			return "desc/annotators/SimpleTokenizer.xml";
		} else throw new UnrecoverableUIMAException("Unkonwn tokenizer: " + tok);
	}

	private static String getTagger(String tag) {
		return null;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		log.info("Requested" + request.getRequestURI());
		doGet(request, response);
	}
}
