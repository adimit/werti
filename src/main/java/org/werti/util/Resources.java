package org.werti.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.uima.resource.ResourceInitializationException;

/**
 * A small helper class to help Werti communicate with the outside world.
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public class Resources {
	// The extra 6 bytes per method call don't really matter all that much and we
	// reduce boiler plating for exception throwing in getModel(String).
	public static final ResourceInitializationException
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
	public static final Object getModel(String path)
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
}
