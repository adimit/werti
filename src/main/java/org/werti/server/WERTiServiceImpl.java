package org.werti.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
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
import org.werti.client.run.RunConfiguration;
import org.werti.client.util.Tuple;
import org.werti.uima.types.Enhancement;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the WERTi service.
 *
 * This is were the work is coordinated. The rough outline of the procedure is as follows:
 *
 * <li>We take a request via the <tt>process</tt> method</li>
 * <li>We Construct the analysis engines (post and preprocessing) according to the request</li>
 * <li>In the meantime lib.html.Fetcher is invoked to fetch the requested site off the Internet</li>
 * <li>Then everything is pre-processed in UIMA, invoking the right preprocessor for the task at hand</li>
 * <li>We take the resulting CAS and post-process it, invoking the right post processor for the task at hand</li>
 * <li>We add some necessary headers to the page (<tt>&lt;base&gt;</tt> tag and JS sources).</li>
 * <li>Afterwards we take the CAS and insert enhancement annotations
 * (<tt>WERTi</tt>-<tt>&lt;span&gt;</tt>s) according to the target annotations by the post-processing.</li>
 * <li>Finally, a temporary file is written to, which holds the results</li>
 *
 * Currently, there is no real API for binding into this procedure. This should ultimately change.
 * In order to incorporate new features, modifications to this file will have to be made... :-(
 *
 * @author Aleksandar Dimitrov
 * @version 0.2
 */
public class WERTiServiceImpl extends RemoteServiceServlet implements WERTiService {
	private static final Logger log =
		Logger.getLogger(WERTiServiceImpl.class);

	public static WERTiContext context;

	public static final long serialVersionUID = 10;

	private static void configEngine(AnalysisEngine ae, List<Tuple> config) {
		if (config == null) {
			log.warn("No configuration found for " + ae.getClass().getName());
			return;
		}

		log.debug("Configuring " + ae.getClass().getName());
		for (Tuple e:config) {
			log.debug("Configuring: " + e.fst() + " with " + e.snd());
			ae.setConfigParameterValue(e.fst(), e.snd());
		}
	}

	/**
	 * The implementation of the process method according to the interface specifications.
	 *
	 * @param config The <code>RunConfiguration</code> for this request.
	 * @param url The URL of the page the user has requested.
	 */
	public String process(RunConfiguration config, String urlString)
		throws URLException, InitializationException, ProcessingException {
		context = new WERTiContext(getServletContext());

		if (log.isDebugEnabled()) { // dump configuration to log
			final StringBuilder sb = new StringBuilder();
			sb.append("\nURL: "+ urlString);
			sb.append("\nConfiguration: " + config.getClass().getName());
			sb.append("\nPre-processor: " + config.preprocessor());
			sb.append("\n\tlocation: : " + context.getProperty(config.preprocessor()));
			sb.append("\nPost-processor: " + config.postprocessor());
			sb.append("\n\tlocation: : " + context.getProperty(config.postprocessor()));
			log.debug(sb.toString());
		}

		final URL url = maybeGetURL(urlString); // URL validation
		final AnalysisEngine preprocessor = maybeGetAnalysisEngine(config.preprocessor());
		final AnalysisEngine postprocessor = maybeGetAnalysisEngine(config.postprocessor());
		
		final JCas cas = maybeCreateCas(preprocessor);
		final JCas htmlCas = maybeGetJCasView(cas, "html");
		htmlCas.setSofaDataURI(urlString, "text/hmtl");
		
		configEngine(preprocessor, config.preconfig());
		configEngine(postprocessor, config.postconfig());

		try { // to process
			preprocessor.process(cas);
			postprocessor.process(cas);
		} catch (AnalysisEngineProcessException aepe) {
			log.fatal("Analysis Engine encountered errors!", aepe);
			throw new ProcessingException("Text analysis failed.", aepe);
		}
		
		/* UGLY FUCK SHIT FOLLOWS */
		final String base_url = url.getProtocol() + url.getHost() + url.getPort();
		final String enhanced = enhance(config.enhancer(), cas, base_url);
		final String tempDir = getServletContext().getRealPath("/tmp");
		final File temp;
		try { // to create a temporary file
			temp = File.createTempFile("WERTi", ".tmp.html", new File(tempDir));
		} catch (IOException ioe) {
			log.error("Failed to create temporary file");
			throw new ProcessingException("Failed to create temporary file!", ioe);
		}
		try { // to write to the temporary file
			if (log.isDebugEnabled()) {
				log.debug("Writing to file: " + temp.getAbsoluteFile());
			}
			final FileWriter out = new FileWriter(temp);
			out.write(enhanced);
			out.close();
		} catch (IOException ioe) {
			log.error("Failed to write to temporary file");
			throw new ProcessingException("Failed write to temporary file!", ioe);
		}
		return "/tmp/" + temp.getName();
	}
	
	private JCas maybeCreateCas(AnalysisEngine ae) throws InitializationException {
		try {
			return ae.newJCas();
		} catch (ResourceInitializationException rie) {
			log.fatal("Failed to create CAS object from analysis engine.");
			throw new InitializationException("Failed to create CAS object.", rie);
		}
	}
	
	private JCas maybeGetJCasView(JCas cas, String viewName) throws InitializationException {
		try {
			return cas.createView(viewName);
		} catch (CASException ce) {
			log.fatal("Cas name collision. This shouldn't happen.");
			throw new InitializationException("Cas  name collision. Bad thing.", ce);
		} 
	}
	
	private URL maybeGetURL(String urlString) throws URLException {
		try {
			return new URL(urlString);	
		} catch (MalformedURLException murle) {
			log.fatal("Unrecoverable: invalid URL provided: " + urlString);
			throw new URLException("Could not parse URL.", murle);
		}
	}
	
	private AnalysisEngine maybeGetAnalysisEngine(String aeName) throws InitializationException {
		final String descriptorRoot = context.getProperty("descriptorPath");
		log.trace("Loading AE from descriptor path: " + descriptorRoot);
		final URL descriptor;
		try { // to load the descriptor
			descriptor = getServletContext().getResource(descriptorRoot + context.getProperty(aeName));
		} catch (MalformedURLException murle) {
			log.fatal("Invalid descriptor file url!");
			throw new InitializationException("Could not instantate analysis engine.", murle);
		}
		try { // to initialize the UIMA components
			final XMLInputSource xmlin = new XMLInputSource(descriptor);
			final ResourceSpecifier spec = UIMAFramework.getXMLParser().parseResourceSpecifier(xmlin);
			return UIMAFramework.produceAnalysisEngine(spec);
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
		
		HashMap<Integer, String> insertedTags = new HashMap<Integer, String>();
		
		// TODO: make sure this maintains the correct order of closing tags

		// collect all enhancements on positions
		while (eit.hasNext()) {
			Enhancement e = eit.next();

			// add beginning of enhancement to the position hash
			int begin = e.getBegin();
			if ( ! insertedTags.containsKey(begin)) {
				insertedTags.put(begin, e.getEnhanceStart());
			} else {
				insertedTags.put(begin, insertedTags.get(begin) + e.getEnhanceStart());
			}

			// add end of enhancement to the position hash
			int end = e.getEnd();
			if ( ! insertedTags.containsKey(end)) {
				insertedTags.put(end, e.getEnhanceEnd());
			} else {
				insertedTags.put(end, e.getEnhanceEnd() + insertedTags.get(end));
			}
		}
		
		// obtain sorted key set
		LinkedList<Integer> positions = new LinkedList<Integer>(insertedTags.keySet());
		Collections.sort(positions);
		
		// loop over position hash and insert enhancement tags into document text using skew
		for ( Integer pos : positions ) {
			String insert = insertedTags.get(pos);
			log.trace("Enhancement: position hash at " + pos+ ": " + insert);
			rtext.insert(pos + skew, insert);
			skew += insert.length();
		}
		
		return rtext.toString();
	}
}
