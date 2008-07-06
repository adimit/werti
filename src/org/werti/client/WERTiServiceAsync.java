package org.werti.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WERTiServiceAsync {
	@SuppressWarnings("unchecked")
	public void process(String method, String language, String[] tags, String url, AsyncCallback callback) 
		throws ProcessingException, InitializationException, URLException;
}
