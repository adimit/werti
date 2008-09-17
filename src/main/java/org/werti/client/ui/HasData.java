package org.werti.client.ui;


/**
 * An interface for components that <i>have</i> a <tt>data</tt> field
 * to store specific keys.
 */
public interface HasData {
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



