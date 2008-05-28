

/* First created by JCasGen Wed May 28 02:55:53 CEST 2008 */
package org.werti.uima.annot;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Path in a html file
 * Updated by JCasGen Wed May 28 02:57:22 CEST 2008
 * XML source: /home/aleks/src/werti/desc/PoSTagger.xml
 * @generated */
public class Path extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Path.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Path() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Path(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Path(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Path(JCas jcas, int begin, int end) {
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
  //* Feature: localized

  /** getter for localized - gets Localized version of this path
   * @generated */
  public String getLocalized() {
    if (Path_Type.featOkTst && ((Path_Type)jcasType).casFeat_localized == null)
      jcasType.jcas.throwFeatMissing("localized", "org.werti.uima.annot.Path");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Path_Type)jcasType).casFeatCode_localized);}
    
  /** setter for localized - sets Localized version of this path 
   * @generated */
  public void setLocalized(String v) {
    if (Path_Type.featOkTst && ((Path_Type)jcasType).casFeat_localized == null)
      jcasType.jcas.throwFeatMissing("localized", "org.werti.uima.annot.Path");
    jcasType.ll_cas.ll_setStringValue(addr, ((Path_Type)jcasType).casFeatCode_localized, v);}    
  }

    