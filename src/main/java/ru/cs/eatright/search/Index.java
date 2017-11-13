package ru.cs.eatright.search;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Index {
    private final Integer position;
    private final String ngramm;

    public Index(Integer position, String ngramm) {
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
        if (!(o instanceof Index)) return false;
        Index index = (Index) o;
        return Objects.equal(getPosition(), index.getPosition()) &&
                Objects.equal(getNgramm(), index.getNgramm());
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