package ru.cs.eatright.nlpmodel;

import org.tartarus.snowball.ext.RussianStemmer;

import java.util.ArrayList;
import java.util.List;

public class Stemmer {

    private final RussianStemmer stemmer = new RussianStemmer();

    public List<String> getStemmedWords(List<String> phraseWords) {
        List<String> stemmedPhraseWords = new ArrayList<>();
        for (String phraseWord : phraseWords) {
            if (phraseWord.length() >= 5) {
                stemmedPhraseWords.add(stem(phraseWord));
            } else {
                stemmedPhraseWords.add(phraseWord);
            }
        }
        return stemmedPhraseWords;
    }

    private String stem(String phraseWord) {
        stemmer.setCurrent(phraseWord);
        stemmer.stem();
        return stemmer.getCurrent();
    }
}
