package org.werti.uima.ae;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

/**
 * An UIMA annotator that checks for the language used in
 * a document in the WERTi pipeline and fails if it doesn't
 * match a given set of languages specified in  the configuration.
 * 
 * @author Niels Ott
 *
 */
public class LanguageChecker extends JCasAnnotator_ImplBase {

	@Override
	public void process(JCas cas) throws AnalysisEngineProcessException {
		// FIXMD: make this do something
		
	}




}
