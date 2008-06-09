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
	@SuppressWarnings("unchecked")
	public void process(JCas cas) {
		getContext().getLogger().log(Level.INFO,
				"Starting enhancement");
		Object o = getContext().getConfigParameterValue("Tags");
		final String[] tags;
		if (o instanceof String[]) {
			tags = (String[]) o;
			getContext().getLogger().log(Level.INFO, "Tags: ");
			for (String s:tags) {
				getContext().getLogger().log(Level.INFO, s);
			}
		} else {
			throw new RuntimeException("Expected String Array as Paramater. Aborting");
		}

		o = getContext().getConfigParameterValue("enhance");
		final String method;
		if (o instanceof String) {
			method = (String) o;
		} else {
			throw new RuntimeException("Expected String as Paramater. Aborting");
		}

		final FSIndex textIndex = cas.getAnnotationIndex(Token.type);
		final Iterator<Token> tit = textIndex.iterator();

		iteratetokens: while (tit.hasNext()) {
			final Token t = tit.next();
			if (t.getTag() == null) {
				getContext().getLogger().log(Level.WARNING,
						"Encountered token with NULL tag");
				continue iteratetokens;
			}
			if (arrayContains(t.getTag(), tags)) {
				Enhancement e = new Enhancement(cas);
				e.setBegin(t.getBegin());
				e.setEnd(t.getEnd());
				StringArray  csa = new StringArray(cas, 2);
				IntegerArray cia = new IntegerArray(cas, 2);

				csa.set(0, "<span style=\"color:blue;font-weight:bold\">");
				csa.set(1, "</span>");

				cia.set(0, e.getBegin());
				cia.set(1, e.getEnd());
				e.setEnhancement_list(csa);
				e.setIndex_list(cia);
				e.addToIndexes();
			}
		}
		getContext().getLogger().log(Level.INFO,
				"Finished enhancement");
	}
	
	private static boolean arrayContains(String data, String[] sa) {
		for (String s:sa) {
			if (s.equals(data)) return true;
		}
		return false;
	}
}
