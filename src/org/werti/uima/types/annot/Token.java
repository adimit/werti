

/* First created by JCasGen Thu May 29 17:15:33 CEST 2008 */
package org.werti.uima.types.annot;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A relevant Token with PoS information attached.
 * Updated by JCasGen Tue Jun 03 00:06:43 CEST 2008
 * XML source: /home/aleks/src/werti/desc/WERTiTypeSystem.xml
 * @generated */
public class Token extends Annotation {
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
  //* Feature: PoS

  /** getter for PoS - gets The part of speech this tag stands for.
   * @generated */
  public String getPoS() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_PoS == null)
      jcasType.jcas.throwFeatMissing("PoS", "org.werti.uima.types.annot.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_PoS);}
    
  /** setter for PoS - sets The part of speech this tag stands for. 
   * @generated */
  public void setPoS(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_PoS == null)
      jcasType.jcas.throwFeatMissing("PoS", "org.werti.uima.types.annot.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_PoS, v);}    
   
    
  //*--------------*
  //* Feature: word

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

    
