

/* First created by JCasGen Wed Sep 17 17:47:41 CEST 2008 */
package org.werti.uima.types.annot;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A sentence in natural language on the page to be processed.
 * Updated by JCasGen Wed Sep 17 17:47:41 CEST 2008
 * XML source: /home/aleks/src/WERTi/desc/enhancers/TokenEnhancer.xml
 * @generated */
public class SentenceAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(SentenceAnnotation.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected SentenceAnnotation() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public SentenceAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public SentenceAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public SentenceAnnotation(JCas jcas, int begin, int end) {
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
  public double getCoherence() {
    if (SentenceAnnotation_Type.featOkTst && ((SentenceAnnotation_Type)jcasType).casFeat_coherence == null)
      jcasType.jcas.throwFeatMissing("coherence", "org.werti.uima.types.annot.SentenceAnnotation");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((SentenceAnnotation_Type)jcasType).casFeatCode_coherence);}
    
  /** setter for coherence - sets The coherence of this sentence. How many html tags interefere? 
   * @generated */
  public void setCoherence(double v) {
    if (SentenceAnnotation_Type.featOkTst && ((SentenceAnnotation_Type)jcasType).casFeat_coherence == null)
      jcasType.jcas.throwFeatMissing("coherence", "org.werti.uima.types.annot.SentenceAnnotation");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((SentenceAnnotation_Type)jcasType).casFeatCode_coherence, v);}    
  }

    