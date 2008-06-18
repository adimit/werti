package org.werti.client;

import com.google.gwt.user.client.WindowResizeListener;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WERTiUI extends Composite implements WindowResizeListener {

	private static final String STYLE_PREFIX = "WERTi";
	int windowWidth;

	VerticalPanel topPanel;

	/**
	 * Content wrapper. Put stuff here.
	 */
	private SimplePanel contentWrapper;

	/**
	 * Constructs the layout and widegts of the application.
	 */
	public WERTiUI() {
		mkTopPanel();
	}

	/**
	 * Create top panel containing account status
	 */
	private void mkTopPanel() {
		topPanel = new VerticalPanel();
		topPanel.setStyleName(STYLE_PREFIX + "-top"); 
		Label foo = "You are not logged in.";
		topPanel.add(foo);
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

	@Override public void onWindowResized(int width, int height) {
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
