package org.werti.server;

import java.io.FileWriter;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Iterator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import lib.html.Fetcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	private static final Log log = LogFactory.getLog(WERTiServiceImpl.class);

	private static final String PREFIX = "/desc/";
	private static final String ENHNCE = PREFIX + "enhancers/PoSEnhancer.xml";
	private static final String OPERATORS = PREFIX + "operators/";

	// maximum amount of of ms to wait for a web-page to load
	private static final int MAX_WAIT = 1000 * 10;


	public static final long serialVersionUID = 0;

	public String process(String method, String language, String[] tags, String url) {
		//DEBUG
		log.debug("Arguments, pipeline:");
		log.debug("Arguments, tags");
		for (String e:tags) {
			log.debug(e);
		}
		log.debug("Arguments, url: "+url);

		for (Object path:getServletContext().getResourcePaths("/")) {
			log.error("Path: "+path);
		}

		final URL descriptor;
		try {
			descriptor = getServletContext().getResource(OPERATORS + "ptb-ptb-hil.xml");
		} catch (MalformedURLException murle) {
			log.error("The Url is invalid!", murle);
			throw new RuntimeException("Invalid URL!");
		}

		log.debug("Fetching site " + url);
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

			log.debug("Initialized UIMA components.");

			// wait for document text to be available
			fetcher.join(MAX_WAIT);

			if (fetcher.getText() == null) {
				throw new RuntimeException("Webpage retrieval failed.");
			} 

			cas.setDocumentText(fetcher.getText());

			preprocessor.process(cas);

			postprocessor.setConfigParameterValue("Tags", tags);
			postprocessor.setConfigParameterValue("enhance", method);

			postprocessor.process(cas);

			final String base_url = "http://" + fetcher.getBase_url() + ":" + fetcher.getPort();

			final String enhanced = enhance(cas, base_url);

			final long currentTime = System.currentTimeMillis();

			final String file = "WERTi-"+currentTime+"-tmp.html";
			final FileWriter out = new FileWriter(file);
			try {
				out.write(enhanced);
			} catch (IOException ioe) {
				log.error("Failed to create temporary file");
			}
			return file;
		} catch (IOException ioe) {
			log.error("Processing of " + descriptor + " encountered errors!", ioe);
		} catch (InvalidXMLException ixmle) {
			log.error("XML of " + descriptor + " seems to be invalid.", ixmle);
		} catch (Exception e) {
			log.error("Unknown problem occured: ", e);
		}
		return null;
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
				log.warn("Found no eList or iList on Enhancement");
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
