
/* First created by JCasGen Thu Oct 02 12:52:02 CEST 2008 */
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

/** A relevant Token with PoS information attached.
 * Updated by JCasGen Thu Oct 02 12:52:02 CEST 2008
 * @generated */
public class Token_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Token_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Token_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Token(addr, Token_Type.this);
  			   Token_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Token(addr, Token_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = Token.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.werti.uima.types.annot.Token");
 
  /** @generated */
  final Feature casFeat_tag;
  /** @generated */
  final int     casFeatCode_tag;
  /** @generated */ 
  public String getTag(int addr) {
        if (featOkTst && casFeat_tag == null)
      jcas.throwFeatMissing("tag", "org.werti.uima.types.annot.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tag);
  }
  /** @generated */    
  public void setTag(int addr, String v) {
        if (featOkTst && casFeat_tag == null)
      jcas.throwFeatMissing("tag", "org.werti.uima.types.annot.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_tag, v);}
    
  
 
  /** @generated */
  final Feature casFeat_lemm;
  /** @generated */
  final int     casFeatCode_lemm;
  /** @generated */ 
  public String getLemm(int addr) {
        if (featOkTst && casFeat_lemm == null)
      jcas.throwFeatMissing("lemm", "org.werti.uima.types.annot.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_lemm);
  }
  /** @generated */    
  public void setLemm(int addr, String v) {
        if (featOkTst && casFeat_lemm == null)
      jcas.throwFeatMissing("lemm", "org.werti.uima.types.annot.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_lemm, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Token_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_tag = jcas.getRequiredFeatureDE(casType, "tag", "uima.cas.String", featOkTst);
    casFeatCode_tag  = (null == casFeat_tag) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tag).getCode();

 
    casFeat_lemm = jcas.getRequiredFeatureDE(casType, "lemm", "uima.cas.String", featOkTst);
    casFeatCode_lemm  = (null == casFeat_lemm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lemm).getCode();

  }
}



    