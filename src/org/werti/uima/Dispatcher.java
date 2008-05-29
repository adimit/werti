package org.werti.uima;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.util.Iterator;

import java.util.logging.*;

import org.apache.uima.UIMAFramework;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;

import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;

import org.werti.uima.types.annot.PoSTag;

public class Dispatcher {

	private final JCas cas;
	private final AnalysisEngine ae;
	private final PrintWriter out;
	private final String baseurl;

	private static final Logger log = Logger.getLogger("org.werti");

	/**
	 * Initialize the Dispatcher with a given config.
	 *
	 * We initialize the dispatcher's fields, namely we construct the given analysis 
	 * engine and the CAS.
	 *
	 * This can be a source of breakdown. If we can't initialize because we can't read
	 * or find the XML descriptor, of it its XML is corrupt, we have to abort 
	 * ungracefully. I know no other way around it. Inserting a default value in 
	 * this case is bound to cause all sorts of trouble, especially if the error is
	 * system wide (all descriptors unreadable, etc...)
	 *
	 * We therefore throw an UrecoverableUIMAException to inform the caller that
	 * they should go fix teir code. Be careful what you throw at this thing.
	 */
	public Dispatcher(Config cfg, PrintWriter out) throws UnrecoverableUIMAException {
		try {
			final XMLInputSource in = new XMLInputSource(cfg.getDescriptor());
			baseurl = cfg.getBaseURL();
			final ResourceSpecifier spec = 
				UIMAFramework
				.getXMLParser()
				.parseResourceSpecifier(in);
			ae  = UIMAFramework.produceAnalysisEngine(spec);
			cas = ae.newJCas();
			this.out = out;
		} catch (IOException ioe) {
			log.severe("Couldn't open despcriptor at: " + cfg.getDescriptor());
			throw new UnrecoverableUIMAException(ioe);
		} catch (InvalidXMLException ixmle) {
			log.severe("Check your XML in: " + cfg.getDescriptor());
			throw new UnrecoverableUIMAException(ixmle);
		} catch (ResourceInitializationException rie) {
			log.severe("Failed to initialize resource. Dumping Stack:\n"
					+ rie.getStackTrace());
			throw new UnrecoverableUIMAException(rie);
		} 
	}

	/**
	 * Start the analysis process, according to the AE Descriptor given to this object's 
	 * constructor. 
	 * 
	 * This method is thread safe, but it may block. A long time. It has therefore
	 * been decoupled from retrieving the results, so it can be called asynchronously.
	 */
	public synchronized void process(BufferedReader bin) {
		log.info("Starting Analysis.");
		try {
			String s = "";
			while (bin.ready()) {
				s += bin.readLine();
			}
			process(s);
		} catch (IOException ioe) {
			log.severe("Couldn't read from website input stream");
		} catch (UnrecoverableUIMAException uuimae) {
			log.severe("Something baaaad happenend");
		}
	}

	/**
	 * See process(BufferedReader).
	 */
	public synchronized void process(InputStream in) {
		process(new BufferedReader(new InputStreamReader(in)));
	}
	
	/*
	 * Private helper to process a String s. AEs can only have full strings set as
	 * analysis objects.
	 */
	private synchronized void process(String s) 
		throws UnrecoverableUIMAException {
		cas.setDocumentText(s);
		try {
			ae.process(cas);
		} catch (AnalysisEngineProcessException aepe) {
			log.severe("Problems processing data!");
			throw new UnrecoverableUIMAException(aepe);
		}
	}

	private String enhance(JCas cas) {
		String docText = cas.getDocumentText();
		final int foo = docText.indexOf("<head>")+6;
		docText = docText.substring(0,foo) + "<base href=\"" + baseurl + "\" />" + docText.substring(foo,docText.length()-1);
		final FSIndex tagIndex = cas.getAnnotationIndex(PoSTag.type);
		final Iterator<PoSTag> pit = tagIndex.iterator();
		int skew = 0;
		while (pit.hasNext()) {
			final PoSTag tag = pit.next();
			if (tag.getPoS().equals("NN")) {
				log.info("found a noun");
				docText = docText.substring(skew, (skew = docText.indexOf(tag.getWord())))
					+ "<font color=\"#FF0000>"
					+ tag.getWord()
					+ "</font>"
					+ docText.substring((skew = skew + tag.getWord().length()), docText.length() - 1);
			}
		}
		return docText;
	}

	public void print(PrintWriter out) {
		log.fine("Printing results to given PrintWriter");
		out.print(cas.getDocumentText());
	}

	public String get_results() {
		log.fine("Returning the results.");
		return enhance(cas);
	}
}
