package org.werti.util;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

import org.apache.uima.cas.CASException;

import org.apache.uima.jcas.JCas;

public final class AEUtil {

	/* Generic buffer size for input streams */
	public static final int BUFSIZE = 256;

	public static JCas maybeGetJCasView(JCas cas, String view)
		throws AnalysisEngineProcessException {
		try {
			return cas.getView(view);
		} catch (CASException ce) {
			throw new AnalysisEngineProcessException(ce);
		}
	}

	public static JCas maybeCreateJCasView(JCas cas, String view)
		throws AnalysisEngineProcessException {
		try {
			return cas.createView(view);
		} catch (CASException ce) {
			throw new AnalysisEngineProcessException(ce);
		}
	}
}
