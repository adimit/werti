package org.werti.client;

/**
 * An Exception to be sent by the server to the client when the processing
 * of the page fails.
 *
 * @author Aleksandar Dimitrov
 * @version 1.0
 */

public class ProcessingException extends Exception {

	public static final long serialVersionUID = 10;

	/**
	 * Don't use this.
	 */
	public ProcessingException() {
		super("Very bad. Tell Aleks to fix his code!");
	}

	/**
	 * Just a single message.
	 *
	 * Please don't really call this unless you're genuinely throwing this
	 * exception and not catching  - we'd like to have the exception proper
	 * ending up at client side
	 *
	 * @param message An Exception message
	 */
	public ProcessingException(String message) {
		super(message);
	}

	/**
	 * Just the exception.
	 *
	 * Again, sending both, a message and an exception would be nice.
	 *
	 * @param exception
	 */
	public ProcessingException(Throwable message) {
		super(message);
	}

	/**
	 * This gives the user interface the full possibilities of reacting.
	 *
	 * @param message A message to be sent along with the exception
	 * @param exception An exception that occured in server-side context
	 */
	public ProcessingException(String message, Throwable exception) {
		super(message, exception);
	}

}
