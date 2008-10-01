package org.werti.client.run;

import java.util.List;

import org.werti.client.ui.ERadioButton;
import org.werti.client.util.Tuple;

/**
 * A {@link RunConfiguration} for colorizing gerunds and to-infinitives.
 * @author no
 *
 */
public class GerundsConfiguration implements RunConfiguration {

	private static final String[][] radios2enhancers = new String[][]{
		{"Colorize", "GerundsColorize"},
		{"Ask", ""},
		{"Cloze", "GerundsCloze"}
	};
	
	private boolean showAllKnownIngforms;
	private String enhancer;

	private static final long serialVersionUID = 4207177199810853087L;
	
	public GerundsConfiguration(boolean showAllKnownIngforms, ERadioButton chosenRadio) {
		this.showAllKnownIngforms = showAllKnownIngforms;
		for (String[] entry : radios2enhancers) {
			if (entry[0].equals(chosenRadio.getData())) {
				this.enhancer = entry[1];
				break;
			}
		}
	}
	
	/*
	public GerundsConfiguration() {
		this(false, "GerundsColorize");
	}
	*/

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
