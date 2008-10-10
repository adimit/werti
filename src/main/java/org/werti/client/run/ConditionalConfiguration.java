package org.werti.client.run;

import java.util.LinkedList;
import java.util.List;

import org.werti.client.util.Tuple;

/**
 * A configuration for the original WERTi capabilities.
 *
 * PoS-tags the input. Enhancement is applied according to PoS Tags.
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public class ConditionalConfiguration implements RunConfiguration {

	private String conditionals;
	private String enhancer;

	/**
	 * Initialize a configuration with parameters.
	 *
	 * This is the preferred constructor. Do not use the other one.
	 *
	 * @param tags A comma-seperated list of tags that are to be targeted.
	 * @param enhancer the name of the GWT module that will be used for enhancement.
	 */
	public ConditionalConfiguration(String conditionals, String enhancer) {
		this.conditionals = conditionals;
		this.enhancer = enhancer;
	}

	/**
	 * A dummy-zero-argument-constructor for GWT to be able to able to
	 * serialize this class.
	 *
	 * @deprecated Do not use this constructor. It is here solely for compatibility
	 * reasons. Please use <code>CategoriesConfiguration(String,String)</code> instead.
	 */
	public ConditionalConfiguration() {
		this.conditionals = "1,2,3";
		this.enhancer = "Colorize";
	}

	public static final long serialVersionUID = 0;

	/**
	 * Returns the preprocessor for this configuration, which needs only a tagger.
	 *
	 * @return the property name of the tagger descriptor file.
	 */
	public String preprocessor() {
		return "pre.conditional";
	}

	/**
	 * Returns the preprocessor for this configuration, depending on the enhancement 
	 * method to be used.
	 *
	 * @return the property name of the enhancer descriptor file to use.
	 */
	public String postprocessor() {
		if (enhancer.equals("Ask")) {
			return "post.ask";
		} else {
			return "post.conditional";
		}
	}

	/**
	 * Configuration data for the preprocessor.
	 *
	 * @return <code>null</code> since the tagger does not currently need configuration.
	 */
	public List<Tuple> preconfig() {
		return null;
	}

	/**
	 * Configuration data for the postprocessor.
	 *
	 * In this case, this is just a Tuple of ("Tags",ListOfTags), where ListOfTags is
	 * comma-separated.
	 *
	 * @return A list of tuples to use for configuring the postprocessor.
	 */
	public List<Tuple> postconfig() {
		final List<Tuple> config = new LinkedList<Tuple>();
		config.add(new Tuple("conditionals", conditionals));
		return config;
	}

	/**
	 * Request the enhancement module.
	 *
	 * @return The Name of the GWT module to use for enhancement.
	 */
	public String enhancer() { return this.enhancer; }
}
