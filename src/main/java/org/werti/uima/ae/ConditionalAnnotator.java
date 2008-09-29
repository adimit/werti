package org.werti.uima.ae;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.text.AnnotationIndex;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.annot.Conditional;
import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;

public class ConditionalAnnotator extends JCasAnnotator_ImplBase {
	private static final String[] triggersa =
	{ "if", "unless", "given", "suppose", "provided", "supposing", "providing" };
	private static final List<String> triggers = new ArrayList<String>(Arrays.asList(triggersa));

	@SuppressWarnings("unchecked")
	public void process(JCas cas) {
		final AnnotationIndex sindex = cas.getAnnotationIndex(SentenceAnnotation.type);
		final AnnotationIndex tindex = cas.getAnnotationIndex(Token.type);

		sentences: for(final Iterator<SentenceAnnotation> sit = sindex.iterator(); sit.hasNext(); ) {
			final SentenceAnnotation sentence = sit.next();
			for(final Iterator<Token> tit = tindex.subiterator(sentence); tit.hasNext(); ) {
				final Token t = tit.next();
				final String s = t.getCoveredText();
				if (triggers.contains(s)) {
					final Conditional c = new Conditional(cas);
					c.setBegin(sentence.getBegin());
					c.setEnd(sentence.getEnd());
					c.setTrigger(s);
					c.addToIndexes();
					continue sentences;
				}
			}
		}
	}
}
