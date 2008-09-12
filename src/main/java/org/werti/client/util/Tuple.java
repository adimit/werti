package org.werti.client.util;

import java.io.Serializable;

/**
 * A helper class for representing a simple immutable tuple.
 *
 * <i>Sigh</i>, if only I was in Haskell… Probably this isn't even a very
 * good way of doing things in Java.
 *
 * @author Aleksandar Dimitrov (aleks_d@gmx.de)
 * @version 0.1
 */
public class Tuple<F,S> implements Serializable {
	public static final long serialVersionUID = 1;

	final F fst;
	final S snd;

	public Tuple(F fst, S snd) {
		this.fst = fst;
		this.snd = snd;
	}

	public F fst() { return fst; }
	public S snd() { return snd; }
}
