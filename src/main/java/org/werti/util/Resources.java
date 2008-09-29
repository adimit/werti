package org.werti.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import org.apache.uima.resource.ResourceInitializationException;

/**
 * A small helper class to help Werti communicate with the outside world.
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public class Resources {
	private static final Logger log =
		Logger.getLogger(Resources.class);

	// The extra 6 bytes per method call don't really matter all that much and we
	// reduce boiler plating for exception throwing in getModel(String).
	private static final ResourceInitializationException
		noAccess(final String reason, final Exception exception) {
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
	 *
	 * @param path The full path to the model (including file name) based on
	 * the servlet context's root.
	 * @param servlet The servlet context.
	 * @return An object input stream to the requested resource.
	 */
	public static final Object getResource(final String path, final ServletContext servlet) 
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
		if (log.isDebugEnabled()) {
			log.debug("Retrieving from " + mPath.getPath());
		}
		return getResource(mPath);
	}

	/**
	 * Fetch a model from the JVM context.
	 *
	 * @param path The full path to the model (including file name) based on
	 * the JVM's root.
	 * @return An object input stream to the requested resource.
	 */
	public static final Object getResource(final String path)
		throws ResourceInitializationException {
		final URL mPath;
		try { // to find the correct location of the resource
			mPath = new URL(new URL("file://"), path);
		} catch (MalformedURLException murle) {
			final Object[] args = { path };
			throw new ResourceInitializationException
				(ResourceInitializationException.MALFORMED_URL
				 , args
				 , murle);
		}
		if (log.isDebugEnabled()) {
			log.debug("Retrieving from " + mPath.getPath());
		}
		return getResource(mPath);
	}

	/*
	 * This is a super-safe method that shuoldn't leak any dangling references.
	 * Thanks to dmlloyd at ##java.
	 */
	private static final Object getResource(URL path)
		throws ResourceInitializationException {
		try { // to open a connection to the resource
			final InputStream is = path.openStream();
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
						throw noAccess(path.getPath(), ioe);
					}
				}
			} finally {
				try {
					is.close();
				} catch(IOException ioe) {
					throw noAccess(path.getPath(), ioe);
				}
			}
		} catch (IOException ioe) {
			throw noAccess(path.getPath(), ioe);
		} catch (NullPointerException npe) {
			throw noAccess("Couldn't load path: "+path.getPath(), npe);
		}
	}
}
