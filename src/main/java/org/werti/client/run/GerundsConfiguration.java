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
	private boolean showAllKnownIngforms;
	private String enhancer;

	
	public GerundsConfiguration(boolean showAllKnownIngforms, String enhancer) {
		this.showAllKnownIngforms = showAllKnownIngforms;
		this.enhancer = enhancer;
	}
	
	private GerundsConfiguration() {
		this(false, "GerundsColorize");
	}
	
	public String enhancer() {
		return enhancer;	
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
