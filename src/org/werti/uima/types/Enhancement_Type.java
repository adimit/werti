
/* First created by JCasGen Wed Jun 04 02:43:38 CEST 2008 */
package org.werti.uima.types;

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

/** Describes an enhancment on the current spot.
 * Updated by JCasGen Wed Jun 04 04:34:24 CEST 2008
 * @generated */
public class Enhancement_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Enhancement_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Enhancement_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Enhancement(addr, Enhancement_Type.this);
  			   Enhancement_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Enhancement(addr, Enhancement_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = Enhancement.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.werti.uima.types.Enhancement");
 
  /** @generated */
  final Feature casFeat_enhancement_list;
  /** @generated */
  final int     casFeatCode_enhancement_list;
  /** @generated */ 
  public int getEnhancement_list(int addr) {
        if (featOkTst && casFeat_enhancement_list == null)
      jcas.throwFeatMissing("enhancement_list", "org.werti.uima.types.Enhancement");
    return ll_cas.ll_getRefValue(addr, casFeatCode_enhancement_list);
  }
  /** @generated */    
  public void setEnhancement_list(int addr, int v) {
        if (featOkTst && casFeat_enhancement_list == null)
      jcas.throwFeatMissing("enhancement_list", "org.werti.uima.types.Enhancement");
    ll_cas.ll_setRefValue(addr, casFeatCode_enhancement_list, v);}
    
   /** @generated */
  public String getEnhancement_list(int addr, int i) {
        if (featOkTst && casFeat_enhancement_list == null)
      jcas.throwFeatMissing("enhancement_list", "org.werti.uima.types.Enhancement");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_enhancement_list), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_enhancement_list), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_enhancement_list), i);
  }
   
  /** @generated */ 
  public void setEnhancement_list(int addr, int i, String v) {
        if (featOkTst && casFeat_enhancement_list == null)
      jcas.throwFeatMissing("enhancement_list", "org.werti.uima.types.Enhancement");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_enhancement_list), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_enhancement_list), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_enhancement_list), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_index_list;
  /** @generated */
  final int     casFeatCode_index_list;
  /** @generated */ 
  public int getIndex_list(int addr) {
        if (featOkTst && casFeat_index_list == null)
      jcas.throwFeatMissing("index_list", "org.werti.uima.types.Enhancement");
    return ll_cas.ll_getRefValue(addr, casFeatCode_index_list);
  }
  /** @generated */    
  public void setIndex_list(int addr, int v) {
        if (featOkTst && casFeat_index_list == null)
      jcas.throwFeatMissing("index_list", "org.werti.uima.types.Enhancement");
    ll_cas.ll_setRefValue(addr, casFeatCode_index_list, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Enhancement_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_enhancement_list = jcas.getRequiredFeatureDE(casType, "enhancement_list", "uima.cas.StringArray", featOkTst);
    casFeatCode_enhancement_list  = (null == casFeat_enhancement_list) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_enhancement_list).getCode();

 
    casFeat_index_list = jcas.getRequiredFeatureDE(casType, "index_list", "uima.cas.IntegerList", featOkTst);
    casFeatCode_index_list  = (null == casFeat_index_list) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_index_list).getCode();

  }
}



    