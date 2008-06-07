package org.werti.uima.enhancer;

import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.cas.StringArray;

import org.apache.uima.util.Level;

import org.werti.uima.types.Enhancement;

import org.werti.uima.types.annot.Token;


public class PoSEnhancer extends JCasAnnotator_ImplBase {
	private static final String TAG = "NN";

	@SuppressWarnings("unchecked")
	public void process(JCas cas) {
		getContext().getLogger().log(Level.INFO,
				"Starting enhancement");
		final FSIndex textIndex = cas.getAnnotationIndex(Token.type);
		final Iterator<Token> tit = textIndex.iterator();

		iteratetokens: while (tit.hasNext()) {
			final Token t = tit.next();
			if (t.getTag() == null) {
				getContext().getLogger().log(Level.WARNING,
						"Encountered token with NULL tag");
				continue iteratetokens;
			}
			if (t.getTag().equals(TAG)) {
				Enhancement e = new Enhancement(cas);
				e.setBegin(t.getBegin());
				e.setEnd(t.getEnd());
				StringArray  sa = new StringArray(cas, 2);
				IntegerArray ia = new IntegerArray(cas, 2);

				sa.set(0, "<span style=\"color:blue;font-weight:bold\">");
				sa.set(1, "</span>");

				ia.set(0, e.getBegin());
				ia.set(1, e.getEnd());
				e.setEnhancement_list(sa);
				e.setIndex_list(ia);
				e.addToIndexes();
			}
		}
		getContext().getLogger().log(Level.INFO,
				"Finished enhancement");
	}
}
