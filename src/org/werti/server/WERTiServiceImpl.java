package org.werti.server;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.Iterator;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import org.apache.uima.UIMAFramework;

import org.apache.uima.analysis_engine.AnalysisEngine;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.cas.StringArray;

import org.apache.uima.resource.ResourceSpecifier;

import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;

import org.werti.client.WERTiService;

import org.werti.uima.types.Enhancement;

public class WERTiServiceImpl extends RemoteServiceServlet implements WERTiService {
	private static final String PREFIX = "/var/local/resources/werti/descriptors/";
	private static final String ENHNCE = PREFIX + "enhancers/PoSEnhancer.xml";
	private static final String OPERTR = PREFIX + "operators/";


	// maximum amount of of ms to wait for a web-page to load
	private static final int MAX_WAIT = 1000 * 10;

	private static final Logger log = Logger.getLogger("org.werti");

	public static final long serialVersionUID = 0;

	public String process(String[] pipeline, String[] tags, String url, String enhance) {
		//DEBUG
		log.setLevel(Level.ALL);

		log.fine("Arguments, pipeline:");
		for (String e:pipeline) {
			log.fine(e);
		}
		log.fine("Arguments, tags");
		for (String e:tags) {
			log.fine(e);
		}
		log.fine("Arguments, url: "+url);

		String descriptor = "";
		for (String e:pipeline) {
			descriptor += e + "-";
		}
		descriptor = OPERTR + descriptor.substring(0,descriptor.length()-1) + ".xml";
		log.config("Calling descriptor " + descriptor);

		log.fine("Fetching site " + url);
		final Fetcher fetcher = new Fetcher(url);
		fetcher.start();

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

			final long currentTime = System.currentTimeMillis();

			final String PATH = "/home/aleks/html/";

			final String file = "WERTi-"+currentTime+"-tmp.html";
			final FileWriter out = new FileWriter(PATH+file);
			try {
				out.write(enhanced);
			} catch (IOException ioe) {
				log.severe("Failed to create temporary file");
			}
			return "~aleks/"+file;
		} catch (IOException ioe) {
			log.log(Level.SEVERE, "Processing of " + descriptor + " encountered errors!", ioe);
		} catch (InvalidXMLException ixmle) {
			log.log(Level.SEVERE, "XML of " + descriptor + " seems to be invalid.", ixmle);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Unknown problem occured: ", e);
		}
		return null;
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

		final String basetag = "<base href=\"" + baseurl + "\" /><script type=\"text/javascript\" language=\"javascript\" src=\"http://localhost:8888/WERTi/org.werti.Enhancements-xs.nocache.js\"></script><meta name='gwt:module' content='org.werti.Enhancements'/>";
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
}
