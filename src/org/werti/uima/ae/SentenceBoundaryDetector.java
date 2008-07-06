package org.werti.uima.ae;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;


public class SentenceBoundaryDetector extends JCasAnnotator_ImplBase {
	private static final Log log =
		LogFactory.getLog(SentenceBoundaryDetector.class);

	private static final Set<String> sentenceBoundaries = 
		new HashSet<String>(Arrays.asList(new String[]{".", "!", "?"}));
	private static final Set<String> sentenceBoundaryFollowers =
		new HashSet<String>(Arrays.asList(new String[]{")", "]", "\"", "\'", "''", }));

	private static final double COH_TRESHOLD = 0.1;

	/**
	 * Marks up sentences in the cas.
	 * Note that those sentences will not be part-of-speech tagged.
	 *
	 * @param cas The Cas the Tokens come from and the sentences go to.
	 */
	@SuppressWarnings("unchecked")
	public void process(JCas cas) {
		log.info("Starting sentence boundary detection.");

		final FSIndex textIndex = cas.getAnnotationIndex(Token.type);
		final Iterator<Token> tit = textIndex.iterator();

		int coh_gaps = 0;

		Token t;
		while (tit.hasNext()) {
			t = tit.next();
			log.debug("Looking at token: " + t);

			SentenceAnnotation sa = new SentenceAnnotation(cas);
			sa.setBegin(t.getBegin());

			// skip tokens unil we find a new sentence boundary
			while (tit.hasNext() && !sentenceBoundaries.contains(t.getCoveredText())) {
				// adjust coherence gaps and load next element
				coh_gaps -= t.getEnd() - (t = tit.next()).getBegin();
			}

			final int length = sa.getBegin() + t.getEnd();
			final double coherence = coherence(length, coh_gaps);

			if (!(coherence < COH_TRESHOLD)
			||  !(coherence == 1.0)) { //skip sentence boundary followers
				while (tit.hasNext()
				&&   sentenceBoundaryFollowers.contains(t.getCoveredText())) {
					t = tit.next();
				}
			}

			sa.setEnd(t.getEnd());
			sa.setCoherence(coherence);
			sa.addToIndexes();
			coh_gaps = 0;
		} 
		log.info("Finished sentence boundary detection.");
	}

	// holds the formula for calculating the coherence based on the length
	// and the gap value
	private final double coherence(int length, int coh_gaps) {
		return ((double)(length - coh_gaps))/length;
	}
}
