/*
Copyright (C) 2007 Niels Ott
Copyright (C) 2007 Ramon Ziai

This file is part of the Blog Post Corpus and Ontology Toolkit.

The Blog Post Corpus and Ontology Toolkit is free software; you can
redistribute it and/or modify it under the terms of the GNU General
Public License as published by the Free Software Foundation; either 
version 2 of the License, or (at your option) any later version.

The Blog Post Corpus and Ontology Toolkit is distributed in the hope 
that it will be useful, but WITHOUT ANY WARRANTY; without even the 
implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License 
along with the Blog Post Corpus and Ontology Toolkit; if not, write to 
the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, 
MA 02110-1301 USA

Linking the Blog Post Corpus and Ontology Toolkit statically or 
dynamically with other modules is making a combined work based on the
Blog Post Corpus and Ontology Toolkit. Thus, the terms and conditions 
of the GNU General Public License cover the whole combination.

In addition, as a special exception, the copyright holders of the Blog 
Post Corpus and Ontology Toolkit give you permission to combine the Blog
Post Corpus and Ontology Toolkit program with free software programs or
libraries that are released under the GNU LGPL and with code included
in the standard release of UIMA under the Common Public License  and 
XMLBeans under the Apache Software License (or modified versions of such 
code, with unchanged license). You may copy and distribute such a system 
following the terms of the GNU GPL for the Blog Post Corpus and Ontology 
Toolkit and the licenses of the other code concerned, provided that you 
include the source code of that other code when and as the GNU GPL 
requires distribution of source code.

Note that people who make modified versions of the Blog Post Corpus and 
Ontology Toolkit are not obligated to grant this special exception for 
their modified versions; it is their choice whether to do so. The GNU 
General Public License gives permission to release a modified version 
without this exception; this exception also makes it possible to release 
a modified version which carries forward this exception.
*/
package org.werti.util;

import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.NonEmptyFSList;
import org.apache.uima.jcas.cas.NonEmptyStringList;
import org.apache.uima.jcas.cas.StringList;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.JCas;

/**
 * Helper class/library that contains some static method to
 * deal with the ugly UIMA lists
 * @author Niels Ott, Ramon Ziai
 */
public class UimaListHelper {
	
	/**
	 * Helper method to add an item to a stupid FS list.
	 */
	public static NonEmptyFSList addToFSList(JCas aJCas, FSList list, TOP item ) {
		
		NonEmptyFSList result = new NonEmptyFSList(aJCas);
		
		result.setHead(item);
		result.setTail(list);
		
		return result;
		
	}
	
	/**
	 * Helper method to add an item to a stupid String list.
	 */
	public static NonEmptyStringList addToStringList(JCas aJCas, StringList list, String item ) {
		
		NonEmptyStringList result = new NonEmptyStringList(aJCas);
		
		result.setHead(item);
		result.setTail(list);
		
		return result;
		
	}
	

}
