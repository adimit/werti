package org.werti.enhancements.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.dom.client.Element;

import com.google.gwt.user.client.DOM;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ask implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		Element domSpan;
		int i = 1;
		while ((domSpan = DOM.getElementById(getId(i))) != null) {
			final String text = domSpan.getInnerText();
			final String a_start = 
				"<a href=\"javascript:void(null)\" style=\"color:black\" "
				+ "onclick=\"{this.style.color = '"
				+ ((domSpan.getAttribute("hit").equals("1"))? "green" : "red")
				+ "'; this.style.fontWeight = 'bold';}\">";
			domSpan.setInnerHTML(a_start + text + "</a>");
			i++;
		}
	}

	private static String getId(int id) {
		return "WERTi-span-" + id;
	}
}
