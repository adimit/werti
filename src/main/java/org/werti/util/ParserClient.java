package org.werti.util;

import java.io.Console;

import java.net.MalformedURLException;

import java.rmi.RemoteException;

import java.util.Iterator;
import java.util.List;

import danbikel.lisp.Sexp;

import danbikel.parser.Decoder;
import danbikel.parser.DecoderServer;
import danbikel.parser.DecoderServerRemote;
import danbikel.parser.Parser;

import danbikel.switchboard.AbstractSwitchboardUser;
import danbikel.switchboard.Switchboard;
import danbikel.switchboard.SwitchboardRemote;

import org.apache.log4j.Logger;

import org.werti.uima.types.annot.Token;

public class ParserClient extends Parser {
	private static final Logger log =
		Logger.getLogger(ParserClient.class);

	public static final long serialVersionUID = 2;

	public ParserClient(DecoderServer srv) throws RemoteException {
		super(srv);
	}

	public ParserClient() throws RemoteException {
		super(50);
		try {
			final SwitchboardRemote sb =
				AbstractSwitchboardUser.getSwitchboard(Switchboard.defaultBindingName, 2);
			register("Switchboard");
			server = (DecoderServerRemote) sb.getServer(id);
			decoder = new Decoder(id, server);
		} catch (MalformedURLException murle) {
			log.fatal(murle);
		}
	}

	public Sexp parseSentence(List<Token> sentence) throws Exception {
		StringBuilder sexp = new StringBuilder();

		for (Iterator<Token> i = sentence.iterator(); i.hasNext(); ) {
			final Token t = i.next();
			sexp.append("(");
			sexp.append(t.getCoveredText());
			sexp.append(" (");
			sexp.append(t.getTag());
			sexp.append("))");
		}

		log.debug("Parsing Sexp: " + sexp.toString());
		
		return parse(Sexp.read(sexp.toString()).list());
	}

	public static void main (String[] args) {
		try {
			final ParserClient parser = new ParserClient();

			final Console cons = System.console();

			while (true) {
				System.out.println("Sentence?");
				final String sentence = cons.readLine();
				final Sexp parsed = parser.parse(Sexp.read(sentence).list());
				System.out.println(parsed);
			}
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.out);
		}
	}
}
