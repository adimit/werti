package org.werti.client.ui;

import com.google.gwt.user.client.ui.CheckBox;

/**
 * A CheckBox that implements the HasData interface to
 * integrate with our other interface components.
 */
public class ECheckBox extends CheckBox implements HasData {
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
