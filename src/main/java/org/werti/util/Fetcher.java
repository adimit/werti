package org.werti.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;


/**
 * Retrieves the text on a web page.
 */
public class Fetcher extends Thread { 
	private static final Logger log =
		Logger.getLogger(Fetcher.class);

	String base_url;
	final URL site_url;
	String text;
	int port;


	public Fetcher(final String url) throws MalformedURLException {
		this.site_url = new URL(url);
	}

	public void run() {
		try {
			port = site_url.getPort();
			base_url = site_url.getHost();
			log.debug("Host name of target URL '" + site_url +"': " + base_url);

			final BufferedReader content = new BufferedReader
				(new InputStreamReader(site_url.openStream()));

			text = bis2str(content);
			content.close();
			log.debug("Fetched site.");
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	// converts a buffered reader to a String. Don't forget to close it.
	private static String bis2str(BufferedReader in) {
		final StringBuilder sb = new StringBuilder();
		try {
			if (!in.ready()) {
				log.error("No input stream to read from!");
			} else {
				log.debug("Input stream seems to be fine.");
			}
			String inLine;
			while ((inLine = in.readLine()) != null) {
				sb.append(inLine);
			}
			log.debug("Last line I read: " + inLine);
		} catch (IOException ioe) {
			log.error("Encountered errors while retrieving remote site!", ioe);
		}
		return sb.toString();
	}

	/**
	 * Gets the base_url for this instance.
	 *
	 * @return The base_url.
	 */
	public String getBase_url () {
		return this.base_url;
	}

	/**
	 * Gets the text of the retrieved page for this instance.
	 *
	 * @return The text of the retrieved page.
	 */
	public String getText () {
		return this.text;
	}

	/**
	 * Gets the port for this instance.
	 *
	 * @return The port.
	 */
	public int getPort () {
		return this.port;
	}
}
