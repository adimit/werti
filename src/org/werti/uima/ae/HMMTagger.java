package org.werti.uima.ae;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.UimaContext;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

import org.apache.uima.analysis_engine.annotator.AnnotatorConfigurationException;

import org.apache.uima.cas.text.AnnotationIndex;

import org.apache.uima.examples.tagger.Tagger;
import org.apache.uima.examples.tagger.Viterbi;

import org.apache.uima.examples.tagger.trainAndTest.ModelGeneration;

import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;

import org.apache.uima.util.Level;

import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;

public class HMMTagger extends JCasAnnotator_ImplBase implements Tagger {

	/*
	 * Parameter Definitions
	 */
	// n-gram model
	private static final String pN = "NGRAM_SIZE";
	private int N;

	// model generation
	private static final String pModel = "MODEL_LOCATION";
	private ModelGeneration model;

	/**
	 * Initializes the HMMTagger with a model supplied by the configuration-resource
	 * stored in pModel.
	 *
	 * @param context The context this AE has to initialize in.
	 */
	public void initialize(final UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		try {
			this.N = (Integer) context.getConfigParameterValue(pN);
			this.model = get_model(context);
		} catch (AnnotatorConfigurationException ace) {
			throw new ResourceInitializationException(ace);
		} catch (Exception e) {
			throw new ResourceInitializationException(e);
		}
	}

	// initialize the model from context
	private static ModelGeneration get_model(final UimaContext context) 
		throws AnnotatorConfigurationException {
		return get_model(context, null);
	}

	// initialize the model
	private static ModelGeneration get_model(final UimaContext context, final String loc)
		throws AnnotatorConfigurationException {
		final InputStream model;
		try {
			if (loc == null || loc.equals("")) {
				// fetch model from Annotator configuration
				final String resource = (String) context.getConfigParameterValue(pModel);
				model = ClassLoader.getSystemResourceAsStream(resource);
			} else {
				model = new FileInputStream(loc);
			}
			final ObjectInputStream ois = new ObjectInputStream(model);
			final ModelGeneration oRead = (ModelGeneration) ois.readObject();
			return oRead;
		} catch (IOException ioe) {
			context.getLogger().log(Level.SEVERE,
					"Input Output Error");
			throw new AnnotatorConfigurationException(ioe);
		} catch (ClassNotFoundException cnfe) {
			context.getLogger().log(Level.SEVERE,
					"Couldn't find required class");
			throw new AnnotatorConfigurationException(cnfe);
		} catch (Exception e) {
			context.getLogger().log(Level.SEVERE,
					"Unknown problem occured.");
			throw new AnnotatorConfigurationException(e);
		}
	}

	/**
	 * Processes all sentence annotations; iterates over their Tokens
	 * and tags the Tokens with what the Viterbi algorithm says it should
	 * tag them.
	 *
	 * @param cas The cas for this AE
	 */
	@SuppressWarnings("unchecked")
	public void process(final JCas cas) throws AnalysisEngineProcessException {
		final List<Token> tlist = new ArrayList<Token>();
		List<String> wlist = new ArrayList<String>();

		final AnnotationIndex sindex = cas.getAnnotationIndex(SentenceAnnotation.type);
		final AnnotationIndex tindex = cas.getAnnotationIndex(Token.type);

		final Iterator<SentenceAnnotation> sit = sindex.iterator();

		while (sit.hasNext()) {
			final SentenceAnnotation s = sit.next();

			tlist.clear();
			wlist.clear();

			final Iterator<Token> tit = tindex.subiterator(s);

			while (tit.hasNext()) {
				final Token t = tit.next();
				tlist.add(t);
				wlist.add(t.getCoveredText());
			}

			wlist = viterbi(this.N, this.model, wlist);

			assert true: wlist.size() == tlist.size();

			for (int i = 0; i < tlist.size(); i++) {
				final Token t = tlist.get(i);
				final String tag = wlist.get(i);
				t.setTag(tag);
			}
		}
	}

	// A wrapper for the Viterbi algorithm, so it doesn't uglify the processing code.
	@SuppressWarnings("unchecked")
	private static List<String> viterbi(final int N, final ModelGeneration model, final List<String> list) {
		return Viterbi.process(N, list
				, model.suffix_tree
				, model.suffix_tree_capitalized
				, model.transition_probs
				, model.word_probs
				, model.lambdas2
				, model.lambdas3
				, model.theta);
	}
}
