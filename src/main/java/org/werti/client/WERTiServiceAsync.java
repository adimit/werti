package org.werti.client;

import org.werti.client.run.RunConfiguration;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The service interface to the server.
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public interface WERTiServiceAsync {
	/**
	 * Process the user's request.
	 *
	 * @param method The task the user has requested.
	 * @param language The language the user says the target is in.
	 * @param tags A bunch of part-of-speech tags you want to highlight.
	 * @param url The URL of the page the user has requested.
	 */
	public void process(RunConfiguration config, String url, AsyncCallback<String> callback);
}
