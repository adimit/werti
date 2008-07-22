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
 *
 * This is the main Application interface for WERTi. It defines all user controls and makes
 * everything accessible, the user wants to access.
 *
 * It is still in its rudimentary form and should be enhanced. The HTML page corresponding
 * is in <tt>org.werti.public</tt>.
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public class WERTi implements EntryPoint {

	final HTML status = new HTML("Ready.");

	/**
	 * Pops up an error dialog to the user, with a general error message, a general excuse
	 * and a 'more info' option to show details about the error that occured.
	 *
	 * All arguments are sort-of optional, but you should provide at least the first
	 * two.
	 *
	 * @param message An error message to be shown to the user directly
	 * @param exceptionMessage The message the exception generated. This is hidden initially.
	 * @param cause An optional stacktrace.
	 */
	public void showError(String message, String exceptionMessage, String cause) {
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

	/**
	 * This is the main user-interface callback.
	 *
	 * It is sent to the server in order to initialize processing there.
	 * <p><em>On return</em> it will pop up a new window and also show a link to the temporary
	 * file generated on server side.</p>
	 * <p><em>On failure</em> it will pop up an error dialog where the user will be informed
	 * of the cause of the failure.
	 */
	@SuppressWarnings("unchecked")
	private class SearchCallback implements AsyncCallback {
		/**
		 * Pops up an error dialog to inform the user why this happened.
		 *
		 * @param reason An exception we recieved from the server.
		 */
		public void onFailure(Throwable reason) {
			StringBuilder sb = new StringBuilder();
			for (StackTraceElement ste:reason.getStackTrace()) {
				sb.append(ste.toString() + "<br />");
			}
			if (reason instanceof InvocationException) {
				final InvocationException ie = (InvocationException) reason;
				showError("Failed to contact Server! "
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
						+ "This probably means that one the option "
						+ "you chose has some problems currently. " 
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

		/**
		 * Pops up a new window with the result and displays a link to the result in
		 * the <tt>StatusBar</tt>
		 *
		 * @param result The String denoting a URL to the page to be displayed.
		 */
		public void onSuccess(Object result) {
			final String whattoload = "/WERTi" + result.toString();
			status.setHTML("<a href=\"localhost:8080" + whattoload + "\">Result</a>.");
			Window.open(whattoload, "", "");
		}
	}

	/**
	 * This is the entry point method.
	 *
	 * It constructs the interface and sets everything into place.
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

	final ECheckBox dets = new ECheckBox("at", "Determiners");
	final ECheckBox prps = new ECheckBox("pp,in", "Prepositions");

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

	/*
	 * Create the exercises panel and populate it.
	 * NOTE: you will have to edit this when you want to integrate a new
	 * exercise type.
	 */
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

	/*
	 * Go through the interface items and collect user request data.
	 */
	private String[] collectData() {
		StringBuilder sb = new StringBuilder();
		for (HasData o:formData) {
			sb.append(o.getData() + ",");
		}
		// dirty regex hacking. Sure, there are better solutions...
		return sb.toString().replaceAll("^[^\\w]*", "").split("(\\s*,\\s*)+");
	}

	/**
	 * An interface for components that <i>have</i> a <tt>data</tt> field
	 * to store specific keys.
	 */
	private interface HasData {

		/**
		 * Retrieve this element's key.
		 * @return The data this element hosts.
		 */
		public String getData();

		/**
		 * Give this element a key.
		 * @param data The data this element has to host.
		 */
		public void setData(String data);
	}

	/**
	 * A TextBox that implements the HasData interface to
	 * integrate with our other interface components.
	 */
	private class ETextBox extends TextBox implements HasData {

		/**
		 * Construct the box with some data in it.
		 *
		 * This delegates to <tt>TextBox.setText(String)</tt>
		 */
		public ETextBox(String data) {
			super();
			setText(data);
		}

		/**
		 * Construct an empty TextBox.
		 */
		public ETextBox() {
			super();
		}

		/**
		 * Retrieve this element's data.
		 *
		 * This just calls the <tt>TextBox.getText()</tt> method.
		 *
		 * @return The text currently in the TextBox.
		 */
		public String getData() {
			return ((isEnabled())? getText(): "");
		}

		/**
		 * Set this element's data.
		 *
		 * Just delegates to the TextBox and can set its text.
		 * 
		 * @param data The text you want to appear in the text box.
		 */
		public void setData(String data) {
			setText(data);
		}

	}

	/**
	 * A CheckBox that implements the HasData interface to
	 * integrate with our other interface components.
	 */
	private class ECheckBox extends CheckBox implements HasData {
		String data;

		/**
		 * Construct a CheckBox that knows what it's good for.
		 *
		 * @param data The key of this Checkbox.
		 * @param name The caption of this CheckBox.
		 */
		public ECheckBox(String data, String name) {
			super(name);
			this.data = data;
		}

		/**
		 * Retrieve this element's key.
		 * @return The data this element hosts.
		 */
		public String getData() {
			return ((isChecked() && isEnabled())? data: "");
		}

		/**
		 * Give this element a key.
		 * @param data The data this element has to host.
		 */
		public void setData(String data) {
			this.data = data;
		}
	}

	/**
	 * A RadioButton that implements the HasData interface to
	 * integrate with our other interface components.
	 */
	private class ERadioButton extends RadioButton implements HasData {
		String data;

		/**
		 * Construct a RadioButton that knows what it's good for.
		 *
		 * @param data The key of this RadioButton.
		 * @param group The radio group this RadioButton belongs to.
		 * @param name The caption of this RadioButton.
		 */
		public ERadioButton(String data, String group, String name) {
			super(group, name);
			this.data = data;
		}

		/**
		 * Retrieve this element's key.
		 * @return The data this element hosts.
		 */
		public String getData() {
			return ((isChecked() && isEnabled())? data: "");
		}

		/**
		 * Give this element a key.
		 * @param data The data this element has to host.
		 */
		public void setData(String data) {
			this.data = data;
		}
	}
}
