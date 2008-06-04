package org.werti.uima.ae;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.stanford.nlp.ling.Sentence;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.util.Level;

import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;


public class SentenceBoundaryDetector extends JCasAnnotator_ImplBase {

	private static final Set<String> sentenceBoundaries = 
		new HashSet<String>(Arrays.asList(new String[]{".", "!", "?"}));
	private static final Set<String> sentenceBoundaryFollowers =
		new HashSet<String>(Arrays.asList(new String[]{")", "]", "\"", "\'", "''", }));

	/**
	 * Marks up sentences in the cas.
	 * Note that those sentences will not be part-of-speech tagged.
	 *
	 * @param cas The Cas the Tokens come from and the sentences go to.
	 */
	public void process(JCas cas) {
		getContext().getLogger().log(Level.INFO, "Starting sentence boundary detection.");

		final FSIndex textIndex = cas.getAnnotationIndex(Token.type);
		final Iterator<Token> tit = textIndex.iterator();

		Token t0       = tit.next();
		int   coh_gaps = 0;

		Sentence<Token> s = new Sentence<Token>();
		iteratetokens: while (tit.hasNext()) {
			getContext().getLogger().setLevel(Level.ALL);
			getContext().getLogger().log(Level.FINEST, "Looking at token: " + t0.word());
			final String w = t0.word();
			if (sentenceBoundaries.contains(w)) {
				getContext().getLogger().log(Level.FINEST, "Concidering " + t0.word() + "to end a sentence.");
				// start finishing.
				s.add(t0);
				final int length = t0.getEnd() - s.get(0).getBegin();
				final double coherence = calculate_coherence(length, coh_gaps);
				followerfinder: while (tit.hasNext()) {
					t0 = tit.next();
					if (!sentenceBoundaryFollowers.contains(t0.word())) 
						break followerfinder;
				}
				createSentenceAnnotation(s, coherence, cas);
				s = new Sentence<Token>();
				continue iteratetokens;
			} else {
				s.add(t0);
			}
			// adjust coherence gaps and load next element
			coh_gaps -= t0.getEnd() - (t0 = tit.next()).getBegin();
		}
		getContext().getLogger().log(Level.INFO, "Finished sentence boundary detection.");
	}

	/**
	 * Calculate Sentence's span (begin to end) and add to Cas index.
	 *
	 * Note that coherence as defined here is a rather primitive notion, but it should get the job done.
	 * Coherence of 1 is very good, worsens approaching 0.
	 *
	 * @param sentence The sentence that has to be added to the index.
	 * @param coherence The sentence's coherence measure
	 */
	private void createSentenceAnnotation(Sentence<Token> sentence, double coherence, JCas cas) {
		final SentenceAnnotation annot = new SentenceAnnotation(cas);
		annot.setBegin(sentence.get(0).getBegin());
		annot.setEnd(sentence.get(sentence.size()-1).getEnd());

		annot.setCoherence(coherence);
		annot.addToIndexes();
	}

	// holds the formula for calculating the coherence based on the length and the gap value
	private static final double calculate_coherence(int length, int coh_gaps) {
		return (length - coh_gaps)/length;
	}
}
