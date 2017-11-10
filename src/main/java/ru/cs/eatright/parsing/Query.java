package ru.cs.eatright.parsing;

import com.google.common.base.MoreObjects;

import java.util.List;

public class Query {

    private List<String> phraseWords;
    private List<String> stemmedWords;

    public Query(List<String> phraseWords, List<String> stemmedWords) {
        this.phraseWords = phraseWords;
        this.stemmedWords = stemmedWords;
    }

    public List<String> getPhraseWords() {
        return phraseWords;
    }

    public List<String> getStemmedWords() {
        return stemmedWords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Query query = (Query) o;

        if (!phraseWords.equals(query.phraseWords)) return false;
        return stemmedWords.equals(query.stemmedWords);
    }

    @Override
    public int hashCode() {
        int result = phraseWords.hashCode();
        result = 31 * result + stemmedWords.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("phraseWords", phraseWords.toString())
                .add("stemmedWords", stemmedWords.toString())
                .toString();
    }
}
