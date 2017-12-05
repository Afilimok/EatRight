package ru.cs.eatright.nlp.signatures;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

public class Query {

    private List<Word> words = new ArrayList<>();

    public Query(List<Word> words) {
        this.words = words;
    }

    public List<Word> getWords() {
        return words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Query query = (Query) o;

        return words.equals(query.words);
    }

    @Override
    public int hashCode() {
        return words.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("phraseWords", words.toString())
                .toString();
    }
}
