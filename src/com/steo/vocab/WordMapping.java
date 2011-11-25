package com.steo.vocab;

import java.util.ArrayList;

/**
 * This class corresponds to a mapping of words in various languages
 * Note: The database currently doesn't support this
 */
public class WordMapping {

    public static class Word {

        public String word;
        public int language;    //Not used for now

        public Word(String word, int language) {
            super();
            this.word = word;
            this.language = language;
        }
    }

    public ArrayList<Word> words;
    public int category;

    public WordMapping(ArrayList<Word> words, int category) {
        super();
        this.words = words;
        this.category = category;
    }
}
