

/* First created by JCasGen Thu May 22 04:15:17 CEST 2008 */
package org.werti.uima.types.annot;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Optional annotation to specify which text to work on.
 * Updated by JCasGen Wed May 28 15:40:28 CEST 2008
 * XML source: /home/aleks/src/werti/desc/HTMLAnnotator.xml
 * @generated */
public class RelevantText extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(RelevantText.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected RelevantText() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public RelevantText(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public RelevantText(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public RelevantText(JCas jcas, int begin, int end) {
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
  //* Feature: enclosing_tag

  /** getter for enclosing_tag - gets The html tag that encloses this text fragment.
   * @generated */
  public String getEnclosing_tag() {
    if (RelevantText_Type.featOkTst && ((RelevantText_Type)jcasType).casFeat_enclosing_tag == null)
      jcasType.jcas.throwFeatMissing("enclosing_tag", "org.werti.uima.types.annot.RelevantText");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RelevantText_Type)jcasType).casFeatCode_enclosing_tag);}
    
  /** setter for enclosing_tag - sets The html tag that encloses this text fragment. 
   * @generated */
  public void setEnclosing_tag(String v) {
    if (RelevantText_Type.featOkTst && ((RelevantText_Type)jcasType).casFeat_enclosing_tag == null)
      jcasType.jcas.throwFeatMissing("enclosing_tag", "org.werti.uima.types.annot.RelevantText");
    jcasType.ll_cas.ll_setStringValue(addr, ((RelevantText_Type)jcasType).casFeatCode_enclosing_tag, v);}    
  }

    