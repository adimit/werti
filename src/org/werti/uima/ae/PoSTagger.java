package org.werti.uima.ae;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;

import edu.stanford.nlp.tagger.maxent.*;

import edu.stanford.nlp.util.Pair;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import org.apache.uima.cas.FSIndex;

import org.apache.uima.jcas.JCas;

import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import org.werti.uima.types.annot.PoSTag;
import org.werti.uima.types.annot.RelevantText;

public class PoSTagger extends JCasAnnotator_ImplBase {
	private static final String TAG = "NN";

	public void process(JCas cas) {
		getContext().getLogger().setLevel(Level.ALL);
		final FSIndex textIndex = cas.getAnnotationIndex(RelevantText.type);
		final Iterator<RelevantText> tit = textIndex.iterator();
		try {
			getContext().getLogger().log(Level.INFO, "Constructing Tagger");
			final MaxentTagger tagger = new MaxentTagger("/home/aleks/models/bidirectional-wsj-0-18.tagger");
			getContext().getLogger().log(Level.INFO, "Finished constructing Tagger");
			final RelTextContainer rtc = new RelTextContainer(tagger);
			while (tit.hasNext()) {
				final RelevantText rt = tit.next();
				final String span = rt.getCoveredText();
				rtc.add(span, rt.getBegin());
			}
			getContext().getLogger().log(Level.FINE,
					"Amortized relevant text. " 
					+ "\nString length = " + rtc.text.length()
					+ "\nAmount of offsets = " + rtc.offsets.size());
			getContext().getLogger().log(Level.INFO, "Starting tagging.");
			rtc.tag();
			getContext().getLogger().log(Level.INFO, "Finished Tagging.");
                        /*
			 *final List<Pair<Integer,Integer>> allTags = rtc.getAll(TAG);
			 *for (Pair<Integer,Integer> t:allTags) {
			 *        PoSTag tag = new PoSTag(cas);
			 *        tag.setBegin(t.first);
			 *        tag.setEnd(t.second);
			 *        tag.setPoS(TAG);
			 *        tag.addToIndexes(cas);
			 *}
                         */
			for(IndexedTaggedWord itw:rtc.itlist) {
				PoSTag tag = new PoSTag(cas);
				tag.setBegin(itw.begin);
				tag.setEnd(itw.end);
				tag.setPoS(itw.tag());
				tag.setWord(itw.word());
				tag.addToIndexes();
			}
			getContext().getLogger().log(Level.INFO, "Done.");
		} catch (Exception e) {
			getContext().getLogger().log(Level.SEVERE, "Failed Processing!", e);
		}
	}

	private class RelTextContainer {
		private static final char DELIMITER = ' ';
		final MaxentTagger tagger;
		final List<Pair<Integer,Integer>> offsets;
		String text;
		List<IndexedTaggedWord> itlist;

		// used for addIndexes. Initialized in tag()
		int listindex = 0;
		int skew = 0;

		RelTextContainer(MaxentTagger tagger) {
			this.tagger = tagger;
			text = null;
			offsets = new ArrayList<Pair<Integer,Integer>>();
			itlist = null;
		}

		/**
		 * Add a string and its offset to the list.
		 *
		 * The offset should be retrieved with the Annotation.Begin() method.
		 */
		public void add(String span, int offset) {
			offsets.add(new Pair<Integer,Integer>(offset,span.length()));
			text += span + DELIMITER;
			getContext().getLogger().log(Level.FINE, "Added span\n" 
					+ span + "\nwith offset " + offset);
		}

		/**
		 * Tag what's already been put into the Container with the given Tagger.
		 */
		public void tag() {
			getContext().getLogger().log(Level.INFO, "Tokenizing sentences.");
			final List<Sentence<? extends HasWord>> slist =
				MaxentTagger.tokenizeText(new StringReader(text));
			itlist = new ArrayList<IndexedTaggedWord>(slist.size());
			getContext().getLogger().log(Level.FINE, "Found " + slist.size() + " sentences.");
			getContext().getLogger().log(Level.INFO, "Starting to tag.");
			listindex = 0;
			skew = offsets.get(listindex).first();
			getContext().getLogger().log(Level.FINE, "Initial skew is: " + skew);
			for (Sentence<? extends HasWord> s:slist) {
				getContext().getLogger().log(Level.FINE, 
						"Adding sentence. Skew " + skew);
				getContext().getLogger().log(Level.FINEST, 
						"Sentence is:\n" + s.toString());
				itlist.addAll(addIndexes(MaxentTagger.tagSentence(s)));
				getContext().getLogger().log(Level.FINE, 
						"I have found " + itlist.size() + " words so far.");
			}
		}

		public Collection<IndexedTaggedWord> addIndexes(Sentence<TaggedWord> s) {
			Pair<Integer,Integer> offset = offsets.get(listindex);
			getContext().getLogger().log(Level.FINEST, "Starting addIndexes with offset: b: " 
					+ offset.first + " e: " + offset.second);
			List<IndexedTaggedWord> indexes = new ArrayList<IndexedTaggedWord>(s.size());
			wordloop: for (TaggedWord w:s) {
				if (skew > (offset.first() + offset.second())) {
					getContext().getLogger().log(Level.FINEST, 
							"offset.first() = " + offset.first()
							+ " offset.second() = " + offset.second()
							+ "skew = " + skew + "; searching new skew.");
					offset = offsets.get((++listindex));
					skew = offset.first();
					getContext().getLogger().log(Level.FINEST, "Reset skew to " + skew);
					getContext().getLogger().log(Level.FINEST, 
							"Now working with offset: "
							+ offset.first + " end: " + offset.second);
				}
				getContext().getLogger().log(Level.FINEST, "Adjusted skew. adding a word");
				IndexedTaggedWord itw = new IndexedTaggedWord(w);
				getContext().getLogger().log(Level.FINEST, 
						"Treating TaggedWord " + w.word() 
						+ " with tag " + w.tag()
						+ " Setting b: " + itw.begin + " e: " + itw.end);
				getContext().getLogger().log(Level.FINEST, 
						"Is it null?");
				if (itw.word().equals("null")) {
					getContext().getLogger().log(Level.FINEST, 
							"it is. Skipping.");
					continue wordloop;
				}
				getContext().getLogger().log(Level.FINEST, 
						"no.");
				itw.begin = (skew);
				itw.end   = (skew = itw.word().length() + skew);
				if (itw.tag().equals("SYM")) {
					skew--;
				}
				getContext().getLogger().log(Level.FINEST, 
						"Defined word" + w.word() 
						+ " with tag " + w.tag()
						+ " Setting b: " + itw.begin + " e: " + itw.end
						+ "\nSkew is now " + skew);
				indexes.add(itw);
			}
			return indexes;
		}

		public List<Pair<Integer,Integer>> getAll(String tag) {
			List<Pair<Integer,Integer>> r = new ArrayList<Pair<Integer,Integer>>(itlist.size()/2);
			Iterator<IndexedTaggedWord> it = itlist.iterator();
			while (it.hasNext()) {
				final IndexedTaggedWord itw = it.next();
				if (itw.tag().equals(tag)) {
					r.add(new Pair<Integer,Integer>(itw.begin,itw.end));
				}
			}
			return r;
		}


	}
	public class IndexedTaggedWord extends TaggedWord {
		int begin, end;
		TaggedWord w;

		IndexedTaggedWord(TaggedWord w) {
			this.w = w;
		}

		public String word() {
			return w.word();
		}

		public String tag() {
			return w.tag();
		}
	}
}
