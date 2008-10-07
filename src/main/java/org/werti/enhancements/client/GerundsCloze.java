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
	
	private static final char[] consonants = new char[]{
		'b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','z'
	};
	private static final char[] vowels = new char[]{
		'a','e','i','o','u','y'
	};
	
	public void onModuleLoad() {
		Element domSpan;
		
		ClozeItem ci = null;
		ClozeItem prev;
		for (int ii = 1; (domSpan = DOM.getElementById(prefix + "-" + RELEVANT + "-" + ii)) != null; ii++) {
			Node clue = domSpan.getFirstChild();
			Node occurrence  = domSpan.getLastChild();
			
			if ((clue.getNodeType() != Node.ELEMENT_NODE) || (occurrence.getNodeType() != Node.ELEMENT_NODE)) {
				System.err.println("BANG! Something is wrong with the RELEVANT span");
				// this shouldn't happen
				continue;
			}
			Element clueE = (Element) clue;
			
			Element occurrenceE = (Element) occurrence;
			prev = ci;
			ci = new ClozeItem(RootPanel.get(occurrenceE.getId()));
			if (prev != null) {
				prev.setNext(ci);
			}
			
			clueE.setInnerHTML("<span style=\"color: " + cluColor
					+ "; font-weight:bold\">" + clueE.getInnerText()
					+ "</span>");
			String baseForm = "<em>(" + toBaseForm(occurrenceE) + ")</em>";
			ci.setTarget(normalizeTarget(occurrenceE.getInnerText()));
			occurrenceE.setInnerText("");
			ci.finish();
			Element bfE = DOM.createLabel();
			bfE.setInnerHTML(baseForm);
			domSpan.appendChild(bfE);
		}
	}
	
	private static String normalizeTarget(String target) {
		return target.replaceAll("\\s+", " ");
	}
	
	private String toBaseForm(Element occurrenceE) {
		if (occurrenceE.getId().contains(INF)) {
			// infinitive: just return the form after "to"
			String[] tokens = occurrenceE.getInnerText().split("\\s+", 3);
			return tokens[1];
		}
		// gerund: slightly more involved
		String gerund = occurrenceE.getInnerText();
		String base = gerund.replaceFirst("ing", "");
		
		// consonant endings
		if (containsChar(consonants, base.charAt(base.length()-1))) {
			// double consonant
			if (base.charAt(base.length()-2) == base.charAt(base.length()-1)) {
				base = base.substring(0, base.length()-1);
			// special case: 'ck' -> c
			} else if (base.length() > 4 && base.charAt(base.length()-2) == 'c' && base.charAt(base.length()-1) == 'k') {
				base = base.substring(0, base.length()-1);
			// vowel before consonant, need 'e'
			} else if (!containsChar(vowels, base.charAt(base.length()-3))
					&& containsChar(vowels, base.charAt(base.length()-2))
					&& containsChar(consonants, base.charAt(base.length()-1))) {
				base += 'e';
			}
		// special case: 'y' -> 'ie'
        } else if (base.length() < 3 && base.charAt(base.length()-1) == 'y') {
            base = base.substring(0, base.length()-1);
            base += "ie";
        // verbs that end in a vowel must end in 'e'
		} else if (containsChar(vowels, base.charAt(base.length()-1)) && !(base.charAt(base.length()-1) == 'e')) {
			base += 'e';
		}
	
		return base;
	}
	
	private static boolean containsChar(char[] arr, char c) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == c) {
				return true;
			}
		}
		return false;
	}
	
}
