package org.werti.uima;

public class Config {
	private final String desc;

	public Config() {
		desc = "Bogus";
	}

	public Config(String desc) {
		this.desc = desc;
	}

	public String getDescriptor() {
		return desc;
	}
}
