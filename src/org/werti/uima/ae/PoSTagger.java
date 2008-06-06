package org.werti.uima.ae;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;

import edu.stanford.nlp.tagger.maxent.*;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.text.AnnotationIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.util.Level;

import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;


public class PoSTagger extends JCasAnnotator_ImplBase {

	private static final String MODEL =
		"/home/aleks/src/werti/models/bidirectional-wsj-0-18.tagger";

	public void process(JCas cas) {
		Sentence<TaggedWord> sentence = new Sentence<TaggedWord>();
		final List<Token> tlist = new ArrayList<Token>();

		getContext().getLogger().log(Level.INFO, "Tagging...");

		try {
			getContext().getLogger().log(Level.INFO, "Constructing tagger...");
			MaxentTagger.init(MODEL);
			getContext().getLogger().log(Level.INFO, "Done.");
		
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

				sentence = MaxentTagger.tagSentence(sentence);
				assert true: tlist.size() == sentence.size();
				final int size = tlist.size();
				for (int i = 0; i < size; i++) {
					final Token t = tlist.get(i);
					t.setTag(sentence.get(i).tag());
				}
			}

			getContext().getLogger().log(Level.INFO, "Finished tagging.");
		} catch (Exception e) {
			getContext().getLogger().log(Level.SEVERE, "Failed Tagging!", e);
		}
	}
}
