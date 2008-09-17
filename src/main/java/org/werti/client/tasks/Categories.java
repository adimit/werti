package org.werti.client.tasks;

import com.google.gwt.user.client.ui.DisclosureEvent;
import com.google.gwt.user.client.ui.DisclosureHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.werti.client.run.CategoriesConfiguration;
import org.werti.client.run.RunConfiguration;

import org.werti.client.ui.ECheckBox;
import org.werti.client.ui.ERadioButton;
import org.werti.client.ui.ETextBox;
import org.werti.client.ui.HasData;

/**
 * A task that mimics the standard Python WERTi capabilities.
 *
 * More precisely, this task allows the user to select certain parts-of-speech
 * to enhance and a method to enhancem them (currently Cloze test, Active and Passive presentation)
 *
 * @author Aleksandar Dimitrov
 * @version 0.1
 */
public class Categories implements Task {


	// the title of this task, as it will appear in the menu.
	private static final String NAME = "Parts of Speech";

	private static final ECheckBox dets = new ECheckBox("at", "Determiners");
	private static final ECheckBox prps = new ECheckBox("pp,in", "Prepositions");
	private static final ETextBox customCats = new ETextBox();

	private static final ERadioButton clr = new ERadioButton("Colorize", "methods", "Color Enhancement");
	private static final ERadioButton ask = new ERadioButton("Ask", "methods", "Active Presentation");
	private static final ERadioButton fib = new ERadioButton("Cloze", "methods", "Cloze Test");

	private static final HasData[] tagConfig = { dets, prps, customCats };
	private static final HasData[] enhConfig = { clr, ask, fib };

	/**
	 * The name of this component.
	 *
	 * @return This component's name.
	 */
	public String name() { return NAME; }

	/**
	 * Build a user interface for this component.
	 *
	 * This component allows selection of one enhancement method and several
	 * PoS tags as targets.
	 *
	 * @return A GWT <code>Widget</code> that allows the user to configure this task.
	 */
	public Widget userInterface() {
		final VerticalPanel main = new VerticalPanel();

		final HorizontalPanel enhancements = new HorizontalPanel();
		final HorizontalPanel targets = new HorizontalPanel();

		targets.add(dets);
		targets.add(prps);

		dets.setChecked(true);

		final DisclosurePanel advanced = new DisclosurePanel("Advanced…");
		advanced.setAnimationEnabled(true);

		final VerticalPanel advancedContent = new VerticalPanel();

		advancedContent.add(new Label("Custom PoS Tags (comma separated)"));
		advancedContent.add(customCats);

		advanced.setContent(advancedContent);

		advanced.addEventHandler(new DisclosureHandler() {
			public void onClose(DisclosureEvent e) {
				toggleCats(true);
			}

			public void onOpen(DisclosureEvent e) {
				toggleCats(false);
			}

			private void toggleCats(boolean to) {
				dets.setEnabled(to);
				prps.setEnabled(to);
				customCats.setEnabled(!to);
			}
		});

		clr.setChecked(true);

		enhancements.add(clr);
		enhancements.add(ask);
		enhancements.add(fib);

		main.add(targets);
		main.add(advanced);
		main.add(enhancements);

		return main;
	}

	/**
	 * Build a Server-Side configuration object.
	 *
	 * This task uses <code>CategoriesConfiguration</code> adjusting tags
	 * and method according to the user interface settings.
	 *
	 * @return A server-side configuration object knowing about the
	 * <code>AnalysisEngine</code>s to use and how to configure them and also
	 * what enhancement module to use.
	 */
	public RunConfiguration configure() {
		// dirty regex hacking. Sure, there are better solutions…
		final StringBuilder sb_tags = new StringBuilder();
		for (final HasData o:tagConfig) {
			sb_tags.append(o.getData() + ",");
		}
		final String tags = sb_tags.toString().replaceAll("^[^\\w]*", "");

		final StringBuilder module = new StringBuilder();
		for (final HasData o:enhConfig) {
			module.append(o.getData());
		}

		final CategoriesConfiguration config = new CategoriesConfiguration(tags, module.toString());
		return config;
	}

	/**
	 * A help widget to inform the user.
	 *
	 * @return A widget containing an explanatory text.
	 */
	public Widget helpText() {
		HTML text = new HTML
			( "<h4>Part of Speech</h4>"
			+ "<p>Train your understanding of English parts of speech."
			);
		return text;
	}
}
