package org.werti.uima.ae;

import danbikel.parser.DecoderServer;
import danbikel.parser.Parser;

import org.apache.log4j.Logger;

import org.apache.uima.UimaContext;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;

public class DBParser extends JCasAnnotator_ImplBase {
	private static final Logger log =
		Logger.getLogger(DBParser.class);


	private static Parser parser;
	private DecoderServer server;

	public void initialize(UimaContext context) throws ResourceInitializationException {
		try {
			parser = new Parser("/home/aleks/var/tmp/dbparser/wsj-02-21.obj.gz");
		} catch (Exception e) {
			log.fatal(e);
		}
	}

	public void process(JCas cas) {

	}

}
