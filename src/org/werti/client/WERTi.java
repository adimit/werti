package org.werti.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.LinkElement;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WERTi implements EntryPoint {

	private Label styleTester;

	private Composite app;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// The root panel.
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.addStyleName("widePanel");
		mainPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

		// Create a widget to test when style sheets are loaded
		styleTester = new HTML("<div class=\"topLeftInner\"></div>");
		styleTester.setStyleName("gwt-DecoratorPanel");
		styleTester.getElement().getStyle().setProperty("position", "absolute");
		styleTester.getElement().getStyle().setProperty("visibility", "hidden");
		styleTester.getElement().getStyle().setProperty("display", "inline");
		styleTester.getElement().getStyle().setPropertyPx("padding", 0);
		styleTester.getElement().getStyle().setPropertyPx("top", 0);
		styleTester.getElement().getStyle().setPropertyPx("left", 0);
		RootPanel.get().add(styleTester);

		// Create the constants
		WERTiConstants constants = (WERTiConstants) GWT.create(WERTiConstants.class);

		// Create the User Interface
		app = new WERTiUI();

                /*
		 *setupTitlePanel(constants);
		 *setupMainLinks(constants);
		 *setupOptionsPanel();
		 *setupMainMenu(constants);
                 */
		RootPanel.get().add(app);


		// Create the dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Welcome to GWT!");
		dialogBox.setAnimationEnabled(true);
		Button closeButton = new Button("close");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setWidth("100%");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialogVPanel.add(closeButton);

		closeButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				dialogBox.hide();
			}
		});

		// Set the contents of the Widget
		dialogBox.setWidget(dialogVPanel);

		RootPanel.get().add(mainPanel);
	}

	/**
	 * Create a new {@link LinkElement} that links to a style sheet and append it
	 * to the head element.
	 * 
	 * @param href the path to the style sheet
	 */
	private static void loadStyleSheet(String href) {
		LinkElement linkElem = Document.get().createLinkElement();
		linkElem.setRel("stylesheet");
		linkElem.setType("text/css");
		linkElem.setHref(href);
		getHeadElement().appendChild(linkElem);
	}

	/* Nasty stuff follows ahead. Please keep at end of file ~adimit */

	private static native HeadElement getHeadElement() /*-{
							     return $doc.getElementsByTagName("head")[0];
							     }-*/;

}
