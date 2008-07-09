package org.werti.uima.enhancer;

import java.util.Iterator;

import org.apache.log4j.Logger;

import org.apache.uima.UimaContext;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.cas.StringArray;

import org.werti.uima.types.Enhancement;

import org.werti.uima.types.annot.Token;


public class PoSEnhancer extends JCasAnnotator_ImplBase {
	private static final Logger log =
		Logger.getLogger(PoSEnhancer.class);

	public void process(JCas cas) {
		log.info("Starting enhancement");
		Object o = getContext().getConfigParameterValue("Tags");
		final String[] tags;
		if (o instanceof String[]) {
			tags = (String[]) o;
			final StringBuilder sb = new StringBuilder();
			for (String s:tags) {
				sb.append(s + " ");
			}
			log.debug("Tags: " + sb.toString());
		} else {
			throw new RuntimeException("Expected String Array as Paramater. Aborting");
		}

		o = getContext().getConfigParameterValue("enhance");
		final String method;
		if (o instanceof String) {
			method = (String) o;
		} else {
			throw new RuntimeException("Expected String as Paramater. Aborting");
		}

		if (method.equals("clr")) clr(cas, tags, getContext());
		else if (method.equals("fib")) fib(cas, tags, getContext());
		else if (method.equals("ask")) ask(cas, tags, getContext());

		log.info("Finished enhancement");
	}

	// need thos two to supply JS-annotations with IDs.
	private static int id = 0;
	private static String get_id() {
		return "WERTi-span-" + id;
	}

	// put annotations for FIB enhancement in the CAS
	@SuppressWarnings("unchecked")
	private static void fib(JCas cas, String[] tags, UimaContext context) {
		final FSIndex textIndex = cas.getAnnotationIndex(Token.type);
		final Iterator<Token> tit = textIndex.iterator();

		Token t = tit.next();

		{//begin form
			final Enhancement e_start = new Enhancement(cas);
			e_start.setBegin(t.getBegin());
			e_start.setEnd(t.getBegin());
			final StringArray  sa = new StringArray(cas, 1);
			final IntegerArray ia = new IntegerArray(cas, 1);

			sa.set(0,
					"<form action=\"javascript:void(null)\">"
					+ "<script type=\"text/javascript\">"
					+ "function fieldAct(id,token,usertoken,spanid) {"
					+ "if (token == usertoken)	{"
					+ "document.getElementById(spanid).innerHTML = token ; "
					+ "document.getElementById(spanid).style.color = 'green';"
					+ "document.getElementById(spanid).style.fontWeight = 'bold';"
					+ "}"
					+ "else {"
					+ "document.getElementById(id).style.color = 'red';"
					+ "document.getElementById(id).style.fontWeight = 'bold';"
					+ "}"
					+ "}"
					+ "</script>"
					+ "<form action=\"javascript:void(null)\">"
					+ "<script type=\"text/javascript\">"
					+ "function fieldAct(id,token,usertoken,spanid) {"
					+ "if (token == usertoken)	{"
					+ "document.getElementById(spanid).innerHTML = token ; "
					+ "document.getElementById(spanid).style.color = 'green';"
					+ "document.getElementById(spanid).style.fontWeight = 'bold';"
					+ "}"
					+ "else {"
					+ "document.getElementById(id).style.color = 'red';"
					+ "document.getElementById(id).style.fontWeight = 'bold';"
					+ "}"
					+ "}"
					+ "</script>"
					);

			ia.set(0, e_start.getBegin());

			e_start.setEnhancement_list(sa);
			e_start.setIndex_list(ia);
			e_start.addToIndexes();
		}
		iteratetokens: while (tit.hasNext()) {
			if (t.getTag() == null) {
				log.debug("Encountered token with NULL tag");
				tit.next();
				continue iteratetokens;
			}
			if (arrayContains(t.getTag(), tags)) {
				final Enhancement e = new Enhancement(cas);
				e.setBegin(t.getBegin());
				e.setEnd(t.getEnd());
				final StringArray  sa = new StringArray(cas, 2);
				final IntegerArray ia = new IntegerArray(cas, 2);

				final String w = t.getCoveredText();

				id++;
				sa.set(0, 
					"<span id=\"" 
					+ get_id() + "\">"
					+ "<input id=\""
					+ get_id() +"\" size=\"4\" onchange=\"fieldAct('" 
					+ id + "','" 
					+ w + "',this.value,'" 
					+ get_id() + "')\" style=\"font-size:80%\"></input><!--"
					);


				sa.set(1, "-->" + "<a href=\"javascript:void(null)\" onclick=\"{document.getElementById('" 
					+ get_id() + "').innerHTML = '" + w + "' ; document.getElementById('" 
					+ get_id() + "').style.color = 'blue';document.getElementById('" 
					+ get_id() + "').style.fontWeight = 'bold';}\" style=\"font-size:80% "
					+ "; padding:2pt; margin:0pt 0pt 0pt -2pt; color:grey\" >?</a></span>");

				ia.set(0, e.getBegin());
				ia.set(1, e.getEnd());
				e.setEnhancement_list(sa);
				e.setIndex_list(ia);
				e.addToIndexes();
			}
			t = tit.next();
		}
		{// end form
			final Enhancement e_end = new Enhancement(cas);
			e_end.setBegin(t.getEnd());
			e_end.setEnd(t.getEnd());
			final StringArray  sa = new StringArray(cas, 1);
			final IntegerArray ia = new IntegerArray(cas, 1);

			sa.set(0, "</form>");

			ia.set(0, e_end.getBegin());

			e_end.setEnhancement_list(sa);
			e_end.setIndex_list(ia);
			e_end.addToIndexes();
		}
	}

	// put annotations for color enhancement in the CAS
	@SuppressWarnings("unchecked")
	private static void clr(JCas cas, String[] tags, UimaContext context) {
		final FSIndex textIndex = cas.getAnnotationIndex(Token.type);
		final Iterator<Token> tit = textIndex.iterator();

		iteratetokens: while (tit.hasNext()) {
			final Token t = tit.next();
			if (t.getTag() == null) {
				log.debug("Encountered token with NULL tag");
				continue iteratetokens;
			}
			if (arrayContains(t.getTag(), tags)) {
				Enhancement e = new Enhancement(cas);
				e.setBegin(t.getBegin());
				e.setEnd(t.getEnd());
				StringArray  csa = new StringArray(cas, 2);
				IntegerArray cia = new IntegerArray(cas, 2);

				csa.set(0, "<span style=\"color:blue;font-weight:bold\">");
				csa.set(1, "</span>");

				cia.set(0, e.getBegin());
				cia.set(1, e.getEnd());
				e.setEnhancement_list(csa);
				e.setIndex_list(cia);
				e.addToIndexes();
			}
		}
	}

	// put annotations for asking enhancement in the CAS
	@SuppressWarnings("unchecked")
	private static void ask(JCas cas, String[] tags, UimaContext context) {
		final FSIndex textIndex = cas.getAnnotationIndex(Token.type);
		final Iterator<Token> tit = textIndex.iterator();

		Token t = tit.next();

		iteratetokens: while (tit.hasNext()) {
			if (t.getTag() == null) {
				log.debug("Encountered token with NULL tag");
				tit.next();
				continue iteratetokens;
			}
			final Enhancement e = new Enhancement(cas);
			e.setBegin(t.getBegin());
			e.setEnd(t.getEnd());
			final StringArray  sa = new StringArray(cas, 2);
			final IntegerArray ia = new IntegerArray(cas, 2);

			sa.set(0, "<a href=\"javascript:void(null)\" stlye=\"color:black!important;\" "
					+ "onclick=\"{this.style.color = '"
					+ ((arrayContains(t.getTag(), tags))
					  ? "green"
					  : "red")
					+ "'; this.style.fontWeight = 'bold';}\">"
					);

			sa.set(1, "</a>" );
			ia.set(0, e.getBegin());
			ia.set(1, e.getEnd());
			e.setEnhancement_list(sa);
			e.setIndex_list(ia);
			e.addToIndexes();
			t = tit.next();
		}
	}
	
	// does an array of Strings contain a given String?
	private static boolean arrayContains(String data, String[] sa) {
		for (String s:sa) {
			if (s.equals(data)) return true;
		}
		return false;
	}
}
