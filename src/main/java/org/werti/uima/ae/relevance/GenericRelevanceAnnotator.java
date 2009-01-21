package org.werti.uima.ae.relevance;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.werti.uima.types.annot.HTML;
import org.werti.uima.types.annot.RelevantText;

/**
 * Generic relevance annotator.
 *
 * Looks at the HTML tags of a document and determines what would be suitable input 
 * for the PoS tagger and the input enhancer.
 *
 * Sometimes this will mark garbage. It might prove hard to actually fix this, though.
 *
 * Tha tags currently ignored are stored in the <tt>HTMLAnnotator</tt>'s code.
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public class GenericRelevanceAnnotator extends JCasAnnotator_ImplBase {
	private static final Logger log =
		Logger.getLogger(GenericRelevanceAnnotator.class);

	private static final Pattern whiteSpacePattern = Pattern.compile("^\\s*$");

	/**
	 * Searches for the document body, then goes on and tags everything it deems
	 * relevant as RelevantTag.
	 *
	 * The 'relevance'-notion is a primitive one. We just don't care about stuff that is
	 * enclosed in, say <i>&lt;script&gt;</i> tags. Whether or not a tag is relevant is
	 * determined by the <tt>HTMLAnnotator</tt>. We only look at an html tag's 'relevance'
	 * field here.
	 *
	 * @param cas The document's cas.
	 */
	@SuppressWarnings("unchecked")
	public void process(JCas cas) throws AnalysisEngineProcessException {
		log.info("Starting relevance annotation");
		final Stack<String> tags = new Stack<String>();
		final FSIndex tagIndex = cas.getAnnotationIndex(HTML.type);
		final Iterator<HTML> tit = tagIndex.iterator();
		HTML tag = tit.next();
		if (tag == null) {
			throw new AnalysisEngineProcessException(
					new NullPointerException("No Html tags were found!"));
		}

		// skip ahead to the body
		while (tit.hasNext()){
			tag = tit.next();
			final String tname = tag.getTag_name().toLowerCase();
			if (tname.equals("/head")
			||  tname.equals("body")) {
				tags.push(tname);
				break;
			}
		}
		
		// read in all the natural language you can find
		findreltxt: while (tit.hasNext()) {
			final String tname = tag.getTag_name().toLowerCase();
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
					log.debug("Tag Stack broken. Tried to match " + tname);
				}
			}

			final RelevantText rt = new RelevantText(cas);
			rt.setBegin(tag.getEnd());
			rt.setEnd((tag = tit.next()).getBegin());
			if (whiteSpacePattern.matcher(rt.getCoveredText()).matches()) 
				continue findreltxt;
			rt.setEnclosing_tag(tname);
			rt.addToIndexes();
		}
		log.info("Finished relevance annotation");
	}
}
