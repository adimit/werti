package org.werti.uima.ae;

import java.util.Iterator;

import edu.stanford.nlp.tagger.maxent.*;


import java.util.logging.*;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.annot.RelevantText;

public class PoSTagger extends JCasAnnotator_ImplBase {
	private static final Logger log = Logger.getLogger("org.werti");

	public void process(JCas cas) {
		final FSIndex textIndex = cas.getAnnotationIndex(RelevantText.type);
		final Iterator<RelevantText> tit = textIndex.iterator();
		final String s = cas.getDocumentText();
		try {
			final MaxentTagger tagger = new MaxentTagger("bidirectional/wsj3t0-18.holder");
		} catch (Exception e) {
			log.severe("Tagger failed to construct!");
		}
		RelevantText textspan = null;
		String text = "";
		while (tit.hasNext()) {
			textspan = (RelevantText)tit.next();
		}
	}
}
