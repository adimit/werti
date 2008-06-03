

/* First created by JCasGen Tue Jun 03 02:06:02 CEST 2008 */
package org.werti.uima.types.annot;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A sentence in natural language on the page to be processed.
 * Updated by JCasGen Tue Jun 03 02:36:21 CEST 2008
 * XML source: /home/aleks/src/werti/desc/operators/TestDescriptor.xml
 * @generated */
public class Sentence extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Sentence.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Sentence() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Sentence(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Sentence(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Sentence(JCas jcas, int begin, int end) {
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
     
}

    