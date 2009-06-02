package org.werti.uima.ae;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aliasi.hmm.HiddenMarkovModel;
import com.aliasi.hmm.HmmDecoder;

import org.apache.log4j.Logger;

import org.apache.uima.UimaContext;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.text.AnnotationIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;

import org.werti.WERTiContext;

import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;

import org.werti.util.Resources;

/**
 * A wrapper around the LingPipe Tagger.
 *
 * It retrieves a Hidden Markov model tagger from the <tt>WERTiContext</tt> and
 * then iterates over all sentences and tags all their token annotations.
 *
 * This is currently the standard tagger, but supports only English.
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public class LingPipeTagger extends JCasAnnotator_ImplBase {
	private static final Logger log =
		Logger.getLogger(LingPipeTagger.class);

	// the average length of a sentence (for performance reasons, this denotes our
	// dynamic data structure's initial capacity)
	private static final int SENTENCE_LENGTH = 30;

	private static HmmDecoder tagger;

	public void initialize2(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		if (tagger == null) {
			if (context.getConfigParameterValue("runningOnServer") != null) {
				Boolean runningOnServer = (Boolean) context.getConfigParameterValue("runningOnServer");
				if (runningOnServer) {
					tagger = WERTiContext.getLpgtagger();
				} else {
					try {
						tagger = getLpgtagger((String)context.getConfigParameterValue("modelLocation"));
					} catch (Exception e) {
						throw new ResourceInitializationException(e);
					}
				}
			} else {
				tagger = WERTiContext.getLpgtagger();
			}
		}
	}

	/*
	 * helper for loading models without werti context
	 */
	private HmmDecoder getLpgtagger(String modelLoc) throws FileNotFoundException, IOException, ClassNotFoundException {
		final HiddenMarkovModel hmm;
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(modelLoc));
		hmm = (HiddenMarkovModel) ois.readObject();
		ois.close();
		return new HmmDecoder(hmm);
	}
	
	/**
	 * Tag using the lingPipe <code>HmmDecoder</code>.
	 *
	 * We have to make a three pass, as usual, since the HmmDecoder just gives strings
	 * and also expects to find a static String[].
	 *
	 * @param cas The CAS with token and sentence annotations.
	 */
	@SuppressWarnings("unchecked")
	public void process(JCas cas) {
		log.info("Starting tagging process...");

		// don't forget to .clear() the list on every new sentence.
		final List<Token> tlist = new ArrayList<Token>(SENTENCE_LENGTH);

		final AnnotationIndex sindex = cas.getAnnotationIndex(SentenceAnnotation.type);
		final AnnotationIndex tindex = cas.getAnnotationIndex(Token.type);

		final Iterator<SentenceAnnotation> sit = sindex.iterator();

		while (sit.hasNext()) {
			final Iterator<Token> tit = tindex.subiterator(sit.next());
			while (tit.hasNext()) {
				final Token t = tit.next();
				// The LingPipe tagger incorrectly marks compound dashes,
				// like in mother-daughter as prepositions.
				if (!t.getCoveredText().equals("-")) {
					tlist.add(t);
				}
			}
			final String[] words = new String[tlist.size()];
			for (int ii = 0; ii < words.length; ii++) {
				words[ii] = tlist.get(ii).getCoveredText();
			}
			final String[] tags = tagger.firstBest(words);
			assert true: words.length == tags.length;
			for (int ii = 0; ii < tags.length; ii++) {
				tlist.get(ii).setTag(tags[ii]);
				if (log.isDebugEnabled()) {
					if (!words[ii].equals(tlist.get(ii).getCoveredText())) {
						log.warn("Mismatching word fields: words = " 
								+ words[ii]
								+ "; tlist = " + tlist.get(ii).getCoveredText());
					}
					if (log.isTraceEnabled()) {
						log.trace("Tagging " + words[ii] + " with " + tags[ii] + ".");
					}	
				}
				// Do NOT even THINK ABOUT calling addToIndexes(). The Tokens are already
				// there. We just modified their field 'tag'.
			}
			tlist.clear();
		}
		log.info("Finished tagging.");
	}
}
