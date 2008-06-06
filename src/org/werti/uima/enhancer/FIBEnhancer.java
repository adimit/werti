package org.werti.uima.enhancer;

import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.cas.StringArray;

import org.apache.uima.util.Level;

import org.werti.uima.types.Enhancement;

import org.werti.uima.types.annot.Token;


public class FIBEnhancer extends JCasAnnotator_ImplBase {
	private static final String TAG = "NN";

	private static int id = 0;

	private static String get_id(int a) {
		return "WERTi-span-" + (id+a);
	}

	public void process(JCas cas) {
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
				getContext().getLogger().log(Level.WARNING,
						"Encountered token with NULL tag");
				tit.next();
				continue iteratetokens;
			}
			if (t.getTag().equals(TAG)) {
				final Enhancement e = new Enhancement(cas);
				e.setBegin(t.getBegin());
				e.setEnd(t.getEnd());
				final StringArray  sa = new StringArray(cas, 2);
				final IntegerArray ia = new IntegerArray(cas, 2);

				final String w = t.getCoveredText();

				sa.set(0, 
					"<span id=\"" 
					+ get_id(1) + "\">"
					+ "get_id<input id=\""
					+ get_id(0) +"\" size=\"4\" onchange=\"fieldAct('" 
					+ id + "','" 
					+ w + "',this.value,'" 
					+ get_id(0) + "')\" style=\"font-size:80%\"></input><!--"
					);


				sa.set(1, "-->" + "<a href=\"javascript:void(null)\" onclick=\"{document.getElementById('" 
					+ get_id(0) + "').innerHTML = '" + w + "' ; document.getElementById('" 
					+ get_id(0) + "').style.color = 'blue';document.getElementById('" 
					+ get_id(0) + "').style.fontWeight = 'bold';}\" style=\"font-size:80% "
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
}
