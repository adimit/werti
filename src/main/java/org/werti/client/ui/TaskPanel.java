package org.werti.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.werti.client.tasks.Task;

public class TaskPanel extends Composite {
	private final Panel helpBox;
	private final HorizontalPanel taskBar;
	private final VerticalPanel form;
	private final VerticalPanel main;

	private final ArrayList<Task> tasks;

	public TaskPanel() {
		helpBox = new VerticalPanel();
		taskBar = new HorizontalPanel();
		form = new VerticalPanel();
		main = new VerticalPanel();

		tasks = new ArrayList<Task>();

		main.add(taskBar);
		main.add(helpBox);
		main.add(form);

		initWidget(main);
	}

	private Task currentTask;

	public int add(Task t) {
		tasks.add(t);
		final Label name = new Label(t.name());
		name.addMouseListener(new TaskMouseListener(t));
		taskBar.add(name);
		select(t);
		return tasks.size();
	}

	public void add(Task t, int index) {
		tasks.add(index, t);
		final Label name = new Label(t.name());
		name.addMouseListener(new TaskMouseListener(t));
		taskBar.insert(name, index);
		select(t);
	}

	public void select(int id) {
		currentTask = tasks.get(id);
		select(currentTask);
	}

	private void select(Task task) {
		helpBox.clear();
		form.clear();

		helpBox.add(task.helpText());
		form.add(task.userInterface());
	}

	public void clear() {
		tasks.clear();
		taskBar.clear();
		main.clear();
		form.clear();
		helpBox.clear();
	}

	private class TaskMouseListener implements MouseListener {
		private Task task;

		public TaskMouseListener(Task t) {
			this.task = t;
		}

		public void onMouseDown (Widget sender, int x, int y) { }
		public void onMouseEnter (Widget sender) {
			helpBox.clear();
			helpBox.add(this.task.helpText());
		}
		public void onMouseLeave (Widget sender) {
			helpBox.clear();
			helpBox.add(currentTask.helpText());
		}

		public void onMouseMove (Widget sender, int x, int y) { }
		public void onMouseUp (Widget sender, int x, int y) {
			helpBox.clear();
			helpBox.add(this.task.helpText());
			form.clear();
			form.add(this.task.userInterface());
			currentTask = this.task;
		}
	}

	public Task currentTask() {
		return currentTask;
	}
}
