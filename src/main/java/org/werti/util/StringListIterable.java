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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.uima.jcas.cas.NonEmptyStringList;

/**
 * A wrapper class around an UIMA StringList that is iterable
 * @author Ramon Ziai, Niels Ott
 * @version $Id: FSListIterable.java 148 2007-03-09 09:13:08Z niels $
 */
public class StringListIterable implements Iterable<String> {

	private NonEmptyStringList list;
	
	/**
	 * The iterator used by this StringListIterable
	 * @author Ramon Ziai, Niels Ott
	 */
	private class StringListIterator implements Iterator<String> {
		
		private NonEmptyStringList work_list;
		
		public StringListIterator() {
			// the list needs to be modified so save it
			// TODO: this is by reference, shouldn't the list (not its items) be cloned?
			work_list = list;
		}

		public boolean hasNext() {
			
			// nothing left
			if ( work_list == null ) {
				return false;
			}
			
			// as long as there is something in the head 
			// there are data to get
			if ( work_list.getHead() != null ) {
				return true;
			}
			
			// otherwise empty, lists with just the tail are 
			// invalid
			return false;
			
		}

		public String next() {
			// illegal call
			if ( ! hasNext() ) {
				throw new NoSuchElementException();
			}
			
			// save the head
			String old_head = work_list.getHead();
			
			// save the tail as new working list if possible
			if  ( work_list.getTail() instanceof NonEmptyStringList ) {
				
				work_list = (NonEmptyStringList) work_list.getTail();
			} else {
				work_list = null;
			}	
			
			return old_head;
		}

		public void remove() {
			// I'm afraid I'm incapable of serving your request, my dear.
			throw new UnsupportedOperationException();
		}
		
	}

	/**
	 * Create a new StringListIterable from a NonEmptyStringList
	 * that then can be used e.g. in for (... ) loops
	 * @param list the list to use
	 */
	public StringListIterable(NonEmptyStringList list) {
		this.list = list;
	}
	
	
	public Iterator<String> iterator() {
		return new StringListIterator();
	}

}
