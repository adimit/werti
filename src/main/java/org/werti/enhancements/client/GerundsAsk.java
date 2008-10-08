package org.werti.enhancements.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

public class GerundsAsk implements EntryPoint {

		private static final String INF = "INF", GER = "GER",
			CLU_GERONLY = "CLU-GERONLY", CLU_INFONLY = "CLU-INFONLY",
			CLU_BOTHMEANSAME = "CLU-BOTHMEANSAME",
			CLU_BOTHMEANDIFF = "CLU-BOTHMEANDIFF", PRO = "PRO", PAR = "PAR",
			GOI = "GOI", GOP = "GOP", AMB = "AMB", RELEVANT = "RELEVANT";

	private static final String prefix = "WERTi-span";
	private static final String gerColor = "#ce6006";
	private final String infColor = "#8c01c0";
	private final String cluColor = "#2222ff";
	private final String ingformColor = "black";
	private final String ambiguousColor = "red";
	
	private static final int maxParentLevel = 2;
	
	public void onModuleLoad() {
		Element domSpan;
		int i = 1;
		// go through all tokens
		while ((domSpan = DOM.getElementById(prefix + "-" + (i))) != null) {
			Element ancestor = null;
			if ((ancestor = getAncestor(domSpan, ".+CLU.+")) != null) {
				final String text = domSpan.getInnerText();
				final String a_start = "<a href=\"javascript:void(null)\" style=\"color:black\" "
						+ "onclick=\"{this.style.color = 'green"
						+ "'; this.style.fontWeight = 'bold';}\">";
				domSpan.setInnerHTML(a_start + text + "</a>");
			} else {
				final String text = domSpan.getInnerText();
				final String a_start = "<a href=\"javascript:void(null)\" style=\"color:black\" "
						+ "onclick=\"{this.style.color = 'red"
						+ "'; this.style.fontWeight = 'bold';}\">";
				domSpan.setInnerHTML(a_start + text + "</a>");
			}
			i++;
		}
		markHelper("GER", gerColor);
		markHelper("INF", infColor);
	}
	
	private static Element getAncestor(Element elem, String regex){
		Element current = elem;
		int i = 0;
		while (i < maxParentLevel && (current = current.getParentElement()) != null) {
			if (current.getId().matches(regex)) {
				return current;
			}
			i++;
		}
		return null;
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
