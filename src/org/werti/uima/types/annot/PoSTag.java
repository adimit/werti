

/* First created by JCasGen Thu May 22 05:04:52 CEST 2008 */
package org.werti.uima.types.annot;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A relevant PoS-tag.
 * Updated by JCasGen Thu May 22 19:40:05 CEST 2008
 * XML source: /home/aleks/src/werti/desc/WERTiTypeSystem.xml
 * @generated */
public class PoSTag extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(PoSTag.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected PoSTag() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public PoSTag(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public PoSTag(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public PoSTag(JCas jcas, int begin, int end) {
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
    if (PoSTag_Type.featOkTst && ((PoSTag_Type)jcasType).casFeat_PoS == null)
      jcasType.jcas.throwFeatMissing("PoS", "org.werti.uima.types.annot.PoSTag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PoSTag_Type)jcasType).casFeatCode_PoS);}
    
  /** setter for PoS - sets The part of speech this tag stands for. 
   * @generated */
  public void setPoS(String v) {
    if (PoSTag_Type.featOkTst && ((PoSTag_Type)jcasType).casFeat_PoS == null)
      jcasType.jcas.throwFeatMissing("PoS", "org.werti.uima.types.annot.PoSTag");
    jcasType.ll_cas.ll_setStringValue(addr, ((PoSTag_Type)jcasType).casFeatCode_PoS, v);}    
  }

    