package org.werti.client.run;

import java.util.List;

import org.werti.client.util.Tuple;

/**
 * A {@link RunConfiguration} for colorizing gerunds and to-infinitives.
 * @author no
 *
 */
public class GerundsConfiguration implements RunConfiguration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7296681895682846753L;
	private String gwtEnhancer;

	
	public GerundsConfiguration(String gwtEnhancer) {
		this.gwtEnhancer = gwtEnhancer;
	}
	
	/**
	 * A dummy-zero-argument-constructor for GWT to be able to able to
	 * serialize this class.
	 *
	 * @deprecated Do not use this constructor. It is here solely for compatibility
	 * reasons.
	 */	
	public GerundsConfiguration() {
		this("GerundsColorize");
	}
	
	public String enhancer() {
		return gwtEnhancer;	
	}

	public List<Tuple> postconfig() {
		return null;
	}

	public String postprocessor() {
		return "post.gerunds";
	}

	public List<Tuple> preconfig() {
		return null;
	}

	public String preprocessor() {
		return "pre.gerunds";
	}

}
