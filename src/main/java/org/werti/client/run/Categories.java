package org.werti.client.run;

import java.io.Serializable;

import java.util.LinkedList;
import java.util.List;

import org.werti.client.util.Tuple;

/**
 * A configuration for the original WERTi capabilities.
 *
 * PoS-tags the input. Enhancement is applied according to PoS Tags.
 */
public class Categories implements RunConfiguration, Serializable {

	private String[] tags;
	private String enhancer;

	public Categories(String[] tags, String enhancer) {
		this.tags = tags;
		this.enhancer = enhancer;
	}

	public Categories() {
		this.tags = new String[1];
		tags[0] = "pp";
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

	public List<Tuple<String,Object>> preconfig() {
		return null;
	}

	public List<Tuple<String,Object>> postconfig() {
		final List<Tuple<String,Object>> config = new LinkedList<Tuple<String,Object>>();
		config.add(new Tuple<String,Object>("tags", tags));
		return config;
	}

	public String enhancer() { return this.enhancer; }
}
