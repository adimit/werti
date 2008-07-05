package org.werti.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WERTi implements EntryPoint {

	final HTML status = new HTML("Ready.");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button submitURL = new Button("Submit URL");
		submitURL.addClickListener(new ClickListener() {
			public void onClick(Widget w) {
				status.setHTML("Not implemented yet");
			}
		});

		final Button submitTerm = new Button("Query for Term");
		submitTerm.addClickListener(new ClickListener() {
			public void onClick(Widget w) {
				status.setHTML("Not implemented yet");
			}
		});

		final TextBox searchBox = new TextBox();

		final HorizontalPanel statusBar = new HorizontalPanel();
		statusBar.add(status);
		statusBar.setStyleName("statusBar");

		final HorizontalPanel submitButtons = new HorizontalPanel();
		submitButtons.add(submitURL);
		submitButtons.add(submitTerm);

		RootPanel.get("searchBox").add(searchBox);
		RootPanel.get("statusBar").add(statusBar);

		RootPanel.get("submitButtons").add(submitButtons);
	}
}
