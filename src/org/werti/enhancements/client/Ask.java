package org.werti.enhancements.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.dom.client.Element;

import com.google.gwt.user.client.DOM;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

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
			domSpan.setInnerHTML("");
			i++;
		}
	}

	private class AskListener implements ClickListener {
		private final String target;

		public AskListener(final String s) {
			this.target = s;
		}
		public void onClick(Widget w) {
			if (w instanceof HTML) {
				final HTML html = (HTML) w;
				if (html.getText().equals(target)) {
					w.setStyleName("WERTiAskLabelWin");
					addWinCount();
				} else {
					w.setStyleName("WERTiAskLabelFail");
					addFailCount();
				}
			}
		}
	}

	public void addFailCount() {
	
	}

	public void addWinCount() {
	
	}

	private static String getId(int id) {
		return "WERTi-span-" + id;
	}
}
