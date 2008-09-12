package org.werti.client.run;

import java.io.Serializable;

import java.util.List;

import org.werti.client.util.Tuple;

/** 
 * A generic interface for specifying server-side behaviour.
 *
 * A <code>RunConfiguration</code> knows which descriptors to call to
 * instantiate pre- and postprocessing <code>AnalysisEngines</code>, what the
 * configuration parameters for them are and which GWT module to use on
 * client-side to enhance the content of the processed document.
 */

public interface RunConfiguration extends Serializable {

	/**
	 * Returns a path to an <code>AnalysisEngine</code> descriptor file
	 * under the <code>/desc</code> path that instantiates a suitable preprocessor.
	 *
	 * @return The location of the descriptor file for preprocessing.
	 */
	public String preprocessor();

	/**
	 * Returns a path to an <code>AnalysisEngine</code> descriptor file
	 * under the <code>/desc</code> path that instantiates a suitable postprocessor.
	 *
	 * @return The location of the descriptor file for postprocessing.
	 */
	public String postprocessor();

	/**
	 * Returns a list of name/value pairs representing the preprocessing
	 * AnalysisEngine's configuration parameters.
	 *
	 * @return Preprocessor's configuration parameters.
	 */
	public List<Tuple<String,Object>> preconfig();

	/**
	 * Returns a list of name/value pairs representing the postprocessing
	 * AnalysisEngine's configuration parameters.
	 *
	 * @return Postprocessor's configuration parameters.
	 */
	public List<Tuple<String,Object>> postconfig();

	/**
	 * Denotes the GWT-module (in org.werti.enhancements) that
	 * will be used on client side to enhance the content.
	 *
	 * @return The name of a GWT module (case sensitive!) to use
	 * for enhancement.
	 */
	public String enhancer();
}
