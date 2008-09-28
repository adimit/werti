package org.werti.enhancements.client;

/**
 * A simple wrapper subclass that initializes its super
 * in a way that switches on markup of all ingforms
 * found in the input. This is required because currently we 
 * cannot pass arguments to GWT markup modules.
 * For all other details, see {@link GerundsColorize}.
 * @author no
 *
 */
public class GerundsColorizeAll extends GerundsColorize {

	public GerundsColorizeAll() {
		super(true);
	}

}
