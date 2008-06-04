

/* First created by JCasGen Tue Jun 03 02:06:02 CEST 2008 */
package org.werti.uima.types.annot;

import edu.stanford.nlp.ling.HasWord;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;

import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A sentence in natural language on the page to be processed.
 * Keep in mind that this Sentence also harbors a edu.stanford.nlp.ling.Sentence, which represents
 * all the tokens in it. It'll be filled with <i>org.werti.uima.types.annot.Token</i>s, which have
 * tags and words.
 *
 * Updated by JCasGen Wed Jun 04 02:46:32 CEST 2008
 * XML source: /home/aleks/src/werti/desc/WERTiTypeSystem.xml
 * @generated */
public class Sentence extends Annotation {
	/** @generated
	 * @ordered 
	 */
	public final static int typeIndexID = JCasRegistry.register(Sentence.class);
	/** @generated
	 * @ordered 
	 */
	public final static int type = typeIndexID;
	/** @generated  */
	public              int getTypeIndexID() {return typeIndexID;}

	/** Never called.  Disable default constructor
	 * @generated */
	protected Sentence() {}

	/** Internal - constructor used by generator 
	 * @generated */
	public Sentence(int addr, TOP_Type type) {
		super(addr, type);
		readObject();
	}

	/** @generated */
	public Sentence(JCas jcas) {
		super(jcas);
		readObject();   
	} 

	/** @generated */  
	public Sentence(JCas jcas, int begin, int end) {
		super(jcas);
		setBegin(begin);
		setEnd(end);
		readObject();
	}   

	/** <!-- begin-user-doc -->
	 * Write your own initialization here
	 * <!-- end-user-doc -->
	 @generated modifiable */
	private void readObject() {}

	//*--------------*
	//* Feature: coherence

	/** getter for coherence - gets The coherence of this sentence. How many html tags interefere?
	 * @generated */
	public float getCoherence() {
		if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_coherence == null)
			jcasType.jcas.throwFeatMissing("coherence", "org.werti.uima.types.annot.Sentence");
		return jcasType.ll_cas.ll_getFloatValue(addr, ((Sentence_Type)jcasType).casFeatCode_coherence);
	}

	/** setter for coherence - sets The coherence of this sentence. How many html tags interefere? 
	 * @generated */
	public void setCoherence(float v) {
		if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_coherence == null)
			jcasType.jcas.throwFeatMissing("coherence", "org.werti.uima.types.annot.Sentence");
		jcasType.ll_cas.ll_setFloatValue(addr, ((Sentence_Type)jcasType).casFeatCode_coherence, v);
	}

	//*--------------*
	//* Feature: sentence
	// WARNING: this is *not* a UIMA RangeType. I can't find a way to turn it into one.
	// Instead, we're just defining our own feature, which is a Stanford Sentence.
	
	private edu.stanford.nlp.ling.Sentence<? extends HasWord> sentence;

	/**
	 * Gets the sentence for this instance.
	 *
	 * @return The sentence.
	 */
	public edu.stanford.nlp.ling.Sentence<? extends HasWord> getSentence () {
		return this.sentence;
	}

	/**
	 * Sets the sentence for this instance.
	 *
	 * @param sentence The sentence.
	 */
	public void setSentence (edu.stanford.nlp.ling.Sentence<? extends HasWord> sentence) {
		this.sentence = sentence;
	}
}


