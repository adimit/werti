package org.werti.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.werti.client.tasks.Task;

public class TaskPanel extends TabPanel {

	private final ArrayList<Task> tasks;

	public TaskPanel() {
		tasks = new ArrayList<Task>();
	}

	private Task currentTask;

	public int add(Task t) {
		tasks.add(t);

		final Widget helpBox = t.helpText();
		final Widget form = t.userInterface();
		final Panel main = new VerticalPanel();

		main.add(helpBox);
		main.add(form);

		this.add(main, t.name());

		assert true: tasks.size() == this.getWidgetCount();

		return tasks.size();
	}

	@Override public void selectTab(int index) {
		super.selectTab(index);
		currentTask = tasks.get(index);
	}

	public Task currentTask() {
		return currentTask;
	}
}
