package org.werti.uima.ae;

import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;

import edu.stanford.nlp.tagger.maxent.*;

import org.apache.uima.UimaContext;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.text.AnnotationIndex;

import org.apache.uima.examples.tagger.Tagger;

import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;

import org.apache.uima.util.Level;

import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;


public class PTBTagger extends JCasAnnotator_ImplBase implements Tagger {
	
	/*
	 * Parameter definitions
	 */

	// Model file
	private static final String pModel = "MODEL_LOCATION";

	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		try {
			final String resource = (String) context.getConfigParameterValue(pModel);
			getContext().getLogger().log(Level.INFO, "Resource file should be: "
					+ resource);
			final URL rurl = ClassLoader.getSystemResource(resource);
			getContext().getLogger().log(Level.INFO, "URL.toString(): "
					+ rurl);
			getContext().getLogger().log(Level.INFO, "Constructing tagger...");
			MaxentTagger.init( "/home/aleks/src/werti/src/org/werti/resources/models/ptbtagger/bidirectional-wsj-0-18.tagger");
			getContext().getLogger().log(Level.INFO, "Done.");
		} catch (Exception e) {
			context.getLogger().log(Level.SEVERE, "Failed to initialize tagger!");
			throw new RuntimeException("No tagger, no game.", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void process(JCas cas) {
		Sentence<TaggedWord> sentence = new Sentence<TaggedWord>();
		final List<Token> tlist = new ArrayList<Token>();

		getContext().getLogger().log(Level.INFO, "Tagging...");

		try {
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
