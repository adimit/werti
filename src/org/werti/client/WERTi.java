package org.werti.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.Window;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosureEvent;
import com.google.gwt.user.client.ui.DisclosureHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WERTi implements EntryPoint {

	final HTML status = new HTML("Ready.");

	private void showError(String message, String exceptionMessage, String cause) {
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("We encountered an Error!");
		dialogBox.setAnimationEnabled(true);

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setWidth("100%");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

		final HTML explanation = new HTML(message);

		final DisclosurePanel moreInfo = new DisclosurePanel("More info:");
		moreInfo.setAnimationEnabled(true);
		final VerticalPanel moreInfos = new VerticalPanel();
		final HTML exmes = new HTML(
				"The message of the exception thrown:<br />" +exceptionMessage);
		final HTML stacktrace = new HTML(cause);
		
		moreInfos.add(exmes);
		moreInfos.add(stacktrace);
		moreInfo.add(moreInfos);

		final Button closeButton = new Button("close");
		closeButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				dialogBox.hide();
			}
		});

		final HTML excuse = new HTML("<p>We're sorry this happened. "
				+ "The error has been logged on server-side. However, if you "
				+ "feel this really shouldn't have happened, please feel free "
				+ "to <a href=\"mailto:admtrov@sfs.uni-tuebingen.de\">"
				+ "drop the administrator a line</a> so we can fix the problem "
				+ "sooner.");

		dialogVPanel.add(explanation);
		dialogVPanel.add(moreInfo);
		dialogVPanel.add(excuse);
		dialogVPanel.add(closeButton);
		dialogVPanel.setStyleName("text");

		// Set the contents of the Widget
		dialogBox.setWidget(dialogVPanel);

		dialogBox.setWidth("404px");
		dialogBox.center();
		dialogBox.show();
	}

	@SuppressWarnings("unchecked")
	private class SearchCallback implements AsyncCallback {
		public void onFailure(Throwable reason) {
			StringBuilder sb = new StringBuilder();
			for (StackTraceElement ste:reason.getStackTrace()) {
				sb.append(ste.toString() + "<br />");
			}
			if (reason instanceof InvocationException) {
				final InvocationException ie = (InvocationException) reason;
				showError("Failed to contact Server!"
						, ie.getMessage()
						, sb.toString());
			} else if (reason instanceof ProcessingException) {
				final ProcessingException pe = (ProcessingException) reason;
				showError("Failed Processing of <a href=\""
						+ searchBox.getText() + "\">"
						+ "the requested page.</a>"
						+ "<br />. "
						+ "Sorry. Maybe you'd like to try another?"
						, pe.getMessage()
						, sb.toString());
			} else if (reason instanceof InitializationException) {
				final InitializationException ie = 
					(InitializationException) reason;
				showError("Failed to initialize server-side context. "
						+ "This probably means that one the option"
						+ "you chose has some problems currently." 
						+ "Maybe you could try a different option?"
						, ie.getMessage()
						, sb.toString());
			} else if (reason instanceof URLException) {
				final URLException murle = 
					(URLException) reason;
				showError("The URL you've entered seems invalid. "
						+ "Try another."
						, murle.getMessage()
						, sb.toString());
			}
		}

		public void onSuccess(Object result) {
			final String whattoload = "/WERTi" + result.toString();
			status.setHTML("<a href=\"localhost:8080" + whattoload + "\">Result</a>.");
			Window.open(whattoload, "", "");
		}
	}

	/**
	 * This is the entry point method.
	 */
	@SuppressWarnings("unchecked")
	public void onModuleLoad() {
		final Button submitURL = new Button("Submit URL");
		submitURL.addClickListener(new ClickListener() {
			public void onClick(Widget w) {
				final String[] data = collectData();
				final String method = data[0];
				final String lang = data[1];
				final String[] tags = new String[data.length-2];
				for (int n = 2; n < data.length; n++) {
					tags[n-2] = data[n];
				}
				final WERTiServiceAsync service = 
			(WERTiServiceAsync) GWT.create(WERTiService.class);
		final ServiceDefTarget target = (ServiceDefTarget) service;
		target.setServiceEntryPoint(GWT.getModuleBaseURL()+"/UIMAProcessor");
		AsyncCallback callback = new SearchCallback();
		try {
			service.process(method, lang, tags, searchBox.getText(), callback);
		} catch (InvocationException ie) {
			showError("Failed to contact Server!"
				, ie.getMessage()
				, ie.toString());
		} catch (ProcessingException pe) {
			showError("Failed Processing of <a href=\""
					+ searchBox.getText() + "\">"
					+ "the requested page.</a>"
					+ "<br />. "
					+ "Sorry. Maybe you'd like to try another?"
					, pe.getMessage()
					, pe.toString());
		} catch (InitializationException ie) {
			showError("Failed to initialize server-side context. "
					+ "This probably means that one the option"
					+ "you chose has some problems currently." 
					+ "Maybe you could try a different option?"
					, ie.getMessage()
					, ie.toString()
				 );
		} catch (URLException murle) {
			showError("The URL you've entered seems invalid. "
					+ "Try another."
					, murle.getMessage()
					, murle.toString()
				 );
		}
			}
		});

		final Button submitTerm = new Button("Query for Term");
		submitTerm.addClickListener(new ClickListener() {
			public void onClick(Widget w) {
				status.setHTML("Not implemented yet");
			}
		});


		final HorizontalPanel statusBar = new HorizontalPanel();
		statusBar.add(status);
		statusBar.setStyleName("statusBar");

		final HorizontalPanel submitButtons = new HorizontalPanel();
		submitButtons.add(submitURL);
		submitButtons.add(submitTerm);

		createExercises();

		HorizontalPanel languages = new HorizontalPanel();
		en.setChecked(true);
		de.setEnabled(false);
		languages.add(en);
		languages.add(de);

		RootPanel.get("Exercises").add(exercises);

		RootPanel.get("languageChoice").add(languages);

		RootPanel.get("searchBox").add(searchBox);
		RootPanel.get("statusBar").add(statusBar);

		RootPanel.get("submitButtons").add(submitButtons);
	}

	final TextBox searchBox = new TextBox();

	final ECheckBox dets = new ECheckBox("DT", "Determiners");
	final ECheckBox prps = new ECheckBox("PP", "Prepositions");

	final ERadioButton clr = new ERadioButton("Colorize", "methods", "Color Enhancement");
	final ERadioButton ask = new ERadioButton("Ask", "methods", "Guess it!");
	final ERadioButton fib = new ERadioButton("Cloze", "methods", "Cloze Test");

	final ERadioButton de = new ERadioButton("de", "langs", "German");
	final ERadioButton en = new ERadioButton("en", "langs", "English");

	final TabPanel exercises = new TabPanel();
	final ETextBox moreCats = new ETextBox();

	/*
	 * The order of the array contents is important. This is surely suboptimal.
	 * We'll have to change it, once I write some RadioGroup or similar.
	 * For now the Radio Button stuff needs to be in front
	 */
	final HasData[] formData = { clr, ask, fib, en, de, dets, prps, moreCats };

	private void createExercises() {
		dets.setChecked(true);
		clr.setChecked(true);

		VerticalPanel gCats = new VerticalPanel();

		DisclosurePanel advancedCats = new DisclosurePanel("Advanced...");
		advancedCats.setAnimationEnabled(true);
		VerticalPanel customCats = new VerticalPanel();


		customCats.add(new Label("Custom PoS Tags: [ .split(\\s*,\\s*) ]"));
		customCats.add(moreCats);

		advancedCats.setContent(customCats);

		advancedCats.addEventHandler(new DisclosureHandler() {
			public void onClose(DisclosureEvent e) {
				toggleCats(true);
			}

			public void onOpen(DisclosureEvent e) {
				toggleCats(false);
			}

			private void toggleCats(boolean to) {
				dets.setEnabled(to);
				prps.setEnabled(to);
				moreCats.setEnabled(!to);
			}
		});

		gCats.add(dets);
		gCats.add(prps);

		gCats.add(advancedCats);

		gCats.add(clr);
		gCats.add(ask);
		gCats.add(fib);

		VerticalPanel tUdst = new VerticalPanel();

		Label noop = new Label("This functionality is not implemented yet.");

		tUdst.add(noop);

		exercises.add(gCats, "Grammatical Categories");
		exercises.add(tUdst, "Textual Understanding");

		exercises.selectTab(0);
	}

	private String[] collectData() {
		StringBuilder sb = new StringBuilder();
		for (HasData o:formData) {
			sb.append(o.getData() + ",");
		}
		// dirty regex hacking. Sure, there are better solutions...
		return sb.toString().replaceAll("^[^\\w]*", "").split("(\\s*,\\s*)+");
	}

	private interface HasData {
		public String getData();
		public void setData(String data);
	}

	private class ETextBox extends TextBox implements HasData {

		public ETextBox(String data) {
			super();
			setText(data);
		}

		public ETextBox() {
			super();
		}

		public String getData() {
			return ((isEnabled())? getText(): "");
		}

		public void setData(String data) {
			setText(data);
		}

	}

	private class ECheckBox extends CheckBox implements HasData {
		String data;

		public ECheckBox(String data, String name) {
			super(name);
			this.data = data;
		}

		public String getData() {
			return ((isChecked() && isEnabled())? data: "");
		}

		public void setData(String data) {
			this.data = data;
		}
	}

	private class ERadioButton extends RadioButton implements HasData {
		String data;

		public ERadioButton(String data, String group, String name) {
			super(group, name);
			this.data = data;
		}

		public String getData() {
			return ((isChecked() && isEnabled())? data: "");
		}

		public void setData(String data) {
			this.data = data;
		}
	}
}
