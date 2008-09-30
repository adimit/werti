package org.werti.util;

public class EnhancerUtils {

	// need thos two to supply JS-annotations with IDs.
	public static String get_id(String spanClass, int id) {
		return spanClass + "-" + id;
	}

	// does an array of Strings contain a given String?
	public static boolean arrayContains(String data, String[] sa) {
		for (String s:sa) {
			if (s.equals(data)) return true;
		}
		return false;
	}
}
