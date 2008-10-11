package org.werti.enhancements.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class GerundsAsk implements EntryPoint {

		private static final String INF = "INF", GER = "GER",
			CLU_GERONLY = "CLU-GERONLY", CLU_INFONLY = "CLU-INFONLY",
			CLU_BOTHMEANSAME = "CLU-BOTHMEANSAME",
			CLU_BOTHMEANDIFF = "CLU-BOTHMEANDIFF", PRO = "PRO", PAR = "PAR",
			GOI = "GOI", GOP = "GOP", AMB = "AMB", RELEVANT = "RELEVANT";

	private static final String prefix = "WERTi-span";
	private static final String gerColor = "#ce6006";
	private static final String infColor = "#8c01c0";
	private static final String cluColor = "#2222ff";
	private static final String ingformColor = "black";
	private static final String ambiguousColor = "red";
	
	private static final int maxParentLevel = 2;
	
	public void onModuleLoad() {
		Element domSpan;
		for (int ii = 1; (domSpan = DOM.getElementById(prefix + "-" + RELEVANT + "-" + ii)) != null; ii++) {
			NodeList<Element> spans = domSpan.getElementsByTagName("span");
			for (int sp = 0; sp < spans.getLength(); sp++) {
				Element span = spans.getItem(sp);
				if (span.getId().contains("CLU")) {
					String text = span.getInnerText();
					final String a_start = 
						"<a href=\"javascript:void(null)\" style=\"color:black\" "
						+ "onclick=\"{this.style.color = 'green"
						+ "'; this.style.fontWeight = 'bold';}\">";
					span.setInnerHTML(a_start + text + "</a>");
				} else if (span.getId().contains(GER)) {
					span.setInnerHTML("<span style=\"color: "+ gerColor +"; font-weight:bold\">"
							+ span.getInnerText() + "</span>");
				} else if (span.getId().contains(INF)) {
					span.setInnerHTML("<span style=\"color: "+ infColor +"; font-weight:bold\">"
							+ span.getInnerText() + "</span>");
				}
			}
			
			spans = domSpan.getElementsByTagName("span");
			for (int sp = 0; sp < spans.getLength(); sp++) {
				Element span = spans.getItem(sp);
				if (span.getId().matches(prefix + "-\\d+") && span.getFirstChildElement() != null) {
					Element firstChild = span.getFirstChildElement();
					if (firstChild.getTagName().equals("span")) {
						Element spanParent = span.getParentElement();
						spanParent.replaceChild(span, firstChild);
					}
				}
			}
			
		}
		
		NodeList<Element> remainingSpans = RootPanel.getBodyElement().getElementsByTagName("span");
		for (int i = 0; i < remainingSpans.getLength(); i++) {
			domSpan = remainingSpans.getItem(i);
			if (domSpan.getId().matches(prefix + "-\\d+")) {
				final String text = domSpan.getInnerText();
				final String a_start = 
					"<a href=\"javascript:void(null)\" style=\"color:black\" "
					+ "onclick=\"{this.style.color = 'red"
					+ "'; this.style.fontWeight = 'bold';}\">";
				domSpan.setInnerHTML(a_start + text + "</a>");
			}
		}
		
	}
	
}
