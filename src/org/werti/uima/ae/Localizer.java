package org.werti.uima.ae;

import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import org.werti.uima.types.annot.HTML;

public class Localizer extends JCasAnnotator_ImplBase {
	public void process(JCas cas) {
                /*
		 *final FSIndex tagIndex = cas.getAnnotationIndex(HTML.type);
		 *final Iterator<HTML> tagIt = tagIndex.iterator();
		 *final String s = cas.getDocumentText();
		 *HTML tagref = null;
		 *String pathref = null;
		 *while (tagIt.hasNext()) {
		 *        tagref = (HTML)tagIt.next();
		 *}
                 */
	}

        /*
	 *private static String remotify(String remote_path, String relative_path) {
	 *        return relative_path;
	 *}
         */
}
