package org.werti.enhancements.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.dom.client.Element;

import com.google.gwt.user.client.DOM;

/**
 * Receptive presentation (colored highlighting) for the
 * gerunds vs. to-infinitive task based on 
 * the vislcg3 stuff.
 *
 * <ol>
 * <li>Spans with the GER (gerund) keyword are marked orange.</li>
 * <li>Those using the INF (to-infinitive) keyword are marked purple</li>
 * <li>Clue phrases using the CLU keyword are marked blue</li>
 * </ul> 
 *
 *
 * @author nott
 */
public class GerundsColorize implements EntryPoint {
	
	final String prefix = "WERTi-span-";
	final String gerColor = "#ce6006";
	final String infColor = "#8c01c0";
	final String cluColor = "#2222ff";

	/**
	 * This is the entry point method.
	 *
	 * We go through all WERTi-ids and color them bule with a <tt>&lt;span&gt;</tt>.
	 */
	public void onModuleLoad() {

		Element domSpan;
		
		// mark GER (gerund)
		int i = 1;
		while ((domSpan = DOM.getElementById(prefix + "GER-" + i)) != null) {
			domSpan.setInnerHTML("<span style=\"color: "+ gerColor +"; font-weight:bold\">"
					+ domSpan.getInnerText() + "</span>");
			i++;
		}
		
		// mark INF (to-infinitive)
		i = 1;
		while ((domSpan = DOM.getElementById(prefix + "INF-" + i)) != null) {
			domSpan.setInnerHTML("<span style=\"color: "+ infColor +"; font-weight:bold\">"
					+ domSpan.getInnerText() + "</span>");
			i++;
		}
		
		// mark CLUE (clue phrase)
		i = 1;
		while ((domSpan = DOM.getElementById(prefix + "CLU-" + i)) != null) {
			domSpan.setInnerHTML("<span style=\"color: "+ cluColor +"; font-weight:bold\">"
					+ domSpan.getInnerText() + "</span>");
			i++;
		}

		
	}

}
