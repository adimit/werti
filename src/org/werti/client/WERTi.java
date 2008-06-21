package org.werti.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.LinkElement;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WERTi extends Composite implements WindowResizeListener, EntryPoint {

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
		// WERTiConstants constants = (WERTiConstants) GWT.create(WERTiConstants.class);

		// Create the User Interface
		app = new WERTi();

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


	private static final String[] enhancementMethods = { "clr", "ask", "fib" };
	private static final String[] enhancementNames = { "Colorize"
			, "Click on the right word"
			, "Fill in the blanks" };
	private static final String defaultEnhancement = "clr";

	private static final String[] posTags = { "NN", "IN", "DT" };
	private static final String[] posNames = { "Nouns", "Prepositions", "Determiners" };
	private static final String defaultTag = "IN";

	private String[] pipeline = { "smp", "hmm", "hil" };
	private String[] tags = { "NN" };

	private static final String STYLE_PREFIX = "WERTi";
	int windowWidth;

	private VerticalPanel topPanel;
	private TabPanel contentPanel;
	private Button submitButton;
	private HTML statusLabel;
	/**
	 * Content wrapper. Put stuff here.
	 */
	private SimplePanel contentWrapper;

	/**
	 * Constructs the layout and widegts of the application.
	 */
	public WERTi() {
		FlowPanel layout = new FlowPanel();
		initWidget(layout);

		mkTopPanel();
		layout.add(topPanel);

		mkContentPanel();
		layout.add(contentPanel);

	}

	private void mkContentPanel() {
		contentPanel = new TabPanel();
		contentPanel.setStyleName(STYLE_PREFIX + "-content");

		VerticalPanel users = mkUserPanel();
		VerticalPanel developers = mkDeveloperPanel();

		contentPanel.add(users, "Users");
		contentPanel.add(developers, "Developers");

		contentPanel.selectTab(0);
	}

	private void error(String huh) {
	
	}

	private void showPage(Object page) {
	
	}

	@SuppressWarnings("unchecked")
	private void mkSubmitButton() {
		submitButton = new Button("Enhance");
		submitButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				WERTiServiceAsync wertiService = (WERTiServiceAsync) GWT.create(WERTiService.class);
				ServiceDefTarget endpoint = (ServiceDefTarget) wertiService;
				String moduleRelativeURL = GWT.getModuleBaseURL() + "/org.werti.WERTi/WERTiService";
				endpoint.setServiceEntryPoint(moduleRelativeURL);

				AsyncCallback callback = new AsyncCallback() {
					public void onSuccess(Object result) {
						showPage(result);
						final String file = result.toString();
						displayResult("<a href=\"http://localhost/"
								+file
								+"\">Result</a>");
					}

					public void onFailure(Throwable reason) {
						error(reason.toString());
					}
				};
				statusLabel.setText("Processing...");
				getValues();
				wertiService.process(pipeline, tags, url, enhance, callback);
			}
		});
	}

	private String enhance = "clr";
	private String url;

	private void getValues() {
		url = urlbox.getText();
	}

	private TextBox urlbox;

	private void displayResult(String s) {
		if (statusLabel == null) {
			statusLabel = new HTML();
		}
		statusLabel.setHTML(s);
	}

	private VerticalPanel mkUserPanel() {
		VerticalPanel users = new VerticalPanel();

		HorizontalPanel search = new HorizontalPanel();

		HTML l = new HTML("URL to enhance:&nbsp;");

		urlbox = new TextBox();

		mkSubmitButton();

		VerticalPanel enhancements = new VerticalPanel();
		HTML eHeading = new HTML("<h4>Input Enhancement Method:</h4>");
		enhancements.add(eHeading);

		assert true: enhancementMethods.length == enhancementNames.length;
		for (int ii = 0; ii < enhancementNames.length; ii++){
			RadioButton e = new RadioButton("Enhancements", enhancementNames[ii]);
			if (enhancementMethods[ii].equals(defaultEnhancement)) {
				e.setChecked(true);
			}
			enhancements.add(e);
		}

		search.add(l);
		search.add(urlbox);
		search.add(submitButton);

		VerticalPanel s2 = new VerticalPanel();
		
		VerticalPanel tags = new VerticalPanel();

		assert true: posTags.length == posNames.length;
		for (int ii = 0; ii < posTags.length; ii++){
			CheckBox c = new CheckBox(posNames[ii]);
			if (posTags[ii].equals(defaultTag)) {
				c.setChecked(true);
			}
			tags.add(c);
		}

		search.add(tags);

		s2.add(search);
		
		statusLabel = new HTML("Waiting...");

		users.add(s2);
		users.add(enhancements);
		users.add(statusLabel);

		return users;
	}

	private VerticalPanel mkDeveloperPanel() {
		VerticalPanel developers = new VerticalPanel();
		return developers;
	}

	/**
	 * Create top panel containing account status
	 */
	private void mkTopPanel() {
		topPanel = new VerticalPanel();
		topPanel.setStyleName(STYLE_PREFIX + "-top"); 
		HTML title = new HTML("<h1>Welcome to WERTi</h1>");
		topPanel.add(title);
	}

	@Override protected void onLoad() {
		super.onLoad();
		Window.addWindowResizeListener(this);
		onWindowResized(Window.getClientWidth(), Window.getClientHeight());
	}

	@Override protected void onUnload() {
		super.onUnload();
		Window.removeWindowResizeListener(this);
		windowWidth = -1;
	}

	public void onWindowResized(int width, int height) {
		if (width == windowWidth) {
			return;
		}
		windowWidth = width;
	}

	/// Utility Methods \\\

	/**
	 * Convenience method.
	 *
	 * Adds the {@link Widget} or an &amp;nbsp;-entity if the parameter is null.
	 */
	public void addWidget(Widget content) {
		if (content == null) {
			contentWrapper.setWidget(new HTML("&nbsp;"));
		} else {
			contentWrapper.setWidget(content);
		}
	}

	/* Nasty stuff follows ahead. Please keep at end of file ~adimit */

	private static native HeadElement getHeadElement() /*-{
							     return $doc.getElementsByTagName("head")[0];
							     }-*/;
}
