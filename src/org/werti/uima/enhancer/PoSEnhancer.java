package org.werti.uima.enhancer;

import java.util.Iterator;

import org.apache.log4j.Logger;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.Enhancement;

import org.werti.uima.types.annot.Token;


public class PoSEnhancer extends JCasAnnotator_ImplBase {
	private static final Logger log =
		Logger.getLogger(PoSEnhancer.class);

	@SuppressWarnings("unchecked")
	public void process(JCas cas) throws AnalysisEngineProcessException {
		int id = 0;
		log.info("Starting enhancement");
		Object o = getContext().getConfigParameterValue("Tags");
		final String[] tags;
		if (o instanceof String[]) {
			tags = (String[]) o;
			if (log.isDebugEnabled()) {
				final StringBuilder sb = new StringBuilder();
				for (String s:tags) {
					sb.append(s + " ");
				}
				log.debug("Tags: " + sb.toString());
			}
		} else {
			Object[] args = { "Tags" };
			throw new AnalysisEngineProcessException
				(AnalysisEngineProcessException.RESOURCE_DATA_NOT_VALID, args);
		}

		final FSIndex textIndex = cas.getAnnotationIndex(Token.type);
		final Iterator<Token> tit = textIndex.iterator();

		log.debug("Feature Structure index size: " + textIndex.size());

		Token t; // token pointer

		iteratetokens: while (tit.hasNext()) {
			t = tit.next();
			if (t.getTag() == null) {
				log.debug("Encountered token with NULL tag");
				continue iteratetokens;
			}
			if (arrayContains(t.getTag(), tags)) {
				final Enhancement e = new Enhancement(cas);
				e.setBegin(t.getBegin());
				e.setEnd(t.getEnd());

				id++;
				e.setEnhanceStart("<span id=\"" + get_id(id) + "\">");
				e.setEnhanceEnd("</span>");

				if (log.isTraceEnabled()) {
					log.trace("Enhanced " + t.getCoveredText()
							+ " with tag "
							+ t.getTag()
							+ " with id "
							+ id);
				}
				e.addToIndexes();
			}
		}
		log.info("Finished enhancement");
	}

	// need thos two to supply JS-annotations with IDs.
	private static String get_id(int id) {
		return "WERTi-span-" + id;
	}

	// does an array of Strings contain a given String?
	private static boolean arrayContains(String data, String[] sa) {
		for (String s:sa) {
			if (s.equals(data)) return true;
		}
		return false;
	}
}
