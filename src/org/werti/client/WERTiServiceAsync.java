package org.werti.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WERTiServiceAsync {
	@SuppressWarnings("unchecked")
	public void process(String[] pipeline, String[] tags, String url, String enhance, AsyncCallback callback);
}
