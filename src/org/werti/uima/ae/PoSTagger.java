package org.werti.uima.ae;

import java.util.Iterator;

import edu.stanford.nlp.ling.Sentence;

import edu.stanford.nlp.tagger.maxent.*;


import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.util.Level;

import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;

public class PoSTagger extends JCasAnnotator_ImplBase {

	public void process(JCas cas) {
		getContext().getLogger().setLevel(Level.ALL);
		final FSIndex textIndex = cas.getAnnotationIndex(SentenceAnnotation.type);
		final Iterator<SentenceAnnotation> tit = textIndex.iterator();
		try {
			getContext().getLogger().log(Level.INFO, "Constructing Tagger");
			final MaxentTagger tagger = new MaxentTagger("/home/aleks/src/werti/models/bidirectional-wsj-0-18.tagger");
			getContext().getLogger().log(Level.INFO, "Finished constructing Tagger");
			getContext().getLogger().log(Level.INFO, "Starting tagging proces.");
			while (tit.hasNext()) {
				final Sentence<Token> s = tit.next().getSentence();
				if (s == null) {
					getContext().getLogger().log(Level.WARNING,
							"Sentence is null");
				}
				MaxentTagger.tagSentence(s);
				for (Token t:s) {
					getContext().getLogger().log(Level.FINE, 
							"\n**Token**\nWord: " + t.word() + "; Tag: " + t.tag());
				}
			}
			getContext().getLogger().log(Level.INFO, "Finished tagging proces.");
		} catch (Exception e) {
			getContext().getLogger().log(Level.SEVERE, "Failed Processing!", e);
		}
	}
}
