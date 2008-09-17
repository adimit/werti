

/* First created by JCasGen Wed Jun 04 02:43:38 CEST 2008 */
package org.werti.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Describes an enhancment on the current spot.
 * Updated by JCasGen Thu Sep 18 00:51:17 CEST 2008
 * XML source: /home/aleks/src/WERTi/desc/WERTiTypeSystem.xml
 * @generated */
public class Enhancement extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Enhancement.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Enhancement() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Enhancement(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Enhancement(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Enhancement(JCas jcas, int begin, int end) {
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
  //* Feature: EnhanceStart

  /** getter for EnhanceStart - gets The start tag of the enhancement annotation.
   * @generated */
  public String getEnhanceStart() {
    if (Enhancement_Type.featOkTst && ((Enhancement_Type)jcasType).casFeat_EnhanceStart == null)
      jcasType.jcas.throwFeatMissing("EnhanceStart", "org.werti.uima.types.Enhancement");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Enhancement_Type)jcasType).casFeatCode_EnhanceStart);}
    
  /** setter for EnhanceStart - sets The start tag of the enhancement annotation. 
   * @generated */
  public void setEnhanceStart(String v) {
    if (Enhancement_Type.featOkTst && ((Enhancement_Type)jcasType).casFeat_EnhanceStart == null)
      jcasType.jcas.throwFeatMissing("EnhanceStart", "org.werti.uima.types.Enhancement");
    jcasType.ll_cas.ll_setStringValue(addr, ((Enhancement_Type)jcasType).casFeatCode_EnhanceStart, v);}    
   
    
  //*--------------*
  //* Feature: EnhanceEnd

  /** getter for EnhanceEnd - gets The end tag of the enhancement annotation.
   * @generated */
  public String getEnhanceEnd() {
    if (Enhancement_Type.featOkTst && ((Enhancement_Type)jcasType).casFeat_EnhanceEnd == null)
      jcasType.jcas.throwFeatMissing("EnhanceEnd", "org.werti.uima.types.Enhancement");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Enhancement_Type)jcasType).casFeatCode_EnhanceEnd);}
    
  /** setter for EnhanceEnd - sets The end tag of the enhancement annotation. 
   * @generated */
  public void setEnhanceEnd(String v) {
    if (Enhancement_Type.featOkTst && ((Enhancement_Type)jcasType).casFeat_EnhanceEnd == null)
      jcasType.jcas.throwFeatMissing("EnhanceEnd", "org.werti.uima.types.Enhancement");
    jcasType.ll_cas.ll_setStringValue(addr, ((Enhancement_Type)jcasType).casFeatCode_EnhanceEnd, v);}    
  }

    
