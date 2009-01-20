package org.werti.uima.ae;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;

/**
 * An exception that is thrown by the Language Checker in the WERTi
 * pipeline if the document language is not in the required set of
 * languages.
 * @author Niels Ott
 *
 */

public class LanguageCheckerException extends AnalysisEngineProcessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1937207923092942838L;

	public LanguageCheckerException() {
	}

	public LanguageCheckerException(Throwable cause) {
		super(cause);
	}

	public LanguageCheckerException(String messageKey, Object[] arguments) {
		super(messageKey, arguments);
	}

	public LanguageCheckerException(String resourceBundleName,
			String messageKey, Object[] arguments) {
		super(resourceBundleName, messageKey, arguments);
	}

	public LanguageCheckerException(String messageKey, Object[] arguments,
			Throwable cause) {
		super(messageKey, arguments, cause);
	}

	public LanguageCheckerException(String resourceBundleName,
			String messageKey, Object[] arguments, Throwable cause) {
		super(resourceBundleName, messageKey, arguments, cause);
	}

}
