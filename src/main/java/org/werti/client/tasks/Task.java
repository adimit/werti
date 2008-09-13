package org.werti.client.tasks;

import com.google.gwt.user.client.ui.Widget;

import org.werti.client.run.RunConfiguration;

public interface Task {
	public String name();
	public Widget userInterface();
	public RunConfiguration configure(String url);
}
