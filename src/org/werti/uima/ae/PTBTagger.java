package org.werti.uima.ae;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import org.apache.log4j.Logger;

import org.apache.uima.UimaContext;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

import org.apache.uima.cas.text.AnnotationIndex;

import org.apache.uima.examples.tagger.Tagger;

import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;

import org.werti.WERTiContext;

import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;


public class PTBTagger extends JCasAnnotator_ImplBase implements Tagger {
	private static final Logger log =
		Logger.getLogger(PTBTagger.class);

	private static MaxentTagger tagger;

	public void initialize(UimaContext context) throws ResourceInitializationException {
		WERTiContext.getPtbtagger("en");
	}

	@SuppressWarnings("unchecked")
	public void process(JCas cas) throws AnalysisEngineProcessException {
		final List<Token> tlist = new ArrayList<Token>();
		Sentence<TaggedWord> sentence = new Sentence<TaggedWord>();

		log.info("Tagging...");
		try {
			final AnnotationIndex sentIndex = cas.getAnnotationIndex(SentenceAnnotation.type);
			final AnnotationIndex toknIndex = cas.getAnnotationIndex(Token.type);

			final Iterator<SentenceAnnotation> sit = sentIndex.iterator();

			while (sit.hasNext()) { // iterate over all sentences
				sentence.clear();
				tlist.clear();
				final SentenceAnnotation sa = sit.next();
				Iterator<Token> tit = toknIndex.subiterator(sa);

				while (tit.hasNext()) { // fill sentence
					final Token t = tit.next();
					tlist.add(t);
					sentence.add(new TaggedWord(t.getCoveredText()));
				}

				sentence = tagger.processSentence(sentence);
				assert true: tlist.size() == sentence.size();
				final int size = tlist.size();   // tag the words in the CAS 
				for (int i = 0; i < size; i++) { // that are tagged in the sentence
					final Token t = tlist.get(i);
					t.setTag(sentence.get(i).tag());
				}
			}
			log.info("Finished tagging.");
		} catch (Exception e) {
			throw new AnalysisEngineProcessException(e);
		}
	}
}
