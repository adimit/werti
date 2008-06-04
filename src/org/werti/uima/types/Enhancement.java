

/* First created by JCasGen Wed Jun 04 02:43:38 CEST 2008 */
package org.werti.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.cas.IntegerList;
import org.apache.uima.jcas.cas.StringArray;


/** Describes an enhancment on the current spot.
 * Updated by JCasGen Wed Jun 04 16:54:22 CEST 2008
 * XML source: /home/aleks/src/werti/desc/WERTiTypeSystem.xml
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
  //* Feature: enhancement_list

  /** getter for enhancement_list - gets A list of enhancements this Enhancement stands for.
   * @generated */
  public StringArray getEnhancement_list() {
    if (Enhancement_Type.featOkTst && ((Enhancement_Type)jcasType).casFeat_enhancement_list == null)
      jcasType.jcas.throwFeatMissing("enhancement_list", "org.werti.uima.types.Enhancement");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Enhancement_Type)jcasType).casFeatCode_enhancement_list)));}
    
  /** setter for enhancement_list - sets A list of enhancements this Enhancement stands for. 
   * @generated */
  public void setEnhancement_list(StringArray v) {
    if (Enhancement_Type.featOkTst && ((Enhancement_Type)jcasType).casFeat_enhancement_list == null)
      jcasType.jcas.throwFeatMissing("enhancement_list", "org.werti.uima.types.Enhancement");
    jcasType.ll_cas.ll_setRefValue(addr, ((Enhancement_Type)jcasType).casFeatCode_enhancement_list, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for enhancement_list - gets an indexed value - A list of enhancements this Enhancement stands for.
   * @generated */
  public String getEnhancement_list(int i) {
    if (Enhancement_Type.featOkTst && ((Enhancement_Type)jcasType).casFeat_enhancement_list == null)
      jcasType.jcas.throwFeatMissing("enhancement_list", "org.werti.uima.types.Enhancement");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Enhancement_Type)jcasType).casFeatCode_enhancement_list), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Enhancement_Type)jcasType).casFeatCode_enhancement_list), i);}

  /** indexed setter for enhancement_list - sets an indexed value - A list of enhancements this Enhancement stands for.
   * @generated */
  public void setEnhancement_list(int i, String v) { 
    if (Enhancement_Type.featOkTst && ((Enhancement_Type)jcasType).casFeat_enhancement_list == null)
      jcasType.jcas.throwFeatMissing("enhancement_list", "org.werti.uima.types.Enhancement");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Enhancement_Type)jcasType).casFeatCode_enhancement_list), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Enhancement_Type)jcasType).casFeatCode_enhancement_list), i, v);}
   
    
  //*--------------*
  //* Feature: index_list

  /** getter for index_list - gets A list of indices where the elements of enhancement_list should be applied. Please note that your Enhancement must guarantee this.size() == enhancement_list.size()
   * @generated */
  public IntegerList getIndex_list() {
    if (Enhancement_Type.featOkTst && ((Enhancement_Type)jcasType).casFeat_index_list == null)
      jcasType.jcas.throwFeatMissing("index_list", "org.werti.uima.types.Enhancement");
    return (IntegerList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Enhancement_Type)jcasType).casFeatCode_index_list)));}
    
  /** setter for index_list - sets A list of indices where the elements of enhancement_list should be applied. Please note that your Enhancement must guarantee this.size() == enhancement_list.size() 
   * @generated */
  public void setIndex_list(IntegerList v) {
    if (Enhancement_Type.featOkTst && ((Enhancement_Type)jcasType).casFeat_index_list == null)
      jcasType.jcas.throwFeatMissing("index_list", "org.werti.uima.types.Enhancement");
    jcasType.ll_cas.ll_setRefValue(addr, ((Enhancement_Type)jcasType).casFeatCode_index_list, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    