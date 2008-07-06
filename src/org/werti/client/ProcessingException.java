package org.werti.client;

/**
 * An Exception to be sent by the server to the client when the processing
 * of the page fails.
 *
 * @author Aleksandar Dimitrov
 */

public class ProcessingException extends Exception {

	public static final long serialVersionUID = 10;

	public ProcessingException() {
		super("Very bad. Tell Aleks to fix his code!");
	}

	/**
	 * @param message
	 */
	public ProcessingException(String message) {
		super(message);
	}

	/**
	 * @param arg0
	 */
	public ProcessingException(Throwable message) {
		super(message);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public ProcessingException(String message, Throwable exception) {
		super(message, exception);
	}

}
