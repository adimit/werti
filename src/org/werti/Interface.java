package org.werti;

import java.io.*;

import java.util.Iterator;
import java.util.Map;

import java.util.logging.*;
import java.util.logging.Level;

import javax.servlet.*;

import javax.servlet.http.*;

import lib.html.Net;

import org.apache.uima.UIMAFramework;

import org.apache.uima.analysis_engine.AnalysisEngine;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.cas.StringArray;

import org.apache.uima.resource.ResourceSpecifier;

import org.apache.uima.util.XMLInputSource;

import org.werti.uima.types.Enhancement;

/**
 * It will, according to its arguments, call the appropriate classes that
 * are needed to fulfill a specific task.
 */

public class Interface extends HttpServlet {
	static final long serialVersionUID = 0;
	private static final String PREFIX = "/home/aleks/src/werti/desc/operators/";

	private static final Logger log = Logger.getLogger("org.werti");

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		//DEBUG
		log.setLevel(Level.ALL);

		// Get parameters
		final String[] tags = request.getParameter("tags").split("\\s*,\\ss*");
		final String tagger = request.getParameter("tagger");
		final String termOrURL = request.getParameter("termOrUrl");
		final String tokenizer = request.getParameter("tokenizer");
		final String enhance = request.getParameter("enhance");

		final String baseurl = Net.find_baseurl(termOrURL);
		log.fine("Fetching: " + termOrURL + "; baseurl = " + baseurl);

		// log this event's arguments
		final Map<String,String[]> args = request.getParameterMap();
		for (final Map.Entry<String,String[]> e: args.entrySet()) {
		     final String[] vs = e.getValue();
		     String r = "";
		     for (String v: vs) {
			  r += v + ", ";
		     }
		     log.config(e.getKey() + " = " + r);
		}

		final String descriptor = PREFIX + tokenizer + "-" + tagger + "-hil.xml";
		log.config("Calling descriptor " + descriptor);

		final PrintWriter out = response.getWriter();
		final BufferedReader in = Net.fetch(termOrURL);

		if (!in.ready()) {
			log.severe("Input stream to " + termOrURL + " is broken!");
		}

		try {
			final XMLInputSource xmlin = new XMLInputSource(descriptor);
			final ResourceSpecifier spec = UIMAFramework.getXMLParser().parseResourceSpecifier(xmlin);
			final AnalysisEngine ae  = UIMAFramework.produceAnalysisEngine(spec);
			final JCas cas = ae.newJCas();

			cas.setDocumentText(bis2str(in));
			ae.process(cas);

			final String enhanced = enhance(cas, baseurl);
			out.print(enhanced);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Processing encountered errors!", e);
		}


		response.setContentType("text/html");

	}

	private static String bis2str(BufferedReader in) {
		StringBuilder sb = new StringBuilder();
		try {
			while (in.ready()) {
				sb.append(in.readLine());
			}
		} catch (IOException ioe) {
			log.log(Level.SEVERE, "Encountered errors while retrieving remote site!", ioe);
		}
		return sb.toString();
	}

	/** 
	 * Input-Enhances the CAS given with Enhancement annotations.
	 *
	 * This is an easy to understand version of enhance.
	 *
	 * @param cas The cas to enhance.
	 */
	@SuppressWarnings("unchecked")
	private String enhance(JCas cas, String baseurl) {
		final String docText = cas.getDocumentText();
		final StringBuilder rtext = new StringBuilder(docText);

		int skew = docText.indexOf("<head");
		skew = docText.indexOf('>',skew)+1;

		final String basetag = "<base href=\"http://" + baseurl + "\" />";
		rtext.insert(skew, basetag);
		skew = basetag.length();

		final FSIndex tagIndex = cas.getAnnotationIndex(Enhancement.type);
		final Iterator<Enhancement> eit = tagIndex.iterator();

		while (eit.hasNext()) {
			final Enhancement e = eit.next();
			final StringArray sa = e.getEnhancement_list();
			final IntegerArray ia = e.getIndex_list();
			if (sa == null || ia == null) {
				log.severe("Found no eList or iList on Enhancement");
				continue;
			}
			assert sa.size() == ia.size();
			for (int p = 0; p < sa.size(); p++) {
				final String s = sa.get(p);
				final int i = ia.get(p)+skew;

				rtext.insert(i, s);

				skew += s.length();
			}
		}
		return rtext.toString();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		log.info("Requested" + request.getRequestURI());
		doGet(request, response);
	}
}
