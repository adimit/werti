package org.werti.uima.ae;

import java.rmi.RemoteException;

import java.util.Iterator;

import danbikel.lisp.Sexp;

import org.apache.log4j.Logger;

import org.apache.uima.UimaContext;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.text.AnnotationIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;

import org.werti.uima.types.annot.Conditional;
import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;

import org.werti.util.ParserClient;

public class DBParser extends JCasAnnotator_ImplBase {
	private static final Logger log =
		Logger.getLogger(DBParser.class);

	private static ParserClient parser;

	public void initialize(UimaContext context) throws ResourceInitializationException {
		try {
			parser = new ParserClient();
		} catch (RemoteException re) {
			log.fatal(re);
		}
	}

	@SuppressWarnings("unchecked")
	public void process(JCas cas) {
		final AnnotationIndex sindex = cas.getAnnotationIndex(SentenceAnnotation.type);
		final AnnotationIndex tindex = cas.getAnnotationIndex(Token.type);
		final StringBuilder sexp = new StringBuilder();

		for(final Iterator<Conditional> cit = sindex.iterator(); cit.hasNext(); ) {
			final Conditional sentence = cit.next();
			for(final Iterator<Token> tit = tindex.subiterator(sentence); tit.hasNext(); ) {
				final Token t = tit.next();
				sexp.append("(");
				sexp.append(t.getCoveredText());
				sexp.append(" (");
				sexp.append(t.getTag());
				sexp.append("))");
			}
			try {
				final String s = sexp.toString();
				log.debug("Parsing Sexp\n"+s);
				final Sexp parsed = parser.parse(Sexp.read((s)).list());
				if (!doIfAnnotations(sentence.getTrigger(), parsed, cas)) {
					sentence.removeFromIndexes();
				}
			} catch (Exception e) {
				log.fatal(e);
			}
		}
	}

	private boolean doIfAnnotations(String trigger, Sexp sentence, JCas cas) {
		return true;
	}

}
