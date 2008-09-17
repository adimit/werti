package org.werti.client.tasks;

import com.google.gwt.user.client.ui.Widget;

import org.werti.client.run.RunConfiguration;

/**
 * A Task interface to build a client UI-component and task configuration.
 *
 * Implementing classes must override all four methods and provide an interface,
 * a name, a configuration and an explanatory help text to describe this particular
 * task.
 *
 * See <code>org.werti.client.tasks.Categories</code> for an example implementation.
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public interface Task {
	/**
	 * The name of this component.
	 *
	 * @return This component's name.
	 */
	public String name();

	/**
	 * Build a user interface for this component.
	 *
	 * This is a generic GWT <code>Widget</code> that allows the user to configure
	 * this task according to their needs (choose different topics, targets, etc.)
	 *
	 * @return A GWT <code>Widget</code> that allows the user to configure this task.
	 */
	public Widget userInterface();

	/**
	 * Build a Server-Side configuration object.
	 *
	 * This (ideally :-) has to be according to what the user selected in the
	 * user interface.
	 *
	 * Note that it is actually possible to construct a default empty widget and
	 * then just return a standard (or random) configuration.
	 *
	 * @return A server-side configuration object knowing about the
	 * <code>AnalysisEngine</code>s to use and how to configure them and also
	 * what enhancement module to use.
	 */
	public RunConfiguration configure();

	/**
	 * A help widget to inform the user.
	 *
	 * @return A widget containing an explanatory text or similar to help the
	 * user understand what this task is good for.
	 */
	public Widget helpText();
}
