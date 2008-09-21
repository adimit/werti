package org.werti.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.werti.client.tasks.Task;

/**
 * A more specialized <code>Panel<code> to hold several <code>Task</code>s.
 *
 * Users of this panel should be aware that using the default panel's 
 * <code>add(Widget)</code> is not really supported and may result in undefined
 * behaviour. Use <code>add(Task)</code> instead.
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public class TaskPanel extends TabPanel {

	private final ArrayList<Task> tasks;

	public TaskPanel() {
		super();
		tasks = new ArrayList<Task>();
	}

	private Task currentTask;

	/**
	 * Add a task to this panel.
	 *
	 * @param t A task (implementing the <code>Task</code>) interface.
	 * @return The position in the task list that this task will receive.
	 */
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

	/**
	 * Select the task and adjust the current active configuration.
	 *
	 * @param index The TabIndex of the task to be selected.
	 */
	@Override public void selectTab(int index) {
		super.selectTab(index);
	}

	/**
	 * Request the currently active (for the open tab) task.
	 *
	 * @return This Tab's <code>Task</code>
	 */
	public Task currentTask() {
		return currentTask;
	}

	/**
	 * Listener that gets invoked whenever a tab is selected.
	 */
	@Override
	public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
		super.onTabSelected(sender, tabIndex);
		currentTask = tasks.get(tabIndex);
	}
}
