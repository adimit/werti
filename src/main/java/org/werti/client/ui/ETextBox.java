package org.werti.client.ui;

import com.google.gwt.user.client.ui.TextBox;

/**
 * A TextBox that implements the HasData interface to
 * integrate with our other interface components.
 */
public class ETextBox extends TextBox implements HasData {

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
