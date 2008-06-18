package org.werti;

import java.io.*;
import java.io.BufferedReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.Iterator;
import java.util.Map;

import java.util.logging.*;
import java.util.logging.Level;

import javax.servlet.*;

import javax.servlet.http.*;

import org.apache.uima.UIMAFramework;

import org.apache.uima.analysis_engine.AnalysisEngine;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.cas.StringArray;

import org.apache.uima.resource.ResourceSpecifier;

import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;

import org.werti.uima.types.Enhancement;

/**
 * This will, according to its arguments, call the appropriate classes that
 * are needed to fulfill a specific task.
 */

public class Interface extends HttpServlet {
	static final long serialVersionUID = 0;
	private static final String PREFIX = "/var/local/resources/werti/descriptors/";
	private static final String ENHNCE = PREFIX + "enhancers/PoSEnhancer.xml";

	// maximum amount of of ms to wait for a web-page to load
	private static final int MAX_WAIT = 1000 * 10;

	private static final Logger log = Logger.getLogger("org.werti");

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		//DEBUG
		log.setLevel(Level.ALL);

		// Get parameters
		final String[] tags = request.getParameter("tags").split("\\s*,\\s*");
		final String tagger = request.getParameter("tagger");
		final String termOrURL = request.getParameter("termOrUrl");
		final String tokenizer = request.getParameter("tokenizer");
		final String enhance = request.getParameter("enhance");

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

		final String descriptor = PREFIX + "operators/" + tokenizer + "-" + tagger + "-hil.xml";
		log.config("Calling descriptor " + descriptor);

		final PrintWriter out = response.getWriter();

		// start fetching the site in background
		log.fine("Fetching site " + termOrURL);
		final Fetcher fetcher = new Fetcher(termOrURL);
		fetcher.start();

		response.setContentType("text/html");

		try {
			// preprocessor
			final XMLInputSource pre_xmlin = new XMLInputSource(descriptor);
			final ResourceSpecifier pre_spec = 
				UIMAFramework.getXMLParser().parseResourceSpecifier(pre_xmlin);
			final AnalysisEngine preprocessor  = UIMAFramework.produceAnalysisEngine(pre_spec);
			final JCas cas = preprocessor.newJCas();

			// postprocessor
			final XMLInputSource post_xmlin = new XMLInputSource(ENHNCE);
			final ResourceSpecifier post_spec = 
				UIMAFramework.getXMLParser().parseResourceSpecifier(post_xmlin);
			final AnalysisEngine postprocessor  = UIMAFramework.produceAnalysisEngine(post_spec);

			log.fine("Initialized UIMA components.");

			// wait for document text to be available
			fetcher.join(MAX_WAIT);

			if (fetcher.text == null) {
				throw new RuntimeException("Webpage retrieval failed.");
			} 

			cas.setDocumentText(fetcher.text);

			preprocessor.process(cas);

			postprocessor.setConfigParameterValue("Tags", tags);
			postprocessor.setConfigParameterValue("enhance", enhance);

			postprocessor.process(cas);

			final String base_url = "http://" + fetcher.base_url + ":" + fetcher.port;

			final String enhanced = enhance(cas, base_url);

			out.print(enhanced);
		} catch (IOException ioe) {
			log.log(Level.SEVERE, "Processing of " + descriptor + " encountered errors!", ioe);
		} catch (InvalidXMLException ixmle) {
			log.log(Level.SEVERE, "XML of " + descriptor + " seems to be invalid.", ixmle);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Unknown problem occured: ", e);
		}
	}

	// converts a buffered reader to a String. Don't forget to close it.
	private static String bis2str(BufferedReader in) {
		final StringBuilder sb = new StringBuilder();
		try {
			if (!in.ready()) {
				log.severe("Input stream is broken!");
				throw new RuntimeException("No input stream to read from!");
			} else {
				log.fine("Input stream seems to be fine.");
			}
			while (in.ready()) {
				final String s = in.readLine();
				sb.append(s);
				log.log(Level.FINEST, "read in line: " + s);
			}
		} catch (IOException ioe) {
			log.log(Level.SEVERE, "Encountered errors while retrieving remote site!", ioe);
		}
		return sb.toString();
	}

	/** 
	 * Input-Enhances the CAS given with Enhancement annotations.
	 *
	 * This is an easy to understand version of enhance, using StringBuilders insert() rather
	 * than appending to it and minding skews.
	 *
	 * @param cas The cas to enhance.
	 */
	@SuppressWarnings("unchecked")
	private String enhance(JCas cas, String baseurl) {
		final String docText = cas.getDocumentText();
		final StringBuilder rtext = new StringBuilder(docText);

		int skew = docText.indexOf("<head");
		skew = docText.indexOf('>',skew)+1;

		final String basetag = "<base href=\"" + baseurl + "\" />";
		rtext.insert(skew, basetag);
		skew = basetag.length();

		final FSIndex tagIndex = cas.getAnnotationIndex(Enhancement.type);
		final Iterator<Enhancement> eit = tagIndex.iterator();

		while (eit.hasNext()) {
			final Enhancement e = eit.next();
			final StringArray sa = e.getEnhancement_list();
			final IntegerArray ia = e.getIndex_list();
			if (sa == null || ia == null) {
				log.warning("Found no eList or iList on Enhancement");
				continue;
			}
			assert true: sa.size() == ia.size();
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

	/**
	 * Retrieves the text on a web page.
	 */
	private static class Fetcher extends Thread { 
		String base_url;
		final String site_url;
		String text;
		int port;

		public Fetcher(final String url) {
			this.site_url = url;
		}

		public void run() {
			try {
				final URL url = new URL(site_url);
				port = url.getPort();
				base_url = url.getHost();
				log.fine("Host name of target URL '" + site_url +"': " + base_url);

				final URLConnection uc = url.openConnection();
				final BufferedReader content = 
					new BufferedReader(new InputStreamReader(uc.getInputStream()));
				if (content.ready()) {
					log.fine("Connections seems live.");
				} else {
					log.severe("Connection is dead.");
				}

				text = bis2str(content);

				content.close();

				log.fine("Fetched site.");
			} catch (MalformedURLException murle) {
				log.severe(site_url + " is a malformed URL!");
			} catch (IOException ioe) {
				log.severe("Error reading " + site_url);
			}
		}
	}
}
