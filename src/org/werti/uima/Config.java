package org.werti.uima;

public class Config {
	private final String desc;
	private final String baseurl;

	public Config() {
		desc = "Bogus";
		baseurl = "bogus";

	}

	public Config(String desc, String baseurl) {
		this.desc = desc;
		this.baseurl = baseurl;
	}

	public String getDescriptor() {
		return desc;
	}

	public String getBaseURL() {
		return baseurl;
	}
}
