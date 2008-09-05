package org.werti.uima.ae;

import java.util.Iterator;

import com.aliasi.sentences.IndoEuropeanSentenceModel;
import com.aliasi.sentences.SentenceModel;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.annot.Token;

/**
 * This class is still not working. Do not use!
 *
 * This will ultimately supersede <tt>SentenceBoundaryDetector</tt>
 *
 * @author Aleksandar Dimitrov
 * @version 0
 */
public class LingPipeChunker extends JCasAnnotator_ImplBase {
	private static final SentenceModel sModel =
		new IndoEuropeanSentenceModel();

	public void process(JCas cas) {
		final FSIndex tokenIndex = cas.getAnnotationIndex(Token.type);
		final Iterator<Token> tit = tokenIndex.iterator();

		while (tit.hasNext()) {
			// TODO: This can't be written without breaking the underlying
			// lingpipe API (or severely crippling performance and program semantics...)
		}
	}
}
