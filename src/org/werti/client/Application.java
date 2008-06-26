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
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Application extends Composite implements WindowResizeListener {

	public class CRadioButton extends RadioButton implements isUseful {
		private String canonicalValue;

		public String getCanonicalValue() { return canonicalValue; }

		public CRadioButton(String group, String text, String cv) {
			super(group, text);
			this.canonicalValue = cv;
		}
	}

	public class CCheckBox extends CheckBox implements isUseful {
		private String canonicalValue;

		public String getCanonicalValue() { return canonicalValue; }

		public CCheckBox(String text, String cv) {
			super(text);
			this.canonicalValue = cv;
		}
	}

	public interface isUseful {
		public boolean isChecked();
		public String getCanonicalValue();
	}

	private static final String[] enhancementMethods = { "clr", "ask", "fib" };
	private static final String[] enhancementNames = { "Colorize"
			, "Click on the right word"
			, "Fill in the blanks" };
	private String defaultEnhancement = "clr";

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
	 * Constructs the layout and widegts of the application.
	 */
	public Application() {
		FlowPanel layout = new FlowPanel();
		initWidget(layout);

		layout.setWidth("100%");

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
		displayResult("ERROR: "+huh);
	}

	@SuppressWarnings("unchecked")
	private void mkSubmitButton() {
		submitButton = new Button("Enhance");
		submitButton.setStyleName(STYLE_PREFIX + "-submit");
		submitButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				WERTiServiceAsync wertiService = (WERTiServiceAsync) GWT.create(WERTiService.class);
				ServiceDefTarget endpoint = (ServiceDefTarget) wertiService;
				String moduleRelativeURL = GWT.getHostPageBaseURL() + "/org.werti.WERTi/WERTiService";
				endpoint.setServiceEntryPoint(moduleRelativeURL);
				AsyncCallback callback = new AsyncCallback() {
					public void onSuccess(Object result) {
						if (result == null) {
							error("Error occured during processing.");
						}
						final String file = "http://localhost/"+result.toString();
						displayResult("<a href=\"" +file +"\">Result</a>.");
						Window.open(file, "", "");
					}
					public void onFailure(Throwable reason) {
						error(reason.toString());
					}
				};
				statusLabel.setText("Processing... Enhancement: "+defaultEnhancement);
				getValues();
				wertiService.process(pipeline, tags, url, defaultEnhancement, callback);
			}
		});
	}

	private String url;

	private void getValues() {
		url = urlbox.getText();
		getTags();
		getEnhancements();
	}
	
	private void getTags() {
		String r = "";
		for (int ii = 0; ii < selectTags.getWidgetCount(); ii++){
			if (selectTags.getWidget(ii) instanceof isUseful) {
				isUseful w = (isUseful) selectTags.getWidget(ii);
				if (w.isChecked()) {
					r += w.getCanonicalValue() + ", ";
				}
			}
		}
		tags = r.split("\\s*,\\s*");
	}

	private void getEnhancements() {
		for (int ii = 0; ii < selectEnhancements.getWidgetCount(); ii++){
			if (selectEnhancements.getWidget(ii) instanceof isUseful) {
				isUseful w = (isUseful) selectEnhancements.getWidget(ii);
				if (w.isChecked()) {
					defaultEnhancement = w.getCanonicalValue();
					return;
				}
			}
		}
	}

	private TextBox urlbox;

	private void displayResult(String s) {
		if (statusLabel == null) {
			statusLabel = new HTML();
		}
		statusLabel.setHTML(s);
	}

	private VerticalPanel selectEnhancements;

	private HorizontalPanel selectTags;

	private VerticalPanel mkUserPanel() {
		VerticalPanel users = new VerticalPanel();

		HorizontalPanel search = new HorizontalPanel();

		HTML l = new HTML("URL to enhance:&nbsp;");

		urlbox = new TextBox();
		urlbox.setWidth("400px");

		mkSubmitButton();

		selectEnhancements = new VerticalPanel();

		assert true: enhancementMethods.length == enhancementNames.length;
		for (int ii = 0; ii < enhancementNames.length; ii++){
			CRadioButton e = new CRadioButton("selectEnhancements"
					, enhancementNames[ii]
					, enhancementMethods[ii]);
			if (enhancementMethods[ii].equals(defaultEnhancement)) {
				e.setChecked(true);
			}
			selectEnhancements.add(e);
		}

		search.add(l);
		search.add(urlbox);

		VerticalPanel s2 = new VerticalPanel();
		
		selectTags = new HorizontalPanel();

		assert true: posTags.length == posNames.length;
		for (int ii = 0; ii < posTags.length; ii++){
			CCheckBox c = new CCheckBox(posNames[ii], posTags[ii]);
			if (posTags[ii].equals(defaultTag)) {
				c.setChecked(true);
			}
			selectTags.add(c);
		}


		s2.add(search);
		
		statusLabel = new HTML("Waiting...");

		users.add(s2);
		users.add(selectTags);
		users.add(selectEnhancements);
		users.add(statusLabel);

		users.add(submitButton);

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
}
