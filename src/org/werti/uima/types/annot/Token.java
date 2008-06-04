

/* First created by JCasGen Tue Jun 03 02:06:02 CEST 2008 */
package org.werti.uima.types.annot;

import edu.stanford.nlp.ling.HasTag;
import edu.stanford.nlp.ling.HasWord;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;

import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A relevant Token with PoS information attached.
 * Updated by JCasGen Wed Jun 04 16:54:22 CEST 2008
 * XML source: /home/aleks/src/werti/desc/WERTiTypeSystem.xml
 * @generated */
public class Token extends Annotation implements HasWord, HasTag {
	/** @generated
	 * @ordered 
	 */
	public final static int typeIndexID = JCasRegistry.register(Token.class);
	/** @generated
	 * @ordered 
	 */
	public final static int type = typeIndexID;
	/** @generated  */
	public              int getTypeIndexID() {return typeIndexID;}
 
	/** Never called.  Disable default constructor
	 * @generated */
	protected Token() {}
    
	/** Internal - constructor used by generator 
	 * @generated */
	public Token(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
	/** @generated */
	public Token(JCas jcas) {
    super(jcas);
    readObject();   
  } 

	/** @generated */  
	public Token(JCas jcas, int begin, int end) {
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
  //* Feature: Tag

	/** getter for Tag - gets The part of speech this tag stands for.
	 * @generated */
	public String getTag() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_tag == null)
      jcasType.jcas.throwFeatMissing("tag", "org.werti.uima.types.annot.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_tag);}
    
	/**
	 * Stanford conformant getter for tag.
	 *
	 * Only delays it to getTag();
	 *
	 * @return the Tag as a String
	 */
	public String tag() {
		return getTag();
	}
    
	/** setter for Tag - sets The part of speech this tag stands for. 
	 * @generated */
	public void setTag(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_tag == null)
      jcasType.jcas.throwFeatMissing("tag", "org.werti.uima.types.annot.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_tag, v);}    
   
    
  //*--------------*
  //* Feature: word

	/**
	 * Stanford conformant getter for word.
	 *
	 * Returns what getWord() would return.
	 *
	 * @return The word feature for this token as String
	 */
	public String word() {
		return getWord();
	}
    /** getter for word - gets The word we're talking about 

    // FOR DEBUGGING PURPOSES ONLY
     * @generated */
    public String getWord() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_word == null)
      jcasType.jcas.throwFeatMissing("word", "org.werti.uima.types.annot.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_word);}
    
    /** setter for word - sets The word we're talking about 

    // FOR DEBUGGING PURPOSES ONLY 
     * @generated */
    public void setWord(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_word == null)
      jcasType.jcas.throwFeatMissing("word", "org.werti.uima.types.annot.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_word, v);}    
  }
