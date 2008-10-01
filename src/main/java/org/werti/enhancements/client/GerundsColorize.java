package org.werti.enhancements.client;

import org.werti.uima.enhancer.Vislcg3Enhancer;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.dom.client.Element;

import com.google.gwt.user.client.DOM;

/**
 * Receptive presentation (colored highlighting) for the
 * gerunds vs. to-infinitive task based on 
 * the vislcg3 stuff.
 * This class can also highlight other ing-forms than the gerund
 * if they are present in the input. This is meant to be used
 * for debugging purposes.
 *
 * <ol>
 * <li>Spans with the GER (gerund) keyword are marked orange.</li>
 * <li>Spans with the keywords GOI (going-to future), GOP (going-to-future in the past), 
 *        PAR (participle), PRO (progressive verb form) are marked bold faced.</li>
 * <li>Spans with the keyword AMB (for ambiguous) are marked red.</li>
 * <li>Those using the INF (to-infinitive) keyword are marked purple</li>
 * <li>Clue phrases using the CLU keyword are marked blue</li>
 * 
 * </ul> 
 *
 *
 * @author nott
 */
public class GerundsColorize implements EntryPoint {

	private boolean showAllIngForms = true;
	private final String prefix = "WERTi-span";
	private final String gerColor = "#ce6006";
	private final String infColor = "#8c01c0";
	private final String cluColor = "#2222ff";
	private final String ingformColor = "black";
	private final String ambiguousColor = "red";
	
	
	/**
	 * Constructor that allows to switch of the markup of all ingforms found in 
	 * the input from the enhancer AE (which is @link {@link Vislcg3Enhancer}).
	 * @param showAllIngForms set this to true in order to highlight all ingforms
	 */
	public GerundsColorize(boolean showAllIngForms) {
		super();
		this.showAllIngForms = showAllIngForms;
	}
	
	
	/**
	 * Default constructor, disables the highlighting of
	 * all ingforms.
	 * @see {@link #GerundsColorize(boolean)}
	 */
	public GerundsColorize() {
		this(false);
	}


	/**
	 * This is the entry point method.
	 *
	 * We go through all WERTi-ids and color them bule with a <tt>&lt;span&gt;</tt>.
	 */
	public void onModuleLoad() {

		// mark GER (gerund)
		markHelper("GER", gerColor);
		markHelper("INF", infColor);
		markHelper("CLU-GERONLY", cluColor);
		markHelper("CLU-INFONLY", cluColor);
		markHelper("CLU-BOTHMEANSAME", cluColor);
		markHelper("CLU-BOTHMEANDIFF", cluColor);
		
		
		if ( showAllIngForms ) {
			markHelper("GOI", ingformColor);
			markHelper("GOP", ingformColor);
			markHelper("PAR", ingformColor);
			markHelper("PRO", ingformColor);
			markHelper("AMB", ambiguousColor);
		}	
		
	}
		
	private void markHelper(String code, String color) {
		int i = 1;
		Element domSpan;
		String pref = prefix + "-" + code + "-"; 
		
		// loop over all elements with the given ID and mark them
		while ((domSpan = DOM.getElementById(pref + i)) != null) {
			domSpan.setInnerHTML("<span style=\"color: "+ color +"; font-weight:bold\">"
					+ domSpan.getInnerText() + "</span>");
			i++;
		}
	}

		

}
