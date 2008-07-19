package org.werti.uima.ae.relevance;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.annot.RelevantText;

/**
 * Generic Relevance annotator.
 *
 * Looks at the HTML tags of a document and determines what would be suitable input 
 * for the PoS tagger and the input enhancer.
 *
 * Sometimes this will mark garbage. It might prove hard to actually fix this, though.
 */
public class WholeText extends JCasAnnotator_ImplBase {
	/**
	 * Searches for the document body, then goes on and tags everything it deems
	 * relevant as RelevantTag.
	 *
	 * The 'relevance'-notion is a primitive one. We just don't care about stuff that is
	 * enclosed in, say <i>&lt;script&gt;</i> tags.
	 */
	public void process(JCas cas) throws AnalysisEngineProcessException {
		final String text = cas.getDocumentText();
		final RelevantText all = new RelevantText(cas);
		all.setBegin(0);
		all.setEnd(text.length() -1);
		all.addToIndexes();
	}
}
