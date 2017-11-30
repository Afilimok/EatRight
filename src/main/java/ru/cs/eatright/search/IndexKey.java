package ru.cs.eatright.search;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class IndexKey {
    private final Integer position;
    private final String ngramm;

    public IndexKey(Integer position, String ngramm) {
        this.position = position;
        this.ngramm = ngramm;
    }

    public Integer getPosition() {
        return position;
    }

    public String getNgramm() {
        return ngramm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndexKey)) return false;
        IndexKey indexKey = (IndexKey) o;
        return Objects.equal(getPosition(), indexKey.getPosition()) &&
                Objects.equal(getNgramm(), indexKey.getNgramm());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPosition(), getNgramm());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("position", position)
                .add("ngramm", ngramm)
                .toString();
    }
}