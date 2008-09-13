package org.werti.client.tasks;

import com.google.gwt.user.client.ui.DisclosureEvent;
import com.google.gwt.user.client.ui.DisclosureHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.werti.client.run.RunConfiguration;

import org.werti.client.ui.ECheckBox;
import org.werti.client.ui.ERadioButton;
import org.werti.client.ui.ETextBox;
import org.werti.client.ui.HasData;

public class Categories implements Task {
	private static final String NAME = "Grammatical Categories";

	private static final ECheckBox dets = new ECheckBox("at", "Determiners");
	private static final ECheckBox prps = new ECheckBox("pp,in", "Prepositions");
	private static final ETextBox customCats = new ETextBox();

	private static final ERadioButton clr = new ERadioButton("Colorize", "methods", "Color Enhancement");
	private static final ERadioButton ask = new ERadioButton("Ask", "methods", "Active Presentation");
	private static final ERadioButton fib = new ERadioButton("Cloze", "methods", "Cloze Test");

	private static final HasData[] tagConfig = { dets, prps, customCats };
	private static final HasData[] enhConfig = { clr, ask, fib };

	public String name() { return NAME; }

	public Widget userInterface() {
		final VerticalPanel main = new VerticalPanel();

		final HorizontalPanel enhancements = new HorizontalPanel();
		final HorizontalPanel targets = new HorizontalPanel();

		targets.add(dets);
		targets.add(prps);

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

		enhancements.add(clr);
		enhancements.add(ask);
		enhancements.add(fib);

		main.add(targets);
		main.add(advanced);
		main.add(enhancements);

		return main;
	}

	public RunConfiguration configure(final String url) {
		// dirty regex hacking. Sure, there are better solutions…
		final StringBuilder sb_tags = new StringBuilder();
		for (final HasData o:tagConfig) {
			sb_tags.append(o.getData() + ",");
		}
		final String[] tags =
			sb_tags.toString().replaceAll("^[^\\w]*", "").split("(\\s*,\\s*)+");

		final StringBuilder module = new StringBuilder();
		for (final HasData o:enhConfig) {
			module.append(o.getData());
		}

		final org.werti.client.run.Categories config =
			new org.werti.client.run.Categories(tags, module.toString());
		return config;
	}
}
