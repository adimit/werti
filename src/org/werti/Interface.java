package org.werti;

import java.io.*;
import java.io.BufferedReader;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

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
 * It will, according to its arguments, call the appropriate classes that
 * are needed to fulfill a specific task.
 */

public class Interface extends HttpServlet {
	static final long serialVersionUID = 0;
	private static final String PREFIX = "/var/local/resources/werti/descriptors/";
	private static final String ENHNCE = PREFIX + "enhancers/PoSEnhancer.xml";

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

		final String baseurl = find_baseurl(termOrURL);
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

		final String descriptor = PREFIX + "operators/" + tokenizer + "-" + tagger + "-hil.xml";
		log.config("Calling descriptor " + descriptor);

		final PrintWriter out = response.getWriter();
		final BufferedReader in = fetch(termOrURL);

		if (!in.ready()) {
			log.severe("Input stream to " + termOrURL + " is broken!");
		} else {
			log.info("Input stream to " + termOrURL + " seems to be fine.");
		}

		response.setContentType("text/html");

		try {
			final XMLInputSource pre_xmlin = new XMLInputSource(descriptor);
			final ResourceSpecifier pre_spec = 
				UIMAFramework.getXMLParser().parseResourceSpecifier(pre_xmlin);
			final AnalysisEngine preprocessor  = UIMAFramework.produceAnalysisEngine(pre_spec);
			final JCas cas = preprocessor.newJCas();

			final String s = bis2str(in);
			log.info("Input length = " + s.length());
			cas.setDocumentText(s);

			preprocessor.process(cas);

			final XMLInputSource post_xmlin = new XMLInputSource(ENHNCE);
			final ResourceSpecifier post_spec = 
				UIMAFramework.getXMLParser().parseResourceSpecifier(post_xmlin);
			final AnalysisEngine postprocessor  = UIMAFramework.produceAnalysisEngine(post_spec);

			postprocessor.setConfigParameterValue("Tags", tags);
			postprocessor.setConfigParameterValue("enhance", enhance);

			postprocessor.process(cas);

			final String enhanced = enhance(cas, baseurl);

			out.print(enhanced);
		} catch (IOException ioe) {
			log.log(Level.SEVERE, "Processing of " + descriptor + " encountered errors!", ioe);
		} catch (InvalidXMLException ixmle) {
			log.log(Level.SEVERE, "XML of " + descriptor + " seems to be invalid.", ixmle);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Unknown problem occured: ", e);
		}
	}


	private String bis2str(BufferedReader in) {
		final StringBuilder sb = new StringBuilder();
		try {
			if (!in.ready()) {
				log.severe("Input stream is broken!");
			} else {
				log.info("Input stream seems to be fine.");
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

	/**
	 * Open a buffered input stream to the url.
	 *  
	 * Oddly enough, this doesn't seem to work with a URLConnection (not for Reuters at least)
	 * and has to be done using the InputStream from the URL directly. This may be a bug in 
	 * Sun's libs.
	 */
	private static BufferedReader fetch(String site_url) throws MalformedURLException, IOException {
		final URL url = new URL(site_url);
		log.fine("Host name of target URL '" + site_url +"': " + url.getHost());
		final InputStream urlstream = url.openStream();
		//final URLConnection uc = url.openConnection();
		//final InputStream content = uc.getInputStream();
		return new BufferedReader(new InputStreamReader(urlstream));
	}

	/**
	 * Returns the baseURL for the given String.
	 *
	 * @param s A String containing a URL
	 *
	 * @return The baseURL of s
	 */
	private static String find_baseurl(final String s) throws MalformedURLException {
		final URL url = new URL(s);
		return url.getHost();
	}

}
