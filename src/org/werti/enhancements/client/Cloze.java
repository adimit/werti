package org.werti.enhancements.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.dom.client.Element;

import com.google.gwt.user.client.DOM;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Cloze implements EntryPoint {

	private class ClozeListener implements KeyboardListener, ClickListener, FocusListener {
		final String gottagetit;
		final Element elem;
		final TextBox current, nextBox;

		public ClozeListener(Element elem, TextBox nextBox, TextBox current) {
			this.gottagetit = elem.getInnerText();
			elem.setInnerText("");
			this.elem = elem;
			this.current = current;
			this.nextBox = nextBox;
		}

		public void onKeyDown(Widget w, char code, int mods) { }
		
		/**
		 * When the user presses "enter", we check and reat.
		 *
		 * If it's correct, we also move the focus to the next item.
		 */
		public void onKeyPress (Widget sender, char keyCode, int modifiers) {
			if (keyCode == KeyboardListener.KEY_ENTER) {
				if (sender instanceof TextBox) {
					final TextBox tb = (TextBox) sender;
					if (tb.getText().equals(gottagetit)) {
						addSuccess();
						tb.setStyleName("WERTiClozeTextWin");
						tb.setEnabled(false);
						nextBox.setFocus(true);
					} else {
						addFault();
						tb.setStyleName("WERTiClozeTextFail");
					}
				} else {
					showError("Someone sent ClozeListener an event, "
						+ "but we don't know who he is! He says "
						+ "he's " + sender.getClass());
				}
			}
		}

		public void onKeyUp (Widget sender, char keyCode, int modifiers) { }

		public void onLostFocus(Widget w) { }

		/**
		 * If we aren't enabled, pass the buck, the user can't do anything.
		 */
		public void onFocus(Widget w) {
			if (!current.isEnabled()) {
				nextBox.setFocus(true);
			}
		}

		/**
		 * The user requested help and we set the textbox to "I have helped you" and
		 * put the target text in it.
		 */
		public void onClick(Widget w) {
			current.setStyleName("WERTiClozeTextHelped");
			current.setText(gottagetit);
			current.setEnabled(false);
		}
	}

	public void addFault() { }

	public void addSuccess() { }

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
	 */
	public void onModuleLoad() {
		Element domSpan;
		ClozeItem tb0, tb1;
		tb0 = tb1 = new ClozeItem("WERTiClozeText");
		int i = 1;
		while ((domSpan = DOM.getElementById(getId(i))) != null) {
			tb0.setStyleName("WERTiClozeText");
			tb0.addListeners(domSpan, tb1);
			RootPanel.get(getId(i)).add(tb0);
			tb0 = tb1; tb1 = new ClozeItem("WERTiClozeText");
			i++;
		}
	}

	private static String getId(int id) {
		return "WERTi-span-" + id;
	}

	private class ClozeItem extends HorizontalPanel {
		final TextBox target;
		final Button  helpButton;

		public ClozeItem(final String style) {
			super();
			this.setStyleName("WERTiClozeItem");
			target = new TextBox();
			target.setStyleName(style);
			helpButton = new Button("?");
			helpButton.setStyleName("WERTiHelpButton");
			this.add(target);
			this.add(helpButton);
		}

		public void addListeners(Element elem, ClozeItem next) {
			final ClozeListener heinzelmann = new ClozeListener(elem, next.target, target);
			target.addKeyboardListener(heinzelmann);
			target.addFocusLilstener(heinzelmann);
			helpButton.addClickListener(heinzelmann);
		}

		public void setText(String t) {
			target.setText(t);
		}
	}
}
