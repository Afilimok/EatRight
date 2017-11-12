package ru.cs.eatright.nlp.signatures;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

public class Phrase {

    private List<String> phraseWords = new ArrayList<>();
    private List<String> posTags = new ArrayList<>();

    public Phrase(List<String> phraseWords, List<String> posTags) {
        this.phraseWords = phraseWords;
        this.posTags = posTags;
    }

    public List<String> getPhraseWords() {
        return phraseWords;
    }

    public List<String> getPosTags() {
        return posTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phrase phrase = (Phrase) o;

        if (!phraseWords.equals(phrase.phraseWords)) return false;
        return posTags.equals(phrase.posTags);
    }

    @Override
    public int hashCode() {
        int result = phraseWords.hashCode();
        result = 31 * result + posTags.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("phraseWords", phraseWords.toString())
                .add("posTags", posTags.toString())
                .toString();
    }
}
