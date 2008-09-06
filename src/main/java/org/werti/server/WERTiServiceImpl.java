package org.werti.server;

import java.io.FileWriter;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Iterator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import org.werti.util.Fetcher;

import org.apache.log4j.Logger;

import org.apache.uima.UIMAFramework;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;

import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;

import org.werti.WERTiContext;

import org.werti.client.InitializationException;
import org.werti.client.ProcessingException;
import org.werti.client.URLException;
import org.werti.client.WERTiService;

import org.werti.uima.types.Enhancement;

/**
 * The server side implementation of the WERTi service.
 *
 * This is were the work is coordinated. The rough outline of the procedure is as follows:
 *
 * <li>We take a request via the <tt>process</tt> method</li>
 * <li>We Construct the analysis engines (post and preprocessing) according to the request</li> 
 * <li>In the meantime lib.html.Fetcher is invoked to fetch the requested site off the Internet</li>
 * <li>Then everything is pre-processed in UIMA, invoking the right pre processor for the task at hand</li>
 * <li>We take the resulting CAS and post-process it, invoking the right post processor for the task at hand</li>
 * <li>We add some neccessary headers to the page (<tt>&lt;base&gt;</tt> tag and JS sources).</li>
 * <li>Afterwards we take the CAS and insert enhancement annotations
 * (<tt>WERTi</tt>-<tt>&lt;span&gt;</tt>s) according to the target annotations by the post-processing.</li>
 * <li>Finally, a temporary file is written to, which holds the results</li>
 *
 * In order to incorporate new features changes to this are probably neccessary.
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public class WERTiServiceImpl extends RemoteServiceServlet implements WERTiService {
	private static final Logger log =
		Logger.getLogger(WERTiServiceImpl.class);

	// maximum amount of of ms to wait for a web-page to load
	private static final int MAX_WAIT = 1000 * 10; // 10 seconds

	public static WERTiContext context;

	public static final long serialVersionUID = 10;

	/**
	 * The implementation of the process method according to the interface specifications.
	 *
	 * @param method The task the user has requested.
	 * @param language The language the user says the target is in.
	 * @param tags A bunch of part-of-speech tags you want to highlight.
	 * @param url The URL of the page the user has requested.
	 */
	public String process(String method, String language, String[] tags, String url) 
		throws URLException, InitializationException, ProcessingException {
		context = new WERTiContext(getServletContext());

		if (log.isDebugEnabled()) { // dump arguments to log
			final StringBuilder sb = new StringBuilder();
			sb.append( "\nMethod: " + method + "\nLanguage" + language + "\nTags: ");
			for (int ii = 0; ii < tags.length; ii++) {
				sb.append(tags[ii]+" ");
			}
			sb.append("\nURL: "+ url);
		}

		log.debug("Fetching site " + url);
		final Fetcher fetcher;
		try {
			fetcher = new Fetcher(url);
		} catch (MalformedURLException murle) {
			throw new URLException(murle);
		}
		fetcher.start();

		final JCas cas;
		final AnalysisEngine preprocessor, postprocessor;
		final String descPath = context.getProperty("descriptorPath");
		log.trace("Loading from descriptor path: " + descPath);
		final URL preDesc, postDesc;
		try { // to load the descriptor
			preDesc = getServletContext().getResource(
					descPath + context.getProperty("aggregate.default"));
			if (method.equals("Ask")) {
				postDesc = getServletContext().getResource
					(descPath + context.getProperty("enhancer.token"));
			} else {
				postDesc = getServletContext().getResource
					(descPath + context.getProperty("enhancer.pos"));
			}
		} catch (MalformedURLException murle) {
			log.fatal("Unrecoverable: Couldn't find aggregate descriptor file!");
			throw new InitializationException("Couldn't instantiate operator.", murle);
		}

		try { // to initialize UIMA components
			final XMLInputSource pre_xmlin = new XMLInputSource(preDesc);
			final ResourceSpecifier pre_spec = 
				UIMAFramework.getXMLParser().parseResourceSpecifier(pre_xmlin);
			preprocessor  = UIMAFramework.produceAnalysisEngine(pre_spec);
			cas = preprocessor.newJCas();

			final XMLInputSource post_xmlin = new XMLInputSource(postDesc);
			final ResourceSpecifier post_spec = 
				UIMAFramework.getXMLParser().parseResourceSpecifier(post_xmlin);
			postprocessor  = UIMAFramework.produceAnalysisEngine(post_spec);
		} catch (InvalidXMLException ixmle) {
			log.fatal("Error initializing XML code. Invalid?", ixmle);
			throw new InitializationException("Error initializing XML code. Invalid?", ixmle);
		} catch (ResourceInitializationException rie) {
			log.fatal("Error initializing resource", rie);
			throw new InitializationException("Error initializing resource", rie);
		} catch (IOException ioe) {
			log.fatal("Error accessing descriptor file", ioe);
			throw new InitializationException("Error accessing descriptor file", ioe);
		} catch (NullPointerException npe) {
			log.fatal("Error accessing descriptor files or creating analysis objects", npe);
			throw new InitializationException
				("Error accessing descriptor files or creating analysis objects", npe);
		}
		log.info("Initialized UIMA components.");

		try { // to wait for document text to be available
			fetcher.join(MAX_WAIT);
		} catch (InterruptedException itre) {
			log.error("Fetcher recieved interrupt. This shouldn't happen, should it?", itre);
		}

		if (fetcher.getText() == null) { // if we don't have text, that's bad
			log.error("Webpage retrieval failed! " + fetcher.getBase_url());
			throw new InitializationException("Webpage retrieval failed.");
		} 

		log.error("fetcher-text:" + fetcher.getText());
		cas.setDocumentText(fetcher.getText());

		try { // to process
			postprocessor.setConfigParameterValue("Tags", tags);
			postprocessor.setConfigParameterValue("enhance", method);

			preprocessor.process(cas);
			postprocessor.process(cas);
		} catch (AnalysisEngineProcessException aepe) {
			log.fatal("Analysis Engine encountered errors!", aepe);
			throw new ProcessingException("Text analysis failed.", aepe);
		}

		final String base_url = "http://" + fetcher.getBase_url() + ":" + fetcher.getPort();
		final String enhanced = enhance(method, cas, base_url);
		final long currentTime = System.currentTimeMillis();
		final String file = "/tmp/WERTi-" + currentTime + ".html";

		try { // to write temp file
			if (log.isDebugEnabled()) {
				log.debug("Writing to file: " + getServletContext().getRealPath(file));
			}
			final FileWriter out = new FileWriter(getServletContext().getRealPath(file));
			out.write(enhanced);
		} catch (IOException ioe) {
			log.error("Failed to create temporary file");
			throw new ProcessingException("Failed to create temporary file!", ioe);
		}
		return file;
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
	private String enhance(final String method, final JCas cas, final String baseurl) {
		final String docText = cas.getDocumentText();
		final StringBuilder rtext = new StringBuilder(docText);

		int skew = docText.indexOf("<head");
		skew = docText.indexOf('>',skew)+1;

		final String basetag = "<base href=\"" + baseurl + "\" />"
			// GWT main JS module
			+ "<script type=\"text/javascript\" language=\"javascript\" src=\""
			+ context.getProperty("this-server") + "/WERTi/org.werti.enhancements."
			+ method
			+ "/org.werti.enhancements."
			+ method
			+ ".nocache.js\"></script>";
		rtext.insert(skew, basetag);
		skew = basetag.length();

		final FSIndex tagIndex = cas.getAnnotationIndex(Enhancement.type);
		final Iterator<Enhancement> eit = tagIndex.iterator();

		while (eit.hasNext()) {
			final Enhancement e = eit.next();
			if (log.isTraceEnabled()){
				log.trace("Enhancement starts at " + e.getBegin()
					+ " and ends at " + e.getEnd()
					+ "; Current skew is: " + skew);
			}

			final String start_tag = e.getEnhanceStart();
			rtext.insert(e.getBegin() + skew, start_tag);
			skew += start_tag.length();

			final String end_tag = e.getEnhanceEnd();
			rtext.insert(e.getEnd() + skew, end_tag);
			skew += end_tag.length();
		}
		return rtext.toString();
	}
}
