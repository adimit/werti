package org.werti.uima.ae;

import org.apache.uima.jcas.JCas;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

// import type
import org.werti.uima.types.annot.HTML;

public class HTMLAnnotator extends JCasAnnotator_ImplBase {

	/**
	 * This method tries to find all html tags in the CAS's document text efficiently.
	 *
	 * Note that currently this does not look out for broken HTML tags. This may be stupid.
	 * We hope web sites don't do broken HTML tags right now. Watch errors closely, as they
	 * may be related to this.
	 *
	 * More sophisticated analysis is on the TODO list.
	 */
	public void process(JCas cas) {
		final String s = cas.getDocumentText();
		int last_index = 0;
		while ((last_index = s.indexOf((int) '<', last_index)) != -1) {
			final HTML tag = new HTML(cas);
			tag.setBegin(last_index);
			final String tname = s.substring(last_index, (last_index = s.indexOf((int) ' ', last_index)));
			tag.setClosing(((tname.charAt(0) == '/')? true : false));
			tag.setTag_name(tname);
			last_index = s.indexOf((int) '>', last_index);
			tag.setEnd(last_index);
			tag.addToIndexes();
		}
	}
}
