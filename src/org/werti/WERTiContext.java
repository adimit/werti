package org.werti;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Properties;

import javax.servlet.ServletContext;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.uima.examples.tagger.trainAndTest.ModelGeneration;

public class WERTiContext {
	private static final String PROPS = "/WERTi.properties";
	private static final Log log = LogFactory.getLog(WERTiContext.class);

	private static ServletContext servlet;

	// Burn, memory, burn! FIXME: this needs more testing!
	private static MaxentTagger ptbtagger_en;
	private static MaxentTagger ptbtagger_de;
	private static MaxentTagger ptbtagger_ar;
	private static MaxentTagger ptbtagger_zh;
	private static ModelGeneration hmmtagger_en;
	private static ModelGeneration hmmtagger_de;

	private static final Properties p = new Properties();

	public WERTiContext(final ServletContext servlet) {
		WERTiContext.servlet = servlet;
		InputStream is = servlet.getResourceAsStream(PROPS);
		try {
			p.load(is);
		} catch (IOException ioe) {
			log.fatal("Unable to load properties!",ioe);
			throw new RuntimeException("I don't kow where the taggers are!", ioe);
		}
	}

	// return the appropriate tagger reference for the language
	private static MaxentTagger get_ptbtagger(final String lang) {
		if (lang.equals("de")) {
			return WERTiContext.ptbtagger_de;
		} else if (lang.equals("zh")) {
			return WERTiContext.ptbtagger_zh;
		} else if (lang.equals("ar")) {
			return WERTiContext.ptbtagger_ar;
		} else {
			return WERTiContext.ptbtagger_en;
		}
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
	 * Gets the ptbtagger for this instance.
	 *
	 * @param lang The two letter language code
	 * @return The ptbtagger.
	 */
	public static MaxentTagger getPtbtagger (final String lang) {
		MaxentTagger ptbtagger = get_ptbtagger(lang);
		if (ptbtagger == null) {
			final String ptbloc = p.getProperty("ptbtagger.base")
				+ p.getProperty("ptbtagger"+lang);
			final URL ptbpath;
			try {
				ptbpath = servlet.getResource(ptbloc);
			} catch (MalformedURLException murle) {
				log.fatal("No access to tagger resource "+ptbloc, murle);
				throw new RuntimeException("No tagger, no game");
			}
			log.debug("Trying to fetch from " + ptbpath + "; getFile(): " + ptbpath.getFile());
			try {
				ptbtagger = new MaxentTagger(ptbpath.getFile());
			} catch (Exception e) {
				log.fatal("Tagger's bitchin'. Sorry, pal.", e);
				throw new RuntimeException("No tagger, no game");
			}
		}
		return ptbtagger;
	}

	/**
	 * Gets the hmmtagger for this instance.
	 *
	 * @param lang The two-letter language code
	 * @return The hmmtagger.
	 */
	public static ModelGeneration getHmmtagger(final String lang) {
		ModelGeneration hmmtagger = get_hmmtagger(lang);
		if (hmmtagger == null) {
			final String hmmloc = p.getProperty("hmmtagger.base")
				+ p.getProperty("hmmtagger"+lang);
			final InputStream hmmstream;
			hmmstream = servlet.getResourceAsStream(hmmloc);
			log.debug("Trying to fetch from " + hmmstream);
			try {
				ObjectInputStream ois = new ObjectInputStream(hmmstream);
				hmmtagger = (ModelGeneration) ois.readObject();
			} catch (IOException ioe) {
				log.fatal("Error reading from input stream.", ioe);
				throw new RuntimeException("No tagger, no game", ioe);
			} catch (ClassNotFoundException cnfe) {
				log.fatal("Couldn't find class to read from", cnfe);
				throw new RuntimeException("No tagger, no game", cnfe);
			}
		}
		return hmmtagger;
	}
}
