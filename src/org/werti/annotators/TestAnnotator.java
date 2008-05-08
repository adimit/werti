package org.werti.annotators;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

public class TestAnnotator extends JCasAnnotator_ImplBase {
	public void process(JCas jc) {
		String t = jc.getDocumentText();

		
	}
}
