package org.werti;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Properties;

import javax.servlet.ServletContext;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import org.apache.log4j.Logger;

import org.apache.uima.examples.tagger.trainAndTest.ModelGeneration;

import org.apache.uima.resource.ResourceInitializationException;

import org.werti.client.InitializationException;

public class WERTiContext {
	private static final Logger log = Logger.getLogger(WERTiContext.class);

	private static final String PROPS = "/WERTi.properties";

	private static ServletContext servlet;

	private static MaxentTagger ptbtagger_en;
	private static ModelGeneration hmmtagger_en;
	private static ModelGeneration hmmtagger_de;

	private static final Properties p = new Properties();

	public WERTiContext(final ServletContext servlet) throws InitializationException {
		WERTiContext.servlet = servlet;
		InputStream is = servlet.getResourceAsStream(PROPS);
		try {
			p.load(is);
		} catch (IOException ioe) {
			throw new InitializationException("I don't know where the taggers are!", ioe);
		}
	}

	// return the appropriate tagger reference for the language
	private static MaxentTagger get_ptbtagger(final String lang)
		throws ResourceInitializationException {
		if (ptbtagger_en != null) {
			return ptbtagger_en;
		}
		final String ptbloc = p.getProperty("ptbtagger.base")
			+ p.getProperty("ptbtagger."+lang);
		final URL ptbpath;
		try {
			ptbpath = servlet.getResource(ptbloc);
		} catch (MalformedURLException murle) {
			final Object[] args = { ptbloc };
			throw new ResourceInitializationException(
					ResourceInitializationException.MALFORMED_URL, args , murle);
		}
		try {
			final File f = new File(ptbpath.toURI());
			log.debug("Trying to fetch from " + ptbpath + "; getFile(): " + ptbpath.getFile());
			ptbtagger_en = new MaxentTagger(f.getAbsolutePath());
			return ptbtagger_en;
		} catch (NullPointerException npe) {
			final Object[] args = { ptbloc };
			throw new ResourceInitializationException(
					ResourceInitializationException.COULD_NOT_ACCESS_DATA, args);
		} catch (Exception e) {
			final Object[] args = { "MaxentTagger" };
			throw new ResourceInitializationException(
					ResourceInitializationException.COULD_NOT_INSTANTIATE, args, e);
		}
	}

	/**
	 * Gets the ptbtagger for this instance.
	 *
	 * @param lang The two letter language code
	 * @return The ptbtagger.
	 */
	public static MaxentTagger getPtbtagger (final String lang)
		throws ResourceInitializationException {
		log.warn(lang);
		MaxentTagger ptbtagger = get_ptbtagger(lang);
		return ptbtagger;
	}

	// return the appropriate tagger reference for the language
	private static ModelGeneration get_hmmtagger(final String lang) {
		if (lang.equals("de")) {
			return WERTiContext.hmmtagger_de;
		} else  {
			return WERTiContext.hmmtagger_en;
		}
	}

	/**
	 * Gets the hmmtagger for this instance.
	 *
	 * @param lang The two-letter language code
	 * @return The hmmtagger.
	 */
	public static ModelGeneration getHmmtagger(final String lang) throws ResourceInitializationException {
		ModelGeneration hmmtagger = get_hmmtagger(lang);
		if (hmmtagger == null) {
			final String hmmloc = p.getProperty("hmmtagger.base")
				+ p.getProperty("hmmtagger."+lang);
			log.warn(hmmloc);
			final URL hmmpath;
			try {
				hmmpath = servlet.getResource(hmmloc);
			} catch (MalformedURLException murle) {
				final Object[] args = { hmmloc };
				throw new ResourceInitializationException(
						ResourceInitializationException.MALFORMED_URL, args , murle);
			}
			try {
				ObjectInputStream ois = new ObjectInputStream(hmmpath.openStream());
				hmmtagger = (ModelGeneration) ois.readObject();
			} catch (IOException ioe) {
				final Object[] args = { hmmloc };
				throw new ResourceInitializationException(
						ResourceInitializationException.COULD_NOT_ACCESS_DATA, args, ioe);
			} catch (NullPointerException npe) {
				final Object[] args = { hmmloc };
				throw new ResourceInitializationException(
						ResourceInitializationException.COULD_NOT_ACCESS_DATA, args);
			} catch (ClassNotFoundException cnfe) {
				final Object[] args = { hmmloc };
				throw new ResourceInitializationException(
						ResourceInitializationException.CLASS_NOT_FOUND, args, cnfe);
			}
		}
		return hmmtagger;
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
