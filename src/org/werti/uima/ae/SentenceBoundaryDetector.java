package org.werti.uima.ae;

import java.util.Iterator;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.Word;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;


public class SentenceBoundaryDetector extends JCasAnnotator_ImplBase {

	public void process(JCas cas) {
		final FSIndex textIndex = cas.getAnnotationIndex(Token.type);
		final Iterator<Token> tit = textIndex.iterator();

		Token t1 = tit.next();
		while (tit.hasNext()) {
			final Token t2 = tit.next();
			int coh_gaps = 0;



			final Sentence<Token> s = new Sentence<Token>();



		}
	}

	/**
	 * Calculate Sentence's span (begin to end) and coherence and add to Cas index.
	 *
	 * Note that coherence as defined here is a rather primitive notion, but it should get the job done.
	 * Coherence of 1 is very good, worsens approaching 0.
	 *
	 * @param sentence The sentence that has to be added to the index.
	 * @param cas The Cas that contains the index this Sentence has to be added to.
	 * @param coh_gaps The number of characters not in tokens due to html tags, punctuation or whitespace.
	 */
	private static void createSentenceAnnotation(Sentence<Token> sentence, JCas cas, int coh_gaps) {
		final SentenceAnnotation annot = new SentenceAnnotation(cas);
		annot.setBegin(sentence.get(0).getBegin());
		annot.setEnd(sentence.get(sentence.size()-1).getEnd());

		final int length = annot.getBegin() + annot.getEnd();
		final double coherence = calculate_coherence(length, coh_gaps);

		annot.setCoherence(coherence);
		annot.addToIndexes();
	}

	private static final double calculate_coherence(int length, int coh_gaps) {
		return (length - coh_gaps)/length;
	}
}
