package org.werti.uima.ae;

// import type

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.jcas.JCas;

import org.apache.uima.util.Level;

import org.werti.uima.types.annot.HTML;

public class HTMLAnnotator extends JCasAnnotator_ImplBase {


	/**
	 * This method tries to find all html tags in the CAS's document text efficiently.
	 *
	 * Note that currently this does not look out for broken HTML tags. This may be stupid.
	 * We hope web sites don't do broken HTML tags right now. Watch errors closely, as they
	 * may be related to this.
	 */

	private static final char[] TAG_NAME_DELMTR = { ' ', '\n', '\r', '>'};

	public void process(JCas cas) {
		final String s = cas.getDocumentText();
		int last_index = 0;
		int temp_index = 0;
		while ((last_index = s.indexOf('<', last_index)) > -1) {
			final HTML tag = new HTML(cas);
			tag.setBegin(last_index);
			tag.setEnd(temp_index = (s.indexOf('>', last_index) + 1));

			final String tname = find_tname(s.substring(last_index, temp_index));

			getContext().getLogger().log(Level.INFO,
					"Looking at tag " + tname);

			last_index = temp_index;

			tag.setClosing(((tname.charAt(0) == '/')? true : false));
			tag.setTag_name((tag.getClosing())
						? tname.substring(1,tname.length())
						: tname);
			tag.addToIndexes();

			if (tag.getTag_name().equals("script") 
			&& !tag.getClosing()) {
				last_index = s.indexOf("</script", last_index) - 1;
			}
		}
	}

	private static String find_tname(String s) {
		int i = 0;
		for (char c:TAG_NAME_DELMTR) {
			i = s.indexOf(c);
			if (i != -1) {
				break;
			}
		}
		if (i == -1) return s.substring(1,s.length()-2);
		else return (s.substring(1,i));
	}
}
