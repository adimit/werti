package org.werti.enhancements.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.dom.client.Element;

import com.google.gwt.user.client.DOM;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Cloze implements EntryPoint {

	/**
	 * Inform the user when something didn't work out the way we expected to.
	 *
	 * GWT error handling needs a bit more work here, but I think it's OK for now.
	 * There shouldn't be too many bad things happening anyway
	 */
	public void showError(String s) {
		final DialogBox db = new DialogBox();
		final VerticalPanel vp = new VerticalPanel();
		final Label l = new Label(s);
		final Button b = new Button("OK");
		b.addClickListener(new ClickListener() {
			public void onClick(Widget w) {
				db.hide();
			}
		});

		vp.add(l);
		vp.add(b);

		db.setTitle("Results");
		db.setAnimationEnabled(true);
		db.setWidget(vp);

		db.center();
		db.show();
	}

	/**
	 * This is the entry point method.
	 *
	 * We make a linked list out of the text boxes, so we can forward focus.
	 */
	public void onModuleLoad() {
		Element domSpan;
		ClozeItem tb0;
		int i = 1;
		tb0 = new ClozeItem(RootPanel.get(getId(i)));
		while ((domSpan = DOM.getElementById(getId(i))) != null) {
			tb0.setTarget(domSpan.getInnerText());
			domSpan.setInnerText("");
			tb0.setNext(new ClozeItem(RootPanel.get(getId(i++))));
			tb0 = tb0.getNext();
		}
	}

	private static String getId(int id) {
		return "WERTi-span-" + id;
	}
}
