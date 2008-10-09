
/* First created by JCasGen Thu Oct 09 14:27:11 CEST 2008 */
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

/** Optional annotation to specify which text to work on.
 * Updated by JCasGen Thu Oct 09 14:27:11 CEST 2008
 * @generated */
public class RelevantText_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (RelevantText_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = RelevantText_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new RelevantText(addr, RelevantText_Type.this);
  			   RelevantText_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new RelevantText(addr, RelevantText_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = RelevantText.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.werti.uima.types.annot.RelevantText");
 
  /** @generated */
  final Feature casFeat_enclosing_tag;
  /** @generated */
  final int     casFeatCode_enclosing_tag;
  /** @generated */ 
  public String getEnclosing_tag(int addr) {
        if (featOkTst && casFeat_enclosing_tag == null)
      jcas.throwFeatMissing("enclosing_tag", "org.werti.uima.types.annot.RelevantText");
    return ll_cas.ll_getStringValue(addr, casFeatCode_enclosing_tag);
  }
  /** @generated */    
  public void setEnclosing_tag(int addr, String v) {
        if (featOkTst && casFeat_enclosing_tag == null)
      jcas.throwFeatMissing("enclosing_tag", "org.werti.uima.types.annot.RelevantText");
    ll_cas.ll_setStringValue(addr, casFeatCode_enclosing_tag, v);}
    
  
 
  /** @generated */
  final Feature casFeat_relevant;
  /** @generated */
  final int     casFeatCode_relevant;
  /** @generated */ 
  public boolean getRelevant(int addr) {
        if (featOkTst && casFeat_relevant == null)
      jcas.throwFeatMissing("relevant", "org.werti.uima.types.annot.RelevantText");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_relevant);
  }
  /** @generated */    
  public void setRelevant(int addr, boolean v) {
        if (featOkTst && casFeat_relevant == null)
      jcas.throwFeatMissing("relevant", "org.werti.uima.types.annot.RelevantText");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_relevant, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public RelevantText_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_enclosing_tag = jcas.getRequiredFeatureDE(casType, "enclosing_tag", "uima.cas.String", featOkTst);
    casFeatCode_enclosing_tag  = (null == casFeat_enclosing_tag) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_enclosing_tag).getCode();

 
    casFeat_relevant = jcas.getRequiredFeatureDE(casType, "relevant", "uima.cas.Boolean", featOkTst);
    casFeatCode_relevant  = (null == casFeat_relevant) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_relevant).getCode();

  }
}



    