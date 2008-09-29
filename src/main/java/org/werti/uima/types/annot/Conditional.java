

/* First created by JCasGen Sat Sep 27 13:39:42 CEST 2008 */
package org.werti.uima.types.annot;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A potential conditional to be evaluated.
 * Updated by JCasGen Sat Sep 27 13:39:42 CEST 2008
 * XML source: /home/aleks/src/WERTi/desc/WERTiTypeSystem.xml
 * @generated */
public class Conditional extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Conditional.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Conditional() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Conditional(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Conditional(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Conditional(JCas jcas, int begin, int end) {
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

  /** getter for trigger - gets The trigger for this conditional to be included.
   * @generated */
  public String getTrigger() {
    if (Conditional_Type.featOkTst && ((Conditional_Type)jcasType).casFeat_trigger == null)
      jcasType.jcas.throwFeatMissing("trigger", "org.werti.uima.types.annot.Conditional");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Conditional_Type)jcasType).casFeatCode_trigger);}
    
  /** setter for trigger - sets The trigger for this conditional to be included. 
   * @generated */
  public void setTrigger(String v) {
    if (Conditional_Type.featOkTst && ((Conditional_Type)jcasType).casFeat_trigger == null)
      jcasType.jcas.throwFeatMissing("trigger", "org.werti.uima.types.annot.Conditional");
    jcasType.ll_cas.ll_setStringValue(addr, ((Conditional_Type)jcasType).casFeatCode_trigger, v);}    
  }

    