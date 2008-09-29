
/* First created by JCasGen Sat Sep 27 13:39:42 CEST 2008 */
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

/** A potential conditional to be evaluated.
 * Updated by JCasGen Sat Sep 27 13:39:42 CEST 2008
 * @generated */
public class Conditional_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Conditional_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Conditional_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Conditional(addr, Conditional_Type.this);
  			   Conditional_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Conditional(addr, Conditional_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = Conditional.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.werti.uima.types.annot.Conditional");
 
  /** @generated */
  final Feature casFeat_trigger;
  /** @generated */
  final int     casFeatCode_trigger;
  /** @generated */ 
  public String getTrigger(int addr) {
        if (featOkTst && casFeat_trigger == null)
      jcas.throwFeatMissing("trigger", "org.werti.uima.types.annot.Conditional");
    return ll_cas.ll_getStringValue(addr, casFeatCode_trigger);
  }
  /** @generated */    
  public void setTrigger(int addr, String v) {
        if (featOkTst && casFeat_trigger == null)
      jcas.throwFeatMissing("trigger", "org.werti.uima.types.annot.Conditional");
    ll_cas.ll_setStringValue(addr, casFeatCode_trigger, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Conditional_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_trigger = jcas.getRequiredFeatureDE(casType, "trigger", "uima.cas.String", featOkTst);
    casFeatCode_trigger  = (null == casFeat_trigger) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_trigger).getCode();

  }
}



    