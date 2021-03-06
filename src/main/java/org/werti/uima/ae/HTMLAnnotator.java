package org.werti.uima.ae;

import java.util.Set;
import java.util.TreeSet;

// import type

import org.apache.log4j.Logger;

import org.apache.uima.UimaContext;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.annot.HTML;

/**
 * Mark up all html tags and all their properties.
 *
 * Properties include 'closing', 'irrelevant' and the 'tag_name'. Also their absolute
 * position in the CAS.
 *
 * @author Aleksandar Dimitrov
 * @version 1.0
 */

public class HTMLAnnotator extends JCasAnnotator_ImplBase {
	private static final Logger log =
		Logger.getLogger(HTMLAnnotator.class);


	private static final char[] TAG_NAME_DELMTR = { ' ', '\t', '\n', '\r', '>'};

	private static final Set<String> ignoredTags = new TreeSet<String>();

	public void initialize(UimaContext context) {
		// this should actually be placed in a .properties file...
		ignoredTags.add("script");
		ignoredTags.add("style");
		ignoredTags.add("a");
	}

	/**
	 * Mark up all html tags and all their properties.
	 *
	 * Properties include 'closing', 'irrelevant' and the 'tag_name'. Also their absolute
	 * position in the CAS.
	 */
	public void process(JCas cas) {
		log.info("Starting HTML tag recognition");
		final String s = cas.getDocumentText();
		int tstart = 0;
		int tend = 0;
		while ((tstart = s.indexOf('<', tstart)) > -1) {
			final HTML tag = new HTML(cas);
			tag.setBegin(tstart);
			tag.setEnd(tend = (s.indexOf('>', tstart) + 1));

			if (log.isTraceEnabled()) {
				log.trace("tstart = " + tstart + "; tend = " + tend);
			}

			final String tname = find_tname(s.substring(tstart, tend));

			if (log.isTraceEnabled()) {
				log.trace("Looking at tag " + tname);
			}

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
		log.info("Finished HTML tag recognition");
	}

	// skips and marks irrelevant tags
	private int skip(HTML tag, int index, String s) {
		if (!tag.getClosing()) {
			// Regular tags
			if (ignoredTags.contains(tag.getTag_name())) {
				tag.setIrrelevant(true);
				return s.indexOf("</"+tag.getTag_name(), index) - 1;
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
