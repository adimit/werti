package org.werti.html;

import java.io.*;
import java.util.logging.*;
import java.net.*;


public class Net {
	private static final Logger log = Logger.getLogger("org.werti");
	public static Object fetch(String site_url) throws MalformedURLException, IOException {
		final URL url = new URL(site_url);
		final URLConnection uc = url.openConnection();
		uc.connect();
		final Object content = uc.getContent();
		log.info("Content Encoding: "+ uc.getContentEncoding());
		return content;
	}
}
