package org.werti.uima.enhancer;

import java.util.Iterator;

import org.apache.log4j.Logger;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.Enhancement;

import org.werti.uima.types.annot.Token;
import org.werti.util.EnhancerUtils;

/**
 * An enhancement class that puts WERTi-<tt>&lt;span&gt;</tt>s around <em>all</em>
 * tokens and optionally gives them the attribute 'hit' when they belong to a given
 * PoS.
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */

public class TokenEnhancer extends JCasAnnotator_ImplBase {
	private static final Logger log =
		Logger.getLogger(PoSEnhancer.class);

	/**
	 * Iterate over all tokens and put a span around them. If a token matches one of the give
	 * PoSTags, then go on and mark it up as a hit.
	 *
	 */
	@SuppressWarnings("unchecked")
	public void process(JCas cas) throws AnalysisEngineProcessException {
		int id = 0;
		log.info("Starting enhancement");
		Object o = getContext().getConfigParameterValue("tags");
		final String[] tags;
		if (o instanceof String) {
			tags = ((String) o).split("(\\s*,\\s*)+");
			if (log.isDebugEnabled()) {
				final StringBuilder sb = new StringBuilder();
				for (String s:tags) {
					sb.append(s + " ");
				}
				log.debug("Tags: " + sb.toString());
			}
		} else {
			Object[] args = { "tags" };
			throw new AnalysisEngineProcessException
				(AnalysisEngineProcessException.RESOURCE_DATA_NOT_VALID, args);
		}

		final FSIndex textIndex = cas.getAnnotationIndex(Token.type);
		final Iterator<Token> tit = textIndex.iterator();

		Token t; // token pointer

		while (tit.hasNext()) {
			t = tit.next();
			if (t.getCoveredText().matches("\\w+")) {
				final Enhancement e = new Enhancement(cas);
				e.setBegin(t.getBegin());
				e.setEnd(t.getEnd());

				id++;
				final String hit;

				if (t.getTag() == null) {
					log.debug("Encountered token with NULL tag");
					hit = "0";
				} else if (EnhancerUtils.arrayContains(t.getTag(), tags)) {
					hit = "1";
				} else {
					hit = "0";
				}

				e.setEnhanceStart("<span id=\"" + EnhancerUtils.get_id("WERTi-span",id) 
						+ "\" hit =\"" + hit + "\">");
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

	
}
