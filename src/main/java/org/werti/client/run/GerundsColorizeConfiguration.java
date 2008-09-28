package org.werti.client.run;

import java.util.List;

import org.werti.client.util.Tuple;

/**
 * A {@link RunConfiguration} for colorizing gerunds and to-infinitives.
 * @author no
 *
 */
public class GerundsColorizeConfiguration implements RunConfiguration {
	
	private boolean showAllKnownIngforms;

	private static final long serialVersionUID = 4207177199810853087L;
	
	public GerundsColorizeConfiguration(boolean showAllKnownIngforms) {
		super();
		this.showAllKnownIngforms = showAllKnownIngforms;
	}
	
	public GerundsColorizeConfiguration() {
		this(false);
	}

	public String enhancer() {
		if (showAllKnownIngforms) {
			return "GerundsColorizeAll";
		} else {
			return "GerundsColorize";
		}	
	}

	public List<Tuple> postconfig() {
		return null;
	}

	public String postprocessor() {
		return "post.gerunds.colorize";
	}

	public List<Tuple> preconfig() {
		return null;
	}

	public String preprocessor() {
		return "pre.gerunds";
	}

}
