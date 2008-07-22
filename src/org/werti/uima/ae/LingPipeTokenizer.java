package org.werti.uima.ae;

import java.util.Iterator;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

import org.apache.log4j.Logger;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.annot.RelevantText;
import org.werti.uima.types.annot.Token;

public class LingPipeTokenizer extends JCasAnnotator_ImplBase {
	private static final Logger log =
		Logger.getLogger(LingPipeTokenizer.class);

	private static final TokenizerFactory tFactory =
		new IndoEuropeanTokenizerFactory();

	/**
	 * Go through all relevant text areas in the RelevantText-AnnotationIndex and annotate them
	 * with Token-Annotations.
	 */
	@SuppressWarnings("unchecked")
	public void process(JCas cas) {
		log.info("Starting tokenization");

		final FSIndex textIndex = cas.getAnnotationIndex(RelevantText.type);
		final Iterator<RelevantText> tit = textIndex.iterator();

		int lskew = -1;
		while (tit.hasNext()) {
			final RelevantText rt = tit.next();
			final int gskew = rt.getBegin();
			final String span = rt.getCoveredText();
			if (log.isTraceEnabled()) {
				log.trace("Tokenizing: " + span);
				log.trace("Starting at " + rt.getBegin());
			}

			final Tokenizer tokenizer =
				tFactory.tokenizer(span.toCharArray(),0,span.length());

			String token;
			while ((token = tokenizer.nextToken()) != null) {
				final Token t = new Token(cas);
				final int start = gskew + (lskew = span.indexOf(token, ++lskew));
				t.setBegin(start);
				t.setEnd(start + token.length());
				t.addToIndexes();
				if (log.isTraceEnabled()) {
					log.trace("Token: " + start + " " + t.getCoveredText() + " " + t.getEnd());
				}
			}
			// reset lskew for next span (where .indexOf(String, int) doesn't make sense)
			lskew = -1;
		}
		log.info("Finished tokenization");
	}
}
