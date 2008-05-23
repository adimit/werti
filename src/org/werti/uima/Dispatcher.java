package org.werti.uima;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.util.logging.*;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;

import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;



public class Dispatcher {

	private final JCas cas;
	private final AnalysisEngine ae;

	private static final Logger log = Logger.getLogger("org.werti");

	/**
	 * Initialize with default config.
	 *
	 * I wouldn't like this to get called, but... just in case, this relies on the
	 * Config() (constructor with no arguments) to give a meaninful result. In the
	 * case it does not, see comments for Dispatcher(Config).
	 */
	public Dispatcher() throws UnrecoverableUIMAException {
		this(new Config());
	}

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
	public Dispatcher(Config cfg) throws UnrecoverableUIMAException {
		try {
			final XMLInputSource in = new XMLInputSource(cfg.getDescriptor());
			final ResourceSpecifier spec = 
				UIMAFramework
				.getXMLParser()
				.parseResourceSpecifier(in);
			ae  = UIMAFramework.produceAnalysisEngine(spec);
			cas = ae.newJCas();
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
	private synchronized void process(String s) {

	}

	public void print(PrintWriter out) {
		log.fine("Printing results to given PrintWriter");
	}

	public String get_results() {
		log.fine("Returning the results.");
		return null;
	}
}
