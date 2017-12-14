package ru.cs.eatright.nlp.signatures;

import com.google.common.base.MoreObjects;
import ru.cs.eatright.nlp.RuleBasedPosTagger;

public class Word {
    private String word;
    private RuleBasedPosTagger.PosTag pos;

    public Word(String word, RuleBasedPosTagger.PosTag pos) {
        this.word = word;
        this.pos = pos;
    }

    public String getWord() {
        return word;
    }

    public RuleBasedPosTagger.PosTag getPos() {
        return pos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word1 = (Word) o;

        if (!word.equals(word1.word)) return false;
        return pos.equals(word1.pos);
    }

    @Override
    public int hashCode() {
        int result = word.hashCode();
        result = 31 * result + pos.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("word", word)
                .add("pos", pos)
                .toString();
    }
}
