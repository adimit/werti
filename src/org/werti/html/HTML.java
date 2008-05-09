package org.werti.html;
/**
 * This is a transition package. Not intended for further use. Just some basic methods
 * to ease the generation of rudimentary HTML output in the beginning.
 */

public class HTML {
	public static String preamble(String title) {
		return "<html><header><title>" 
			+ title 
			+ "</title></header><body>";
	}

	public static String footer() {
		return "</body></html>";
	}

	public static String element(String e, String p) {
		return element(e,p,"");
	}

	public static String element(String e, String p, String css_name) {
		return "<" + e + " class=\""
			+ css_name
			+ "\"> " + p + "</"+ e +">";
	}

	/**
	 * Print a form where every element is going to be placed next to each other.
	 * Also produces a submit button on the next line;
	 */
	public static String form(String action, String method, Input[] elements) {
		String r = "<form action=\"" + action + "\" method=\"" + method + "\">";
		for (Input e:elements) {
			r += e.toString();
		}
		r +=  new Input("submit","", "");
		r +=  "</form>";
		return r;
	}

	/**
	 * Used to instantiate input objects. Don't call the toString methods outside a 
	 * form if you don't know what you're doing to the html ;-)
	 */
	public static class Input {
		String type, size, name;

		public Input(String type, String name, String size) {
			this.type = type;
			this.name = name;
			this.size = size;
		}

		public String toString() {
			return "<input type=\"" + type 
				+ "\" name =\"" + name 
				+ "\" size =\"" + size
				+ "\"/>";
		}
	}
}
