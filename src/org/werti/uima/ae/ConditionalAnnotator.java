package org.werti.uima.ae;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

import org.apache.uima.cas.text.AnnotationIndex;

import org.apache.uima.examples.tagger.Tagger;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;

public class ConditionalAnnotator extends JCasAnnotator_ImplBase implements Tagger {
	private static final Logger log =
		Logger.getLogger(HMMTagger.class);

	@SuppressWarnings("unchecked")
	public void process(final JCas cas) throws AnalysisEngineProcessException {
		log.info("Starting tagging process...");

		final AnnotationIndex sindex = cas.getAnnotationIndex(SentenceAnnotation.type);
		final AnnotationIndex tindex = cas.getAnnotationIndex(Token.type);

		final Iterator<SentenceAnnotation> sit = sindex.iterator();

		final Set<String> sentences = new TreeSet();

		while (sit.hasNext()) {
			final SentenceAnnotation s = sit.next();
			final Iterator<Token> tit = tindex.subiterator(s);

			while (tit.hasNext()) {
				final String token = tit.next().getCoveredText();

				if (token.equals("as") && tit.next().getCoveredText().equals("if")) continue; // no as if
				if (token.equals("if")) {
					sentences.add(s.getCoveredText());
				}
			}
		}

		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter("/home/aleks/etc/ifs"));
			for (Iterator<String> ii = sentences.iterator(); ii.hasNext();){
				final String span = ii.next().replaceAll("\\s+", " ") + "\n";
				out.write(span);
			}
		} catch (IOException ioe) {
			log.fatal("Couldn't open file", ioe);
		}
		log.info("Finished tagging process");
	}
}
