

/* First created by JCasGen Thu Oct 09 14:27:11 CEST 2008 */
package org.werti.uima.types.annot;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** A sentence containing a conditional.
 * Updated by JCasGen Thu Oct 09 14:27:11 CEST 2008
 * XML source: /home/aleks/src/WERTi/desc/WERTiTypeSystem.xml
 * @generated */
public class ConditionalSentence extends SentenceAnnotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(ConditionalSentence.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected ConditionalSentence() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public ConditionalSentence(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public ConditionalSentence(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public ConditionalSentence(JCas jcas, int begin, int end) {
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
  //* Feature: trigger

  /** getter for trigger - gets The token(s) that triggered the markup of this conditional (possibly empty).
   * @generated */
  public FSArray getTrigger() {
    if (ConditionalSentence_Type.featOkTst && ((ConditionalSentence_Type)jcasType).casFeat_trigger == null)
      jcasType.jcas.throwFeatMissing("trigger", "org.werti.uima.types.annot.ConditionalSentence");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_trigger)));}
    
  /** setter for trigger - sets The token(s) that triggered the markup of this conditional (possibly empty). 
   * @generated */
  public void setTrigger(FSArray v) {
    if (ConditionalSentence_Type.featOkTst && ((ConditionalSentence_Type)jcasType).casFeat_trigger == null)
      jcasType.jcas.throwFeatMissing("trigger", "org.werti.uima.types.annot.ConditionalSentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_trigger, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for trigger - gets an indexed value - The token(s) that triggered the markup of this conditional (possibly empty).
   * @generated */
  public Token getTrigger(int i) {
    if (ConditionalSentence_Type.featOkTst && ((ConditionalSentence_Type)jcasType).casFeat_trigger == null)
      jcasType.jcas.throwFeatMissing("trigger", "org.werti.uima.types.annot.ConditionalSentence");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_trigger), i);
    return (Token)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_trigger), i)));}

  /** indexed setter for trigger - sets an indexed value - The token(s) that triggered the markup of this conditional (possibly empty).
   * @generated */
  public void setTrigger(int i, Token v) { 
    if (ConditionalSentence_Type.featOkTst && ((ConditionalSentence_Type)jcasType).casFeat_trigger == null)
      jcasType.jcas.throwFeatMissing("trigger", "org.werti.uima.types.annot.ConditionalSentence");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_trigger), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_trigger), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: condition

  /** getter for condition - gets The verb (cluster) that represents the condition of this conditional.
   * @generated */
  public FSArray getCondition() {
    if (ConditionalSentence_Type.featOkTst && ((ConditionalSentence_Type)jcasType).casFeat_condition == null)
      jcasType.jcas.throwFeatMissing("condition", "org.werti.uima.types.annot.ConditionalSentence");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_condition)));}
    
  /** setter for condition - sets The verb (cluster) that represents the condition of this conditional. 
   * @generated */
  public void setCondition(FSArray v) {
    if (ConditionalSentence_Type.featOkTst && ((ConditionalSentence_Type)jcasType).casFeat_condition == null)
      jcasType.jcas.throwFeatMissing("condition", "org.werti.uima.types.annot.ConditionalSentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_condition, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for condition - gets an indexed value - The verb (cluster) that represents the condition of this conditional.
   * @generated */
  public Token getCondition(int i) {
    if (ConditionalSentence_Type.featOkTst && ((ConditionalSentence_Type)jcasType).casFeat_condition == null)
      jcasType.jcas.throwFeatMissing("condition", "org.werti.uima.types.annot.ConditionalSentence");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_condition), i);
    return (Token)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_condition), i)));}

  /** indexed setter for condition - sets an indexed value - The verb (cluster) that represents the condition of this conditional.
   * @generated */
  public void setCondition(int i, Token v) { 
    if (ConditionalSentence_Type.featOkTst && ((ConditionalSentence_Type)jcasType).casFeat_condition == null)
      jcasType.jcas.throwFeatMissing("condition", "org.werti.uima.types.annot.ConditionalSentence");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_condition), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_condition), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: result

  /** getter for result - gets The verb (cluster) representing the result of this conditional.
   * @generated */
  public FSArray getResult() {
    if (ConditionalSentence_Type.featOkTst && ((ConditionalSentence_Type)jcasType).casFeat_result == null)
      jcasType.jcas.throwFeatMissing("result", "org.werti.uima.types.annot.ConditionalSentence");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_result)));}
    
  /** setter for result - sets The verb (cluster) representing the result of this conditional. 
   * @generated */
  public void setResult(FSArray v) {
    if (ConditionalSentence_Type.featOkTst && ((ConditionalSentence_Type)jcasType).casFeat_result == null)
      jcasType.jcas.throwFeatMissing("result", "org.werti.uima.types.annot.ConditionalSentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_result, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for result - gets an indexed value - The verb (cluster) representing the result of this conditional.
   * @generated */
  public Token getResult(int i) {
    if (ConditionalSentence_Type.featOkTst && ((ConditionalSentence_Type)jcasType).casFeat_result == null)
      jcasType.jcas.throwFeatMissing("result", "org.werti.uima.types.annot.ConditionalSentence");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_result), i);
    return (Token)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_result), i)));}

  /** indexed setter for result - sets an indexed value - The verb (cluster) representing the result of this conditional.
   * @generated */
  public void setResult(int i, Token v) { 
    if (ConditionalSentence_Type.featOkTst && ((ConditionalSentence_Type)jcasType).casFeat_result == null)
      jcasType.jcas.throwFeatMissing("result", "org.werti.uima.types.annot.ConditionalSentence");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_result), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ConditionalSentence_Type)jcasType).casFeatCode_result), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    