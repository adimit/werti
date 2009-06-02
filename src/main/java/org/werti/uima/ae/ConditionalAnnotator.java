package org.werti.uima.ae;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import danbikel.parser.Settings;

import org.apache.log4j.Logger;

import org.apache.uima.UimaContext;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.text.AnnotationIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.jcas.cas.FSArray;

import org.werti.uima.types.annot.ConditionalSentence;
import org.werti.uima.types.annot.SentenceAnnotation;
import org.werti.uima.types.annot.Token;

/**
 * An annotator that looks for certain triggers in {@link
 * org.werti.uima.types.annot.SentenceAnnotation}s and puts {@link
 * org.werti.uima.types.annot.ConditionalSentence}s if it finds them.
 *
 * <p>Note that this is only a primitive pre-processing tool, and only finds
 * 'suspects'. The real conditionals have to be pegged in a parsing step (or
 * something else).</p>
 *
 * <p>The triggers employed are:</p>
 * <ol>
 * <li><em>Single-Word-Triggers</em>
 * <ul>
 * <li>if</li>
 * <li>unless</li>
 * <li>given</li>
 * <li>suppose</li>
 * <li>provided</li>
 * <li>supposing</li>
 * <li>providing</li>
 * </ul>
 * </li>
 * <li><em>Multi-Word-Triggers</em>
 * <ul>
 * <li>so long as</li>
 * <li>in case</li>
 * <li>on condition</li>
 * <li>in the event that</li>
 * </ul>
 * </li>
 * </ol>
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public class ConditionalAnnotator extends JCasAnnotator_ImplBase {
	private static final Logger log =
		Logger.getLogger(ConditionalAnnotator.class);

	private static final String[] triggersa =
	{ "if", "unless", "given", "suppose", "provided", "supposing", "providing" };
	private static final List<String> triggers = new ArrayList<String>(Arrays.asList(triggersa));

	private static final String[] mwtriggersa =
	{ "solongas", "incase", "oncondition", "intheeventthat" };

	public void initialize(UimaContext context) {
		Settings.set(Settings.decoderRelaxConstraintsAfterBeamWidening, "false");
		System.err.println(Settings.get(Settings.decoderRelaxConstraintsAfterBeamWidening));
	}

	@SuppressWarnings("unchecked")
	public void process(JCas cas) {
		final AnnotationIndex sindex = cas.getAnnotationIndex(SentenceAnnotation.type);
		final AnnotationIndex tindex = cas.getAnnotationIndex(Token.type);

		log.debug("Starting conditional detection…");

		sentences: for(final Iterator<SentenceAnnotation> sit = sindex.iterator(); sit.hasNext(); ) {
			final StringBuilder sb = new StringBuilder();
			final SentenceAnnotation sentence = sit.next();
			final ArrayList<Token> buffer = new ArrayList<Token>(5);
			for(final Iterator<Token> tit = tindex.subiterator(sentence); tit.hasNext(); ) {
				final Token t = tit.next();
				final String s = t.getCoveredText().toLowerCase();

				// one word triggers
				if (triggers.contains(s)) {
					final Token[] trigger = { t };
					createAnnotation(trigger, sentence, cas);
					continue sentences;
				}

				// multi-word triggers
				sb.append(s);
				boolean contains = false;
				for (String mwt:mwtriggersa) {
					if (mwt.startsWith(sb.toString())) {
						if (mwt.equals(sb.toString())) {
							buffer.add(t);
							final Token[] ts =
								new Token[buffer.size()];
							createAnnotation(
									buffer.toArray(ts)
									, sentence
									, cas);
							continue sentences;
						} else {
							buffer.add(t);
							contains = true;
						}
					}
				}
				if (!contains) {
					sb.delete(0, sb.length());
					buffer.clear();
				}
			}
		}

		for(ConditionalSentence cs:sentences) {
			cs.addToIndexes();
		}
		log.debug("Finished conditional detection.");
	}

	// buffer to circumvent ConcurrentModificationExceptions
	private static final ArrayList<ConditionalSentence> sentences = new ArrayList<ConditionalSentence>();

	// Helper to create a conditional annotation and add it to the Cas's indexes
	// @param trigger_list An Array of tokens representing the trigger, of which
	//        there may possibly be more than one
	// @param sentence The sentence underlying the conditional
	// @param cas The Cas to put the annotation in
	private static final void createAnnotation(final Token[] trigger_list
			, final SentenceAnnotation sentence
			, final JCas cas) {
		final ConditionalSentence c = new ConditionalSentence(cas);
		c.setBegin(sentence.getBegin());
		c.setEnd(sentence.getEnd());
		final FSArray trigger = new FSArray(cas, trigger_list.length);
		trigger.copyFromArray(trigger_list, 0, 0, trigger_list.length);
		c.setTrigger(trigger);
		if (log.isDebugEnabled()) {
			log.debug("Found suspect conditional:\n"+sentence.getCoveredText());
		}
		sentences.add(c);
	}
}
