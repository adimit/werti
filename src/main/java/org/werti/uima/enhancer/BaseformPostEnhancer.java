package org.werti.uima.enhancer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.werti.uima.ae.Vislcg3Annotator.ExtCommandConsume2Logger;
import org.werti.uima.ae.Vislcg3Annotator.ExtCommandConsume2String;
import org.werti.uima.types.Enhancement;

public class BaseformPostEnhancer extends JCasAnnotator_ImplBase {

	private String morphaLoc;
	private String morphaVerbstemLoc;
	
	/* (non-Javadoc)
	 * @see org.apache.uima.analysis_component.AnalysisComponent_ImplBase#initialize(org.apache.uima.UimaContext)
	 */
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		morphaLoc = (String) context.getConfigParameterValue("morphaLoc");
		morphaVerbstemLoc = (String) context.getConfigParameterValue("morphaVerbstemLoc");
	}

	@Override
	public void process(JCas cas) throws AnalysisEngineProcessException {
		FSIterator enhanceIter = cas.getAnnotationIndex(Enhancement.type).iterator();
		
		while (enhanceIter.hasNext()) {
			Enhancement e = (Enhancement) enhanceIter.next();
			String baseForm = null;
			String startTag = e.getEnhanceStart();
			// to-infinitive, easy
			if (startTag.contains("INF")) {
				String toInf = e.getCoveredText();
				// last token is the baseform
				String[] tokens =  toInf.split("\\s+");
				baseForm = tokens[tokens.length-1];
			// gerund, call morpha
			} else if (startTag.contains("GER")) {
				String gerund = e.getCoveredText();
				try {
					// morpha needs the tag
					baseForm = runMorpha(gerund + "_VVG");
				} catch (IOException e1) {
					throw new AnalysisEngineProcessException(e1);
				}
			// anything else? not interested
			} else {
				continue;
			}
			startTag = startTag.replace(">", " title=\"" + baseForm + "\">");
			e.setEnhanceStart(startTag);
		}
	}
	
	/*
	 * helper for running morpha with a line of input (one token_tag)
	 */
	private String runMorpha(String input) throws IOException {
		// build argument list
		ArrayList<String> argList = new ArrayList<String>();
		argList.add(morphaLoc);
		argList.add("-f");
		argList.add(morphaVerbstemLoc);
		
		// obtain process
		ProcessBuilder builder = new ProcessBuilder(argList);
		Process process = builder.start();
		
		// get input and output streams (are they internally buffered??)
		BufferedWriter toMorpha =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		BufferedReader fromMorpha = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		
		toMorpha.write(input);
		toMorpha.close();
		String output = fromMorpha.readLine();
		fromMorpha.close();
		return output;
	}


}
