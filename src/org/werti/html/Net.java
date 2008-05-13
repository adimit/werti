package org.werti.html;

import java.io.*;

import java.net.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.logging.*;


public class Net {
	private static final Logger log = Logger.getLogger("org.werti");
	/**
	 * Open a buffered input stream to the url.
	 *  
	 * Oddly enough, this doesn't seem to work with a URLConnection (not for Reuters at least)
	 * and has to be done using the InputStream from the URL directly. This may be a bug in 
	 * Sun's libs
	 */
	public static BufferedReader fetch(String site_url) throws MalformedURLException, IOException {
		final URL url = new URL(site_url);
		final InputStream urlstream = url.openStream();
		final URLConnection uc = url.openConnection();
		final InputStream content = uc.getInputStream();
		return new BufferedReader(new InputStreamReader(urlstream));
	}

	// Debugging method returns a serialized form of its argument
	public static String log_map(final Map<String, List<String>> map) {
		final Iterator<Map.Entry<String, List<String>>> it = map.entrySet().iterator();
		String b = "";
		while (it.hasNext()) {
			final Map.Entry<String, List<String>> e = it.next();
			final List<String> l = e.getValue();
			b = "Key: " + e.getKey()+"\nValues:\n";
			for (String s:l) {
				b += s + "\n";
			}
		}
		return b;
	}

	/** 
	 * Return the base url (server root) of the requested url (with a slash at the end). 
	 *
	 * We search for the first occurence of a / from behind the 'http://'
	 * token.
	 */
	public static String find_baseurl(String url) { 
		return url.substring(0, url.indexOf("/", 7)+1); 
	} 
}
