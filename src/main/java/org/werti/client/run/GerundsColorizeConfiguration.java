package org.werti.client.run;

import java.util.List;

import org.werti.client.util.Tuple;

public class GerundsColorizeConfiguration implements RunConfiguration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4207177199810853087L;

	public String enhancer() {
		return "GerundsColorize";
		//return "Colorize";
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
