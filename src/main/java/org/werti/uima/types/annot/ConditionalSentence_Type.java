
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

/** A sentence containing a conditional.
 * Updated by JCasGen Thu Oct 09 14:27:11 CEST 2008
 * @generated */
public class ConditionalSentence_Type extends SentenceAnnotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (ConditionalSentence_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = ConditionalSentence_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new ConditionalSentence(addr, ConditionalSentence_Type.this);
  			   ConditionalSentence_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new ConditionalSentence(addr, ConditionalSentence_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = ConditionalSentence.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.werti.uima.types.annot.ConditionalSentence");
 
  /** @generated */
  final Feature casFeat_trigger;
  /** @generated */
  final int     casFeatCode_trigger;
  /** @generated */ 
  public int getTrigger(int addr) {
        if (featOkTst && casFeat_trigger == null)
      jcas.throwFeatMissing("trigger", "org.werti.uima.types.annot.ConditionalSentence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_trigger);
  }
  /** @generated */    
  public void setTrigger(int addr, int v) {
        if (featOkTst && casFeat_trigger == null)
      jcas.throwFeatMissing("trigger", "org.werti.uima.types.annot.ConditionalSentence");
    ll_cas.ll_setRefValue(addr, casFeatCode_trigger, v);}
    
   /** @generated */
  public int getTrigger(int addr, int i) {
        if (featOkTst && casFeat_trigger == null)
      jcas.throwFeatMissing("trigger", "org.werti.uima.types.annot.ConditionalSentence");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_trigger), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_trigger), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_trigger), i);
  }
   
  /** @generated */ 
  public void setTrigger(int addr, int i, int v) {
        if (featOkTst && casFeat_trigger == null)
      jcas.throwFeatMissing("trigger", "org.werti.uima.types.annot.ConditionalSentence");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_trigger), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_trigger), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_trigger), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_condition;
  /** @generated */
  final int     casFeatCode_condition;
  /** @generated */ 
  public int getCondition(int addr) {
        if (featOkTst && casFeat_condition == null)
      jcas.throwFeatMissing("condition", "org.werti.uima.types.annot.ConditionalSentence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_condition);
  }
  /** @generated */    
  public void setCondition(int addr, int v) {
        if (featOkTst && casFeat_condition == null)
      jcas.throwFeatMissing("condition", "org.werti.uima.types.annot.ConditionalSentence");
    ll_cas.ll_setRefValue(addr, casFeatCode_condition, v);}
    
   /** @generated */
  public int getCondition(int addr, int i) {
        if (featOkTst && casFeat_condition == null)
      jcas.throwFeatMissing("condition", "org.werti.uima.types.annot.ConditionalSentence");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_condition), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_condition), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_condition), i);
  }
   
  /** @generated */ 
  public void setCondition(int addr, int i, int v) {
        if (featOkTst && casFeat_condition == null)
      jcas.throwFeatMissing("condition", "org.werti.uima.types.annot.ConditionalSentence");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_condition), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_condition), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_condition), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_result;
  /** @generated */
  final int     casFeatCode_result;
  /** @generated */ 
  public int getResult(int addr) {
        if (featOkTst && casFeat_result == null)
      jcas.throwFeatMissing("result", "org.werti.uima.types.annot.ConditionalSentence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_result);
  }
  /** @generated */    
  public void setResult(int addr, int v) {
        if (featOkTst && casFeat_result == null)
      jcas.throwFeatMissing("result", "org.werti.uima.types.annot.ConditionalSentence");
    ll_cas.ll_setRefValue(addr, casFeatCode_result, v);}
    
   /** @generated */
  public int getResult(int addr, int i) {
        if (featOkTst && casFeat_result == null)
      jcas.throwFeatMissing("result", "org.werti.uima.types.annot.ConditionalSentence");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_result), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_result), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_result), i);
  }
   
  /** @generated */ 
  public void setResult(int addr, int i, int v) {
        if (featOkTst && casFeat_result == null)
      jcas.throwFeatMissing("result", "org.werti.uima.types.annot.ConditionalSentence");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_result), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_result), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_result), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public ConditionalSentence_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_trigger = jcas.getRequiredFeatureDE(casType, "trigger", "uima.cas.FSArray", featOkTst);
    casFeatCode_trigger  = (null == casFeat_trigger) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_trigger).getCode();

 
    casFeat_condition = jcas.getRequiredFeatureDE(casType, "condition", "uima.cas.FSArray", featOkTst);
    casFeatCode_condition  = (null == casFeat_condition) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_condition).getCode();

 
    casFeat_result = jcas.getRequiredFeatureDE(casType, "result", "uima.cas.FSArray", featOkTst);
    casFeatCode_result  = (null == casFeat_result) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_result).getCode();

  }
}



    