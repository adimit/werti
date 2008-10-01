package org.werti.client.tasks;

import org.werti.client.run.GerundsConfiguration;
import org.werti.client.run.RunConfiguration;
import org.werti.client.ui.ECheckBox;
import org.werti.client.ui.ERadioButton;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A task panel used in WERTi's input dialog that offers options for learning gerunds.
 */
public class Gerunds implements Task {

	// activity type radio buttons
	private final ERadioButton clr = new ERadioButton("Colorize", "methods", "Color Enhancement");
	private final ERadioButton ask = new ERadioButton("Ask", "methods", "Active Presentation");
	private final ERadioButton fib = new ERadioButton("Cloze", "methods", "Cloze Test");
	private static final ECheckBox showAll = new ECheckBox("all", "Debug: Highlight other ingforms as well");
	
	
	public RunConfiguration configure() {
		return new GerundsConfiguration(showAll.isChecked(), retrieveChecked());
	}

	private ERadioButton retrieveChecked() {
		for (ERadioButton erb : new ERadioButton[]{clr, ask, fib}) {
			if (erb.isChecked()) {
				return erb;
			}
		}
		return null;
	}
	
	public Widget helpText() {
		HTML text = new HTML
		( "<h4>Gerunds vs. To-infinitives</h4>"
		+ "<p>Learn more about when to use which.</p>"
		);
	return text;
	}

	public String name() {
		return "Gerunds vs. To-infinitives";
	}

	public Widget userInterface() {

		// main pane
		final VerticalPanel main = new VerticalPanel();
		
		

		// panel for activity type
		final HorizontalPanel enhancements = new HorizontalPanel();
		clr.setChecked(true);
		enhancements.add(clr);
		enhancements.add(ask);
		enhancements.add(fib);
		
		main.add(showAll);
		main.add(enhancements);

		return main;		
	}

}
