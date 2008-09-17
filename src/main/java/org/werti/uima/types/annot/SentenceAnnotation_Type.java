
/* First created by JCasGen Wed Sep 17 17:47:41 CEST 2008 */
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

/** A sentence in natural language on the page to be processed.
 * Updated by JCasGen Wed Sep 17 17:47:41 CEST 2008
 * @generated */
public class SentenceAnnotation_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (SentenceAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = SentenceAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new SentenceAnnotation(addr, SentenceAnnotation_Type.this);
  			   SentenceAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new SentenceAnnotation(addr, SentenceAnnotation_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = SentenceAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.werti.uima.types.annot.SentenceAnnotation");
 
  /** @generated */
  final Feature casFeat_coherence;
  /** @generated */
  final int     casFeatCode_coherence;
  /** @generated */ 
  public double getCoherence(int addr) {
        if (featOkTst && casFeat_coherence == null)
      jcas.throwFeatMissing("coherence", "org.werti.uima.types.annot.SentenceAnnotation");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_coherence);
  }
  /** @generated */    
  public void setCoherence(int addr, double v) {
        if (featOkTst && casFeat_coherence == null)
      jcas.throwFeatMissing("coherence", "org.werti.uima.types.annot.SentenceAnnotation");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_coherence, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public SentenceAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_coherence = jcas.getRequiredFeatureDE(casType, "coherence", "uima.cas.Double", featOkTst);
    casFeatCode_coherence  = (null == casFeat_coherence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_coherence).getCode();

  }
}



    