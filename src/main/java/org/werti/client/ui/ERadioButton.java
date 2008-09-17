package org.werti.client.ui;

import com.google.gwt.user.client.ui.RadioButton;

/**
 * A RadioButton that implements the HasData interface to
 * integrate with our other interface components.
 */
public class ERadioButton extends RadioButton implements HasData {
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
