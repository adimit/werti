package org.werti.uima.ae.relevance;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.util.Level;

import org.werti.uima.types.annot.HTML;
import org.werti.uima.types.annot.RelevantText;

/**
 * Generic Relevance annotator.
 *
 * Looks at the HTML tags of a document and determines what would be suitable input 
 * for the PoS tagger and the input enhancer.
 *
 * Sometimes this will mark garbage. It might prove hard to actually fix this, though.
 */
public class GenericRelevanceAnnotator extends JCasAnnotator_ImplBase {

	private static final int RELEVANCE_THRESHOLD = 1;

	/**
	 * Searches for the document body, then goes on and tags everything it deems
	 * relevant as RelevantTag.
	 *
	 * The 'relevance'-notion is a primitive one. We just don't care about stuff that is
	 * enclosed in, say <i>&lt;script&gt;</i> tags.
	 */
	public void process(JCas cas) {
		getContext().getLogger().log(Level.INFO,
				"Starting relevance annotation");
		final Stack<String> tags = new Stack<String>();
		final FSIndex tagIndex = cas.getAnnotationIndex(HTML.type);
		final Iterator<HTML> tit = tagIndex.iterator();
		HTML tag = tit.next();
		if (tag == null) {
			getContext().getLogger().log(Level.SEVERE, 
					"HTML tag was null while starting to search for relevance.");
		}

		// skip ahead to the body
		while (tit.hasNext()){
			tag = tit.next();
			final String tname = tag.getTag_name();
			if (tname.equals("/head")
			||  tname.equals("body")) {
				tags.push(tname);
				break;
			}
		}
		
		// read in all the natural language you can find
		findreltxt: while (tit.hasNext()) {
			final String tname = tag.getTag_name();
			tags.push(tname);

			// weed out tags we don't need
			if (tag.getIrrelevant()) {
				tag = tit.next();
				continue findreltxt;
			}

			// if it's a closing tag, we reduce the tag stack to find the matching opener
			if (tag.getClosing()) {
				try {
					while (tags.pop().equals(tname)); // read: until not...
				} catch (EmptyStackException ese) {
					getContext().getLogger().log(Level.FINE,
							"Tag Stack broken. Tried to match '"
							+ tname + "';");
				}
			}

			final RelevantText rt = new RelevantText(cas);
			rt.setBegin(tag.getEnd());
			rt.setEnd((tag = tit.next()).getBegin());
			if ((rt.getEnd() - rt.getBegin()) < RELEVANCE_THRESHOLD) continue findreltxt;
			rt.setEnclosing_tag(tname);
			rt.addToIndexes();
		}
		getContext().getLogger().log(Level.INFO,
				"Finished relevance annotation");
	}
}
