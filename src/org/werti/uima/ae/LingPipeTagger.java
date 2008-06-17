package org.werti.uima.ae;

import java.util.Iterator;

import com.aliasi.corpus.TagHandler;

import org.apache.uima.UimaContext;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.text.AnnotationIndex;

import org.apache.uima.examples.tagger.Tagger;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;

public class LingPipeTagger extends JCasAnnotator_ImplBase implements Tagger {

	public void process(JCas cas) {
		final AnnotationIndex sentIndex = cas.getAnnotationIndex(SentenceAnnotation.type);
		final AnnotationIndex toknIndex = cas.getAnnotationIndex(Token.type);

		final Iterator<SentenceAnnotation> sit = sentIndex.iterator();

		while (sit.hasNext()) {
			sentence.clear();
			tlist.clear();
			final SentenceAnnotation sa = sit.next();
			Iterator<Token> tit = toknIndex.subiterator(sa);

			// fill sentence
			while (tit.hasNext()) {
				final Token t = tit.next();
				tlist.add(t);
				sentence.add(new TaggedWord(t.getCoveredText()));
			}

			assert true: tlist.size() == sentence.size();
			final int size = tlist.size();
			for (int i = 0; i < size; i++) {
				final Token t = tlist.get(i);
				t.setTag(sentence.get(i).tag());
			}
		}
	}

	public void initialize(UimaContext context) {

	}

	public class WertiHandler implements TagHandler {

		public void handle (String[] tokens, String[] ws, String[] tags) {
			assert true: tokens.length == tags.length;

			for (int ii = 0; ii < tokens.length; ii++) {
				
			}
		}
	}
}
