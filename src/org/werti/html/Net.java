package org.werti.html;

import java.io.*;

import java.net.*;




/* TODO: remove logging from this class.
 * Ideally, it shouldn't log, but only do its work and leave logging to the big guys
 */


public class Net {
	public static BufferedInputStream fetch(String site_url) throws MalformedURLException, IOException {
		final URL url = new URL(site_url);
		return new BufferedInputStream(url.openStream());

//		final URLConnection uc = url.openConnection();
//		uc.connect();
//		final Object content = uc.getContent();
//		log.info("Retrieved Content of type: "+ content.getClass().getCanonicalName());
	}
}
