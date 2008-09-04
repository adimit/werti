
/* First created by JCasGen Thu May 22 05:04:52 CEST 2008 */
package org.werti.uima.types.annot;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** A relevant PoS-tag.
 * Updated by JCasGen Thu May 29 13:37:56 CEST 2008
 * @generated */
public class PoSTag_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (PoSTag_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = PoSTag_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new PoSTag(addr, PoSTag_Type.this);
  			   PoSTag_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new PoSTag(addr, PoSTag_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = PoSTag.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.werti.uima.types.annot.PoSTag");



  /** @generated */
  final Feature casFeat_PoS;
  /** @generated */
  final int     casFeatCode_PoS;
  /** @generated */ 
  public String getPoS(int addr) {
        if (featOkTst && casFeat_PoS == null)
      jcas.throwFeatMissing("PoS", "org.werti.uima.types.annot.PoSTag");
    return ll_cas.ll_getStringValue(addr, casFeatCode_PoS);
  }
  /** @generated */    
  public void setPoS(int addr, String v) {
        if (featOkTst && casFeat_PoS == null)
      jcas.throwFeatMissing("PoS", "org.werti.uima.types.annot.PoSTag");
    ll_cas.ll_setStringValue(addr, casFeatCode_PoS, v);}
    
  
 
  /** @generated */
  final Feature casFeat_word;
  /** @generated */
  final int     casFeatCode_word;
  /** @generated */ 
  public String getWord(int addr) {
        if (featOkTst && casFeat_word == null)
      jcas.throwFeatMissing("word", "org.werti.uima.types.annot.PoSTag");
    return ll_cas.ll_getStringValue(addr, casFeatCode_word);
  }
  /** @generated */    
  public void setWord(int addr, String v) {
        if (featOkTst && casFeat_word == null)
      jcas.throwFeatMissing("word", "org.werti.uima.types.annot.PoSTag");
    ll_cas.ll_setStringValue(addr, casFeatCode_word, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public PoSTag_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_PoS = jcas.getRequiredFeatureDE(casType, "PoS", "uima.cas.String", featOkTst);
    casFeatCode_PoS  = (null == casFeat_PoS) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_PoS).getCode();

 
    casFeat_word = jcas.getRequiredFeatureDE(casType, "word", "uima.cas.String", featOkTst);
    casFeatCode_word  = (null == casFeat_word) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_word).getCode();

  }
}



    