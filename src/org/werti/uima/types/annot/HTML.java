

/* First created by JCasGen Thu May 22 04:15:17 CEST 2008 */
package org.werti.uima.types.annot;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Annotation spanning an HTML tag.
 * Updated by JCasGen Wed Jun 04 02:46:32 CEST 2008
 * XML source: /home/aleks/src/werti/desc/WERTiTypeSystem.xml
 * @generated */
public class HTML extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(HTML.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected HTML() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public HTML(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public HTML(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public HTML(JCas jcas, int begin, int end) {
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
  //* Feature: tag_name

  /** getter for tag_name - gets The name of the HTML tag.
   * @generated */
  public String getTag_name() {
    if (HTML_Type.featOkTst && ((HTML_Type)jcasType).casFeat_tag_name == null)
      jcasType.jcas.throwFeatMissing("tag_name", "org.werti.uima.types.annot.HTML");
    return jcasType.ll_cas.ll_getStringValue(addr, ((HTML_Type)jcasType).casFeatCode_tag_name);}
    
  /** setter for tag_name - sets The name of the HTML tag. 
   * @generated */
  public void setTag_name(String v) {
    if (HTML_Type.featOkTst && ((HTML_Type)jcasType).casFeat_tag_name == null)
      jcasType.jcas.throwFeatMissing("tag_name", "org.werti.uima.types.annot.HTML");
    jcasType.ll_cas.ll_setStringValue(addr, ((HTML_Type)jcasType).casFeatCode_tag_name, v);}    
   
    
  //*--------------*
  //* Feature: closing

  /** getter for closing - gets Is this tag closing?
   * @generated */
  public boolean getClosing() {
    if (HTML_Type.featOkTst && ((HTML_Type)jcasType).casFeat_closing == null)
      jcasType.jcas.throwFeatMissing("closing", "org.werti.uima.types.annot.HTML");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((HTML_Type)jcasType).casFeatCode_closing);}
    
  /** setter for closing - sets Is this tag closing? 
   * @generated */
  public void setClosing(boolean v) {
    if (HTML_Type.featOkTst && ((HTML_Type)jcasType).casFeat_closing == null)
      jcasType.jcas.throwFeatMissing("closing", "org.werti.uima.types.annot.HTML");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((HTML_Type)jcasType).casFeatCode_closing, v);}    
   
    
  //*--------------*
  //* Feature: irrelevant

  /** getter for irrelevant - gets Is this a tag irrelevant to the interpreter? (like script, functional comment tags...)
   * @generated */
  public boolean getIrrelevant() {
    if (HTML_Type.featOkTst && ((HTML_Type)jcasType).casFeat_irrelevant == null)
      jcasType.jcas.throwFeatMissing("irrelevant", "org.werti.uima.types.annot.HTML");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((HTML_Type)jcasType).casFeatCode_irrelevant);}
    
  /** setter for irrelevant - sets Is this a tag irrelevant to the interpreter? (like script, functional comment tags...) 
   * @generated */
  public void setIrrelevant(boolean v) {
    if (HTML_Type.featOkTst && ((HTML_Type)jcasType).casFeat_irrelevant == null)
      jcasType.jcas.throwFeatMissing("irrelevant", "org.werti.uima.types.annot.HTML");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((HTML_Type)jcasType).casFeatCode_irrelevant, v);}    
  }

    
