package org.werti.uima;

import java.io.IOException;
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

	public Dispatcher() {
		this(new Config());
	}

	public Dispatcher(Config cfg) {
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
		} catch (InvalidXMLException ixmle) {
			log.severe("Check your XML in: " + cfg.getDescriptor());
		} catch (ResourceInitializationException rie) {
			log.severe("Failed to initialize resource. Dumping Stack:\n"
					+ rie.getStackTrace());
		} 
	}

	public synchronized void process() {
		log.info("Starting Analysis.");
	}

	public void print(PrintWriter out) {
		log.fine("Printing results to given PrintWriter");
	}

	public String get_results() {
		log.fine("Returning the results.");
		return null;
	}
}
