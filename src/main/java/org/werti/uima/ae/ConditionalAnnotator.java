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

	private static final String[] mwtriggersa =
	{ "solongas", "incase", "oncondition", "intheeventthat" };


	@SuppressWarnings("unchecked")
	public void process(JCas cas) {
		final AnnotationIndex sindex = cas.getAnnotationIndex(SentenceAnnotation.type);
		final AnnotationIndex tindex = cas.getAnnotationIndex(Token.type);

		sentences: for(final Iterator<SentenceAnnotation> sit = sindex.iterator(); sit.hasNext(); ) {
			final StringBuilder sb = new StringBuilder();
			final SentenceAnnotation sentence = sit.next();
			for(final Iterator<Token> tit = tindex.subiterator(sentence); tit.hasNext(); ) {
				final Token t = tit.next();
				final String s = t.getCoveredText().toLowerCase();
				if (triggers.contains(s)) {
					createAnnotation(s, sentence, cas);
					continue sentences;
				}
				sb.append(s);
				boolean contains = false;
				for (String mwt:mwtriggersa) {
					if (mwt.startsWith(sb.toString())) {
						if (mwt.equals(sb.toString())) {
							createAnnotation(s, sentence, cas);
							continue sentences;
						} else {
							contains = true;
						}
					}
				}
				if (!contains) {
					sb.delete(0, sb.length());
				}
			}
		}
	}

	private static final void createAnnotation(final String trigger
			, final SentenceAnnotation sentence
			, final JCas cas) {
		final Conditional c = new Conditional(cas);
		c.setBegin(sentence.getBegin());
		c.setEnd(sentence.getEnd());
		c.setTrigger(trigger);
		c.addToIndexes();
	}
}
