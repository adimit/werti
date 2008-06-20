package org.werti.client;

import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WERTiUI extends Composite implements WindowResizeListener {

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
	/**
	 * Content wrapper. Put stuff here.
	 */
	private SimplePanel contentWrapper;

	/**
	 * Constructs the layout and widegts of the application.
	 */
	public WERTiUI() {
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
				String moduleRelativeURL = GWT.getModuleBaseURL() + "uima";
				endpoint.setServiceEntryPoint(moduleRelativeURL);

				AsyncCallback callback = new AsyncCallback() {
					public void onSuccess(Object result) {
						showPage(result);
					}

					public void onFailure(Throwable reason) {
						error(reason.toString());
					}
				};
				wertiService.process(pipeline, tags, callback);
			}
		});
	}

	private VerticalPanel mkUserPanel() {
		VerticalPanel users = new VerticalPanel();

		HorizontalPanel search = new HorizontalPanel();

		HTML l = new HTML("URL to enhance:&nbsp;");

		TextBox urlbox = new TextBox();

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
		
		users.add(s2);
		users.add(enhancements);

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
}
