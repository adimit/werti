package org.werti.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Enhancements implements EntryPoint {
	public static final String PREFIX = "WERTi-span-";
	Element e;
	RootPanel trp;
	public void onModuleLoad() {
		for (int i = 0; ; i++) {
			e = DOM.getElementById(PREFIX+i);
			trp = RootPanel.get(PREFIX+i);
			if (e == null) { break; }
			final String word = e.getNodeValue();
			e.setNodeValue("");
			TextBox tb = new TextBox();
			tb.setWidth((10*word.length())+"pt");
		}
	}

	private class BlankListener implements KeyboardListener {
		TextBox next;
		String word;
		String id;
		public BlankListener(String word, String id) {
			this.id = id;
			this.word = word;
		}

		public void setNext(TextBox next) {
			this.next = next;
		}
		
		public void onKeyDown(Widget w, char key, int i) {
		}

		public void onKeyPress(Widget w, char key, int i) {
			if (w instanceof TextBox) {
				TextBox tb = (TextBox) w;
				if (tb.getText().equals(word)) {
					w.setVisible(false);
					RootPanel.get(id).add(new HTML("<font color=\"#00FF00\">"+word+"</font>"));
					if (next != null) {
						next.setFocus(true);
					}
				} 
			}
		}

		public void onKeyUp(Widget w, char key, int i) {
		}
	}
}
