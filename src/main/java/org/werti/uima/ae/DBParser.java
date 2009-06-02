package org.werti.uima.ae;

import java.rmi.RemoteException;

import java.util.Iterator;

import danbikel.lisp.Sexp;

import danbikel.parser.Settings;

import org.apache.log4j.Logger;

import org.apache.uima.UimaContext;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.text.AnnotationIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;

import org.werti.uima.types.annot.ConditionalSentence;
import org.werti.uima.types.annot.Token;

import org.werti.util.ParserClient;

public class DBParser extends JCasAnnotator_ImplBase {
	private static final Logger log =
		Logger.getLogger(DBParser.class);

	private static ParserClient parser;

	public void initialize(UimaContext context) throws ResourceInitializationException {
		Settings.set(Settings.decoderRelaxConstraintsAfterBeamWidening, "false");

		System.err.printf("Memory: %s/%s\n"
				, Runtime.getRuntime().freeMemory()/1024/1024
				, Runtime.getRuntime().totalMemory()/1024/1024);

		try {
			parser = new ParserClient();
		} catch (RemoteException re) {
			log.fatal(re);
		}
	}

	@SuppressWarnings("unchecked")
	public void process(JCas cas) {
		final AnnotationIndex sindex = cas.getAnnotationIndex(ConditionalSentence.type);
		final AnnotationIndex tindex = cas.getAnnotationIndex(Token.type);

		for(final Iterator<ConditionalSentence> cit = sindex.iterator(); cit.hasNext(); ) {
			final ConditionalSentence sentence = cit.next();
			final StringBuilder sexp = new StringBuilder();
			sexp.append("(");
			for(final Iterator<Token> tit = tindex.subiterator(sentence); tit.hasNext(); ) {
				sexp.append(" ");
				final Token t = tit.next();
				final String s = t.getCoveredText();
				if (s.matches("\\w*")) { // strip punctuation
					//sexp.append("("+s+" ("+t.getTag()+"))"); // using external tagging
					sexp.append(s); // using parser's internal tagging
				}
				sexp.append(" ");
			}
			sexp.append(")");
			try {
				final String s = sexp.toString();
				log.debug("Parsing Sexp\n"+s);
				final Sexp parsed = parser.parse(Sexp.read((s)).list());
				System.err.println(parsed);
				sentence.setSexp(parsed.toString());
			} catch (Exception e) {
				log.fatal(e);
			}
		}
	}
}
