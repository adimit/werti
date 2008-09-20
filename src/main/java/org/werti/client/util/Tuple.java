package org.werti.client.util;

import java.io.Serializable;

/**
 * A helper class for representing a simple tuple.
 *
 * <i>Sigh</i>, if only I was in Haskell… Probably this isn't even a very
 * good way of doing things in Java.
 *
 * @author Aleksandar Dimitrov (aleks_d@gmx.de)
 * @version 0.1
 */
public class Tuple implements Serializable {
	public static final long serialVersionUID = 1;

	private String fst, snd;

	public Tuple(String fst, String snd) {
		this.fst = fst;
		this.snd = snd;
	}

	public Tuple() {
		fst = null;
		snd  = null;

	}

	public String fst() { return fst; }
	public String snd() { return snd; }
}
