package org.werti.uima.types;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;

import org.apache.uima.jcas.tcas.Annotation;

/**
 * A <i>sentence</i> to be included with a Sentence annotation.
 *
 * Its words feature 
 */
public class SentenceAnnotation extends Annotation {
	private Sentence<? extends HasWord> sentence;

	/**
	 * Constructs a new instance.
	 */
	public SentenceAnnotation () {
		super();
		sentence = null;
	}

	/**
	 * Gets the sentence for this instance.
	 *
	 * @return The sentence.
	 */
	public Sentence<? extends HasWord> getSentence () {
		return this.sentence;
	}

	/**
	 * Sets the sentence for this instance.
	 *
	 * @param sentence The sentence.
	 */
	public void setSentence (Sentence<? extends HasWord> sentence) {
		this.sentence = sentence;
	}

	public String toString() {
		return this.sentence.toString();
	}
}
