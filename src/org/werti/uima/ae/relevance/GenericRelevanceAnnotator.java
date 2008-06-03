package org.werti.uima.ae.relevance;

import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.util.Level;

import org.werti.uima.types.annot.HTML;
import org.werti.uima.types.annot.RelevantText;

/**
 * Generic Relevance annotator.
 *
 * Looks at the HTML tags of a document and determines what would be suitable input 
 * for the PoS tagger and the input enhancer.
 *
 * Sometimes this will mark garbage. It might prove hard to actually fix this, though.
 */
public class GenericRelevanceAnnotator extends JCasAnnotator_ImplBase {

	private static final int RELEVANCE_TRESHOLD = 30;
	public void process(JCas cas) {
		final FSIndex tagIndex = cas.getAnnotationIndex(HTML.type);
		final Iterator<HTML> tit = tagIndex.iterator();
		HTML tag = tit.next();
		if (tag == null) {
			getContext().getLogger().log(Level.SEVERE, 
					"HTML tag was null while starting to search for relevance.");
		}
		boolean inBody = false;
		titerator: while (tit.hasNext()){
			final int t = tag.getEnd();
			final String tname = tag.getTag_name();
			if (!inBody) {
				if (tname.equals("body")
				||  tname.equals("/head")) {
					getContext().getLogger().log(Level.INFO,
								"HTML tag was " + tname + ". Switching to body.");
					inBody = true;
				}
				tit.next();
				continue titerator;
			}
			if (RELEVANCE_TRESHOLD < ((tag = tit.next()).getBegin() - t)) {
				final RelevantText rt = new RelevantText(cas);
				rt.setBegin(t);
				rt.setEnd(tag.getBegin());
				rt.setEnclosing_tag(tag.getTag_name());
				rt.addToIndexes();
			}
		}
	}
}
