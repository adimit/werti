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
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.werti.uima.types.annot.CGReading;
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
		// collect original tokens here
		ArrayList<Token> originalTokens = new ArrayList<Token>();
		FSIterator tokenIter = jcas.getAnnotationIndex(Token.type).iterator();
		while (tokenIter.hasNext()) {
			originalTokens.add((Token)tokenIter.next());
		}
		// convert token list to cg input
		String cg3input = toCG3Input(originalTokens);
		try {
			// run vislcg3
			String cg3output = runVislcg3(cg3input);
			// parse cg output
			List<CGToken> newTokens = parseCGOutput(cg3output, jcas);
			// assert that we got as many tokens back as we provided
			assert true: newTokens.size() == originalTokens.size();
			// complete new tokens with information from old ones
			for (int i = 0; i < originalTokens.size(); i++) {
				Token origT = originalTokens.get(i);
				CGToken newT = newTokens.get(i);
				copy(origT, newT);
				// update CAS
				jcas.removeFsFromIndexes(origT);
				jcas.addFsToIndexes(newT);
			}
		} catch (IOException e) {
			throw new AnalysisEngineProcessException(e);
		}
	}
	
	/*
	 * helper for copying over information from Token to CGToken
	 */
	private void copy(Token source, CGToken target) {
		target.setBegin(source.getBegin());
		target.setEnd(source.getEnd());
		target.setTag(source.getTag());
		target.setLemma(source.getLemma());
	}
	
	/*
	 * helper for converting Token annotations to a String for vislcg3
	 */
	private String toCG3Input(List<Token> tokenList) {
		StringBuilder result = new StringBuilder();
		for (Token t : tokenList) {
			result.append("\"<" + t.getCoveredText() + ">\"\n");
			result.append("\t\"");
			if (t.getLemma() != null) {
				result.append(t.getLemma());
			}
			result.append("\"");
			String tag = t.getTag();
			if (tag != null) {
				tag = tag.toUpperCase();
			} else {
				tag = "NOTAG";
			}
			result.append(" " + tag + "\n");
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
		BufferedReader errorCG = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		
		// get error stream
		String error = "";
		String errline = null;
		while ((errline = errorCG.readLine()) != null) {
			error += errline;
		}
		errorCG.close();
		
		// write input
		toCG.write(input);
		toCG.close();
		// get back output
		StringBuilder output = new StringBuilder();
		String line = null;
		
		while ((line = fromCG.readLine()) != null) {
			output.append(line);
			output.append("\n");
		}
		fromCG.close();
		return output.toString();
	}

	/*
	 * helper for parsing output from vislcg3 back into our CGTokens
	 */
	private List<CGToken> parseCGOutput(String cgOutput, JCas jcas) {
		ArrayList<CGToken> result = new ArrayList<CGToken>();
		
		// current token and its readings
		CGToken current = null;
		ArrayList<CGReading> currentReadings = new ArrayList<CGReading>();
		// read output line by line, eat multiple newlines
		for (String line : cgOutput.split("\n+")) {
			// case 1: new cohort
			if (line.startsWith("\"<")) {
				if (current != null) {
					// save previous token
					current.setReadings(new FSArray(jcas, currentReadings.size()));
					int i = 0;
					for (CGReading cgr : currentReadings) {
						current.setReadings(i, cgr);
						i++;
					}
					result.add(current);
				}
				// create new token
				current = new CGToken(jcas);
			// case 2: a reading in the current cohort
			} else {
				CGReading reading = new CGReading(jcas);
				// split reading line into tags
				String[] temp = line.split("\\s");
				// iterate backwards due to UIMAs prolog list disease
				for (int i = temp.length-1; i >= 0; i--) {
					reading.setTail(reading);
					reading.setHead(temp[i]);
				}
				// add the reading
				currentReadings.add(reading);
			}
		}
		return result;
	}
}
