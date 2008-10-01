package org.werti.enhancements.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class GerundsCloze implements EntryPoint {

	private static final String INF = "INF",
								GER = "GER",
								CLU_GERONLY = "CLU-GERONLY",
								CLU_INFONLY = "CLU-INFONLY",
								CLU_BOTHMEANSAME = "CLU-BOTHMEANSAME",
								CLU_BOTHMEANDIFF = "CLU-BOTHMEANDIFF",
								PRO = "PRO",
								PAR = "PAR",
								GOI = "GOI",
								GOP = "GOP",
								AMB = "AMB",
								RELEVANT = "RELEVANT";
	
	private static final String prefix = "WERTi-span";
	private static final String gerColor = "#ce6006";
	private final String infColor = "#8c01c0";
	private final String cluColor = "#2222ff";
	private final String ingformColor = "black";
	private final String ambiguousColor = "red";
	
	public void onModuleLoad() {
		Element domSpan; RootPanel rp;
		
		ClozeItem ci = new ClozeItem(RootPanel.get(prefix + "-" + RELEVANT + "-" + 1));
		for (int ii = 1; (domSpan = DOM.getElementById(prefix + "-" + RELEVANT + "-" + ii)) != null; /* see if clause */) {
			Node clue = domSpan.getFirstChild();
			Node occurrence  = domSpan.getLastChild();
			
			if ((clue.getNodeType() != Node.ELEMENT_NODE) || (occurrence.getNodeType() != Node.ELEMENT_NODE)) {
				// this shouldn't happen
				continue;
			}
			Element clueE = (Element) clue;
			
			Element occurrenceE = (Element) occurrence;

			if (occurrenceE.getId().contains(CLU_GERONLY)) {
				clueE.setInnerHTML("<span style=\"color: "+ cluColor +"; font-weight:bold\">"
						+ clueE.getInnerText() + "</span>");
				ci.setTarget(occurrenceE.getInnerText());
				occurrenceE.setInnerText("");
				ci.finish();
				
				if ((rp = RootPanel.get(prefix + "-" + RELEVANT + "-" + (++ii))) != null) {
					ci.setNext(new ClozeItem(rp));
					ci = ci.getNext();
				}
			}
			
		}
	}
	
	private String toBaseForm(String ingform) {
		return null;
	}
	
}
