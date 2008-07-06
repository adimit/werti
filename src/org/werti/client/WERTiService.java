package org.werti.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface WERTiService extends RemoteService {
	public String process(String method, String language, String[] tags, String url);
}
