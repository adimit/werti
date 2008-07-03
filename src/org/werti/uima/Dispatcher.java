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

import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.cas.StringArray;

import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;

import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;

import org.werti.uima.types.Enhancement;

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
			log.log(Level.SEVERE, "Failed to initialize resource. Dumping Stack:\n", rie);
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

	/** 
	 * Input-Enhances the CAS given with Enhancement annotations.
	 *
	 * This is an easy to understand version of enhance.
	 *
	 * @param cas The cas to enhance.
	 */
	private String enhance(JCas cas) {
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

	/** 
	 * Input-Enhances the CAS given with Enhancement annotations.
	 *
	 * This is a faster version of enhance. It's not working yet. DO NOT USE
	 *
	 * @param cas The cas to enhance.
	 */
	@Deprecated
	private String faster_enhance(JCas cas) {
		final String docText = cas.getDocumentText();
		final StringBuilder rtext = new StringBuilder();

		int pos = docText.indexOf("<head>")+6;
		rtext.append(docText.substring(0,pos));

		final String basetag = "<base href=\"" + baseurl + "\" />";
		int skew = basetag.length();
		rtext.append(basetag);

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
			assert true: sa.size() == ia.size();
			for (int p = 0; p < sa.size(); p++) {
				final String s = sa.get(p);
				final int i = ia.get(p)+skew;
				skew += s.length();
			}

		}
		return rtext.toString();
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
