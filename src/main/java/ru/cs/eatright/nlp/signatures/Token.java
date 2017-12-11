package ru.cs.eatright.nlp.signatures;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Token {
    private final String data;
    private final boolean isStopWord;

    public Token(String data, boolean isStopWord) {
        this.data = data;
        this.isStopWord = isStopWord;
    }

    public String getData() {
        return data;
    }

    public boolean isStopWord() {
        return isStopWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return isStopWord() == token.isStopWord() &&
                Objects.equal(getData(), token.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getData(), isStopWord());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("data", data)
                .add("isStopWord", isStopWord)
                .toString();
    }
}
