package ru.cs.eatright.nlp;

import org.tartarus.snowball.ext.RussianStemmer;

public class Stemmer {

    private final RussianStemmer stemmer = new RussianStemmer();

    public String getStemmedWord(String phraseWord) {
        if (phraseWord.length() >= 5) {
            return stem(phraseWord);
        } else {
            return phraseWord;
        }
    }

    private String stem(String phraseWord) {
        stemmer.setCurrent(phraseWord);
        stemmer.stem();
        return stemmer.getCurrent();
    }
}
