package ru.cs.eatright.nlp.signatures;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

public class Phrase {

    private List<Word> words = new ArrayList<>();

    public Phrase(List<Word> words) {
        this.words = words;
    }

    public List<Word> getWords() {
        return words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phrase phrase = (Phrase) o;

        return words.equals(phrase.words);
    }

    @Override
    public int hashCode() {
        return words.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("words", words.toString())
                .toString();
    }
}
