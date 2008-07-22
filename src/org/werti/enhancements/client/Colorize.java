package org.werti.enhancements.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.dom.client.Element;

import com.google.gwt.user.client.DOM;

/**
 * A basic implementation of receptive presentation tasks.
 *
 * All target items are colored blue.
 *
 * Entry point classes define <code>onModuleLoad()</code>.
 *
 * @author Aleksandar Dimitrov
 * @version 1.0
 */
public class Colorize implements EntryPoint {

	/**
	 * This is the entry point method.
	 *
	 * We go through all WERTi-ids and color them bule with a <tt>&lt;span&gt;</tt>.
	 */
	public void onModuleLoad() {
		Element domSpan;
		int i = 1;
		while ((domSpan = DOM.getElementById(getId(i))) != null) {
			domSpan.setInnerHTML("<span style=\"color: #22F; font-weight:bold\">"
					+ domSpan.getInnerText() + "</span>");
			i++;
		}
	}

	private static String getId(int id) {
		return "WERTi-span-" + id;
	}
}
