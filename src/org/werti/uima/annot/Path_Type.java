
/* First created by JCasGen Wed May 28 02:55:53 CEST 2008 */
package org.werti.uima.annot;

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

/** Path in a html file
 * Updated by JCasGen Wed May 28 02:57:22 CEST 2008
 * @generated */
public class Path_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Path_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Path_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Path(addr, Path_Type.this);
  			   Path_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Path(addr, Path_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = Path.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.werti.uima.annot.Path");
 
  /** @generated */
  final Feature casFeat_localized;
  /** @generated */
  final int     casFeatCode_localized;
  /** @generated */ 
  public String getLocalized(int addr) {
        if (featOkTst && casFeat_localized == null)
      jcas.throwFeatMissing("localized", "org.werti.uima.annot.Path");
    return ll_cas.ll_getStringValue(addr, casFeatCode_localized);
  }
  /** @generated */    
  public void setLocalized(int addr, String v) {
        if (featOkTst && casFeat_localized == null)
      jcas.throwFeatMissing("localized", "org.werti.uima.annot.Path");
    ll_cas.ll_setStringValue(addr, casFeatCode_localized, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Path_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_localized = jcas.getRequiredFeatureDE(casType, "localized", "uima.cas.String", featOkTst);
    casFeatCode_localized  = (null == casFeat_localized) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_localized).getCode();

  }
}



    