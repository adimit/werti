package org.werti;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Properties;

import javax.servlet.ServletContext;

import com.aliasi.hmm.HiddenMarkovModel;
import com.aliasi.hmm.HmmDecoder;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import org.apache.log4j.Logger;

import org.apache.uima.examples.tagger.trainAndTest.ModelGeneration;

import org.apache.uima.resource.ResourceInitializationException;

import org.werti.client.InitializationException;

/**
 * Your random garden variety memory hog.
 *
 * Stores static references to different tagger instances. In theory not many of those are
 * going to be used.
 */
public final class WERTiContext {
	private static final Logger log = Logger.getLogger(WERTiContext.class);

	private static final String PROPS = "/WERTi.properties";

	private static ServletContext servlet;

	private static MaxentTagger ptbtagger_en;
	private static ModelGeneration hmmtagger_en;
	private static ModelGeneration hmmtagger_de;

	private static Properties p;

	private static HmmDecoder lgptagger;

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
		throw new UnsupportedOperationException("The Stanford Tagger's API is crap."
				+ " We can't currently work with it. An implementation of the "
				+ "Stanford Tagger might be provided in the future.");
		//log.warn(lang);
		//MaxentTagger ptbtagger = get_ptbtagger(lang);
		//return ptbtagger;
	}

	public static HmmDecoder getLpgtagger() throws ResourceInitializationException {
		if (lgptagger == null) {
			final String lgpmodel = p.getProperty("lgptagger.base")
				+ p.getProperty("lgptagger.en");
			
			final HiddenMarkovModel hmm;
			hmm = (HiddenMarkovModel) getModel(lgpmodel);
			WERTiContext.lgptagger = new HmmDecoder(hmm);
		}
		return lgptagger;
	}

	// The extra 6 bytes per method call don't really matter all that much and we
	// reduce boiler plating for exception throwing in getModel(String).
	private static final ResourceInitializationException
		noAccess(String reason, Exception exception) {
		final Object[] args = { reason };
		final ResourceInitializationException noAccess = 
			new ResourceInitializationException
				(ResourceInitializationException.COULD_NOT_ACCESS_DATA
				 , args
				 , exception);
		return noAccess;
	}

	/**
	 * Fetch a model from the servlet context.
	 *
	 * This is a super-safe method that shuoldn't leak any dangling references.
	 * Thanks to dmlloyd at ##java.
	 *
	 * Note that this could be used as a generic way to access resources
	 * from the servlet context. We would only have to make it public, but
	 * since I don't have any applications for it, it remains private.
	 *
	 * @param path The full path to the model (including file name) based on
	 * the servlet context's root.
	 * @return An object input stream to it.
	 */
	private static final Object getModel(String path) 
		throws ResourceInitializationException {
		final URL mPath;
		try { // to find the correct location of the resource
			mPath = servlet.getResource(path);
		} catch (MalformedURLException murle) {
			final Object[] args = { path };
			throw new ResourceInitializationException
				(ResourceInitializationException.MALFORMED_URL
				 , args
				 , murle);
		}
		try { // to open a connection to the resource
			final InputStream is = mPath.openStream();
			try { // to connect to the object input stream of the resource
				final ObjectInputStream ois = new ObjectInputStream(is);
				try { // to actually read it in and return it.
					final Object o = ois.readObject();
					ois.close();
					is.close();
					return o;
				} catch (ClassNotFoundException cnfe) {
					throw noAccess("The class of the model object is unknown. "
							+ "Path: " + path, cnfe);
				} finally {
					try {
						ois.close();
					} catch(IOException ioe) {
						throw noAccess(path, ioe);
					}
				}
			} finally {
				try {
					is.close();
				} catch(IOException ioe) {
					throw noAccess(path, ioe);
				}
			}
		} catch (IOException ioe) {
			throw noAccess(path, ioe);
		} catch (NullPointerException npe) {
			throw noAccess("Couldn't load path: "+path, npe);
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
	 * Gets the hmmtagger for this instance.
	 *
	 * @param lang The two-letter language code
	 * @return The hmmtagger.
	 */
	public static ModelGeneration getHmmtagger(String lang) 
		throws ResourceInitializationException {
		ModelGeneration hmmtagger = get_hmmtagger(lang);
		if (hmmtagger == null) {
			final String hmmloc = p.getProperty("hmmtagger.base")
				+ p.getProperty("hmmtagger."+lang);
			log.debug("Reading from: " + hmmloc);
			hmmtagger = (ModelGeneration) getModel(hmmloc);
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
