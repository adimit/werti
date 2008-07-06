package org.werti.client;

/**
 * An Exception to be sent by the server to the client when the initialization
 * of the processing components fails.
 *
 * @author Aleksandar Dimitrov
 */

public class InitializationException extends Exception {

	public static final long serialVersionUID = 10;

	public InitializationException() {
		super("Very bad. Tell Aleks to fix his code!");
	}

	/**
	 * @param message
	 */
	public InitializationException(String message) {
		super(message);
	}

	/**
	 * @param arg0
	 */
	public InitializationException(Throwable message) {
		super(message);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public InitializationException(String message, Throwable exception) {
		super(message, exception);
	}

}
