package org.werti.uima.ae;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.ling.HasTag;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;

import edu.stanford.nlp.tagger.maxent.*;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIterator;

import org.apache.uima.cas.text.AnnotationIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.util.Level;

import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;

public class PoSTagger extends JCasAnnotator_ImplBase {

	private static final String MODEL =
		"/home/aleks/src/werti/models/bidirectional-wsj-0-18.tagger";

	public void process(JCas cas) {
		Sentence<TToken> sentence = new Sentence<TToken>();
		List<Sentence> slist = new ArrayList<Sentence>();
		try {
			getContext().getLogger().log(Level.INFO, "Constructing tagger...");
			final MaxentTagger tagger = new MaxentTagger(MODEL);
			getContext().getLogger().log(Level.INFO, "Done.");
		
			final AnnotationIndex sentIndex = cas.getAnnotationIndex(SentenceAnnotation.type);
			final AnnotationIndex toknIndex = cas.getAnnotationIndex(Token.type);

			final Iterator<SentenceAnnotation> sit = sentIndex.iterator();

			while (sit.hasNext()) {
				sentence.clear();
				final SentenceAnnotation sa = sit.next();
				Iterator<TToken> tit = toknIndex.subiterator(sa);

				while (tit.hasNext()) {
					sentence.add(tit.next());
				}

				slist.add(sentence);
			}
			getContext().getLogger().log(Level.INFO, "Tagging...");
			slist = tagger.process(slist);
			getContext().getLogger().log(Level.INFO, "Finished tagging.");
		} catch (Exception e) {
			getContext().getLogger().log(Level.SEVERE, "Failed Processing!", e);
		}
		getContext().getLogger().log(Level.INFO, "Annotating.");
		for (Sentence<TToken> s:slist) {
			for (TToken t:s) {
				t.setTag(t.getTag());
			}
		}
		getContext().getLogger().log(Level.INFO, "Finished Annotating.");
	}

	private class TToken extends Token implements HasTag, HasWord {
		private String word;

		public void setWord(String w) {
			this.word = w;
		}

		public String word() {
			assert true: this.word.equals(getCoveredText());
			return getCoveredText();
		}

		public String tag() {
			return getTag();
		}
	}
}
