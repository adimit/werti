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

	private static final char[] TAG_NAME_DELMTR = { ' ', '\t', '\n', '\r', '>'};

	/**
	 * Mark up all html tags and all their properties.
	 *
	 * Properties include 'closing', 'irrelevant' and the 'tag_name'. Also their absolute
	 * position in the CAS.
	 */
	public void process(JCas cas) {
		final String s = cas.getDocumentText();
		int tstart = 0;
		int tend = 0;
		while ((tstart = s.indexOf('<', tstart)) > -1) {
			final HTML tag = new HTML(cas);
			tag.setBegin(tstart);
			tag.setEnd(tend = (s.indexOf('>', tstart) + 1));

			getContext().getLogger().log(Level.INFO,
					"tstart = " + tstart + "; tend = " + tend);

			final String tname = find_tname(s.substring(tstart, tend));

			getContext().getLogger().log(Level.INFO,
					"Looking at tag " + tname);

			tstart = tend;

			// set Closing and tag_name
			if (tname.charAt(0) == '/') {
				tag.setClosing(true);
				tag.setTag_name(tname.substring(1,tname.length()));
			} else {
				if (tname.endsWith("--")) {
					tag.setClosing(true);
				}
				tag.setTag_name(tname);
			}

			tstart = skip(tag, tstart, s);

			tag.addToIndexes();

		}
	}

	// skips ahead and marks irrelevant tags
	private int skip(HTML tag, int index, String s) {
		if (!tag.getClosing()) {
			// Regular tags
			if (tag.getTag_name().equals("script")) {
				tag.setIrrelevant(true);
				return s.indexOf("</script", index) - 1;
			}
			if (tag.getTag_name().equals("style")) {
				tag.setIrrelevant(true);
				return s.indexOf("</style", index) - 1;
			}
			// Comment tags and functional commented tags
			if (tag.getTag_name().equals("!--[if")) {
				tag.setIrrelevant(true);
				return s.indexOf("<![endif", index) - 1;
			}
		}
		return index;
	}

	// finds the tags name and returns it.
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