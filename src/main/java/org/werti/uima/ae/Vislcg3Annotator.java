package org.werti.uima.ae;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.werti.uima.types.annot.CGToken;
import org.werti.uima.types.annot.Token;

public class Vislcg3Annotator extends JCasAnnotator_ImplBase {

	private String vislcg3Loc;
	private String vislcg3GrammarLoc;
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		vislcg3Loc = (String) context.getConfigParameterValue("vislcg3Loc");
		vislcg3GrammarLoc = (String) context.getConfigParameterValue("vislcg3GrammarLoc");
	}

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		// TODO implement, call various helper methods
	}
	
	/*
	 * helper for converting Token annotations to a String for vislcg3
	 */
	private String toCG3Input(List<Token> tokenList) {
		StringBuilder result = new StringBuilder();
		for (Token t : tokenList) {
			result.append("\"<" + t + ">\"\n");
			result.append("\t\"");
			if (t.getLemma() != null) {
				result.append(t.getLemma());
			}
			result.append(" " + t.getTag() + "\n");
		}
		return result.toString();
	}
	
	/*
	 * helper for running vislcg3
	 */
	private String runVislcg3(String input) throws IOException {
		// build argument list
		ArrayList<String> argList = new ArrayList<String>();
		argList.add(vislcg3Loc);
		argList.add("--grammar");
		argList.add(vislcg3GrammarLoc);
		
		// obtain process
		ProcessBuilder builder = new ProcessBuilder(argList);
		Process process = builder.start();
		// get input and output streams (are they internally buffered??)
		BufferedWriter toCG =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		BufferedReader fromCG = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		// write input
		toCG.write(input);
		// get back output
		StringBuilder output = new StringBuilder();
		String line = null;
		
		while ((line = fromCG.readLine()) != null) {
			output.append(line);
			output.append("\n");
		}
		
		return output.toString();
	}

	/*
	 * helper for parsing output from vislcg3 back into our CGTokens
	 */
	private List<CGToken> parseCGOutput(String cgOutput) {
		// TODO implement
		return null;
	}
}
