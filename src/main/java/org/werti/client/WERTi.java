package org.werti.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.Window;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.werti.client.tasks.Categories;
import org.werti.client.tasks.Task;

import org.werti.client.ui.ETextBox;
import org.werti.client.ui.TaskPanel;

public class WERTi implements EntryPoint {

	private final HTML status = new HTML("Ready");

	private final ETextBox url = new ETextBox();
	private final Button submit = new Button("Submit");

	private final TaskPanel taskPanel = new TaskPanel();

	@SuppressWarnings("unchecked")
	public void onModuleLoad() {
		final VerticalPanel form = new VerticalPanel();

		final Task gc = new Categories();
		taskPanel.add(gc);
		taskPanel.selectTab(0);

		form.add(taskPanel);

		submit.addClickListener(new ClickListener() {
			public void onClick(Widget w) {
				final WERTiServiceAsync service =
					(WERTiServiceAsync) GWT.create(WERTiService.class);
				final ServiceDefTarget target = 
					(ServiceDefTarget) service;
				target.setServiceEntryPoint
					(GWT.getModuleBaseURL()+"/UIMAProcessor");
				final AsyncCallback callback = new SearchCallback();
				//try {
					service.process
						(taskPanel.currentTask().configure()
						 , url.getText()
						 , callback);
				/*} catch(Throwable t) {
					status.setHTML("ClickListener caught error.");
					showError(t);
				}*/
			}
		});

		final HorizontalPanel location = new HorizontalPanel();
		location.setStyleName("WERTiLocationBar");

		location.add(new Label("Web Site: "));
		location.add(url);
		location.add(submit);
		
		form.add(location);

		RootPanel.get("exercises").add(form);
		RootPanel.get("status").add(status);
	}

	/**
	 * Pops up an error dialog to the user, with a general error message, a general excuse
	 * and a 'more info' option to show details about the error that occured.
	 *
	 * @param t The error that we received from ServerSide
	 */
	public void showError(Throwable t) {
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);

		dialogBox.setText("We encountered an Error!");

		final String reason;
		if (t instanceof InvocationException) {
			reason = "Failed to contact Server!";
		} else if (t instanceof ProcessingException) {
			reason = "Failed Processing of <a href=\""+ url.getText() +"\">"
					+ "the requested page.</a><br />. "
					+ "Maybe you'd like to try another?";
		} else if (t instanceof InitializationException) {
			reason = "Failed to initialize server-side context. "
					+ "This probably means that one the option "
					+ "you chose has some problems currently. "
					+ "Maybe you could try a different option?";
		} else if (t instanceof URLException) {
			reason = "The URL you've entered seems invalid. Try another.";
		} else {
			reason = "Uknown error occured";
		}

		final StringBuilder sb = new StringBuilder();
		sb.append("<pre><code>");
		for (StackTraceElement ste:t.getStackTrace()) {
			sb.append(ste.toString() + "<br />");
		}
		sb.append("</code></pre>");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setWidth("100%");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

		final HTML explanation = new HTML(reason);

		final DisclosurePanel moreInfo = new DisclosurePanel("More info:");
		moreInfo.setAnimationEnabled(true);
		final VerticalPanel moreInfos = new VerticalPanel();
		final HTML exmes = new HTML(
				"The message of the exception thrown:<br />" +t.getMessage());
		final HTML stacktrace = new HTML(sb.toString());

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
			status.setHTML("Callback caught error");
			showError(reason);
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
}
