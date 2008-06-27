package org.werti.uima.ae;

import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.util.Level;

import org.werti.uima.types.annot.RelevantText;
import org.werti.uima.types.annot.Token;

public class SimpleTokenizer extends JCasAnnotator_ImplBase {

	/**
	 * Go through all relevant text areas in the RelevantText-AnnotationIndex and annotate them
	 * with Token-Annotations.
	 *
	 * We only set the CAS-relative Begin and End, but also set the Word-Property of Token for now. This may
	 * change in the future for efficiency reasons.
	 */
	@SuppressWarnings("unchecked")
	public void process(JCas cas) {
		getContext().getLogger().log(Level.INFO, "Starting tokenization process.");
		final FSIndex textIndex = cas.getAnnotationIndex(RelevantText.type);
		final Iterator<RelevantText> tit = textIndex.iterator();

		while (tit.hasNext()) {
			final RelevantText rt = tit.next();
			final char[] span = rt.getCoveredText().toLowerCase().toCharArray();

			// global and local skews
			final int gskew = rt.getBegin();
			int lskew = gskew;

			// note that we guarantee a length of 1 in the GenericRelevanceAnnotator
			for (int i = 1; i < span.length; i++) {
				if ((Character.getType(span[i]) != Character.getType(span[i-1]))
				||   Character.getType(span[i]) == Character.DIRECTIONALITY_WHITESPACE
				||   span[i] == '.'
				&&  (span[i] == '-' && span[i-1] != Character.DIRECTIONALITY_WHITESPACE)
				) {
					final Token t = new Token(cas);
					t.setBegin(lskew);
					t.setEnd((lskew = gskew + i));
					t.addToIndexes();
				}
			}
		}
		getContext().getLogger().log(Level.INFO, "Finished tokenization process.");
	}
}
