package org.werti;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Properties;

import javax.servlet.ServletContext;

import com.aliasi.hmm.HiddenMarkovModel;
import com.aliasi.hmm.HmmDecoder;

import org.apache.log4j.Logger;

import org.apache.uima.resource.ResourceInitializationException;

import org.werti.client.InitializationException;

import org.werti.util.Resources;

/**
 * Your random garden variety memory hog.
 *
 * Stores static references to different tagger instances. In theory not many of those are
 * going to be used.
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public final class WERTiContext {
	private static final Logger log = Logger.getLogger(WERTiContext.class);

	private static final String PROPS = "/WERTi.properties";

	private static ServletContext servlet;

	private static Properties p;

	private static HmmDecoder lgptagger;

	/**
	 * Initialize our context from a given servlet context.
	 *
	 * @param servlet The running servlet's context
	 */
	public WERTiContext(final ServletContext servlet) throws InitializationException {
		WERTiContext.servlet = servlet;
		final InputStream is = servlet.getResourceAsStream(PROPS);
		try {
			p = new Properties();
			p.load(is);
		} catch (IOException ioe) {
			throw new InitializationException("I don't know where the taggers are!", ioe);
		}
	}

	public static HmmDecoder getLpgtagger() throws ResourceInitializationException {
		if (lgptagger == null) {
			final String lgpmodel = p.getProperty("lgptagger.base")
				+ p.getProperty("lgptagger.en");
			
			final HiddenMarkovModel hmm;
			hmm = (HiddenMarkovModel) Resources.getResource(lgpmodel, servlet);
			WERTiContext.lgptagger = new HmmDecoder(hmm);
		}
		return lgptagger;
	}

	/**
	 * Gets the Properties for this instance.
	 *
	 * @return The Properties.
	 */
	public Properties getProperties () {
		return p;
	}

	public String getProperty(String key) {
		return p.getProperty(key);
	}
}
