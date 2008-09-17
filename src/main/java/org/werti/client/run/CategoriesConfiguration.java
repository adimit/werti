package org.werti.client.run;

import java.util.LinkedList;
import java.util.List;

import org.werti.client.util.Tuple;

/**
 * A configuration for the original WERTi capabilities.
 *
 * PoS-tags the input. Enhancement is applied according to PoS Tags.
 */
public class CategoriesConfiguration implements RunConfiguration {

	private String tags;
	private String enhancer;

	public CategoriesConfiguration(String tags, String enhancer) {
		this.tags = tags;
		this.enhancer = enhancer;
	}

	public CategoriesConfiguration() {
		tags = "pp";
		this.enhancer = "Colorize";
	}

	public static final long serialVersionUID = 0;

	public String preprocessor() {
		return "pre.tagger";
	}

	public String postprocessor() {
		if (enhancer.equals("Ask")) {
			return "post.ask";
		} else {
			return "post.pos";
		}
	}

	public List<Tuple> preconfig() {
		return null;
	}

	public List<Tuple> postconfig() {
		final List<Tuple> config = new LinkedList<Tuple>();
		config.add(new Tuple("tags", tags));
		return config;
	}

	public String enhancer() { return this.enhancer; }
}
