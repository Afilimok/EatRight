package ru.cs.eatright.search;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexKey {
    private static final Logger logger = LoggerFactory.getLogger(IndexKey.class);

    private final Integer position;
    private final String ngramm;

    public IndexKey(Integer position, String ngramm) {
        //logger.info("position = {} ", position, "   ngramm = {}", ngramm);
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
        logger.info("Object o", o);
        if (this == o) return true;
        if (!(o instanceof IndexKey)) return false;
        IndexKey indexKey = (IndexKey) o;
        logger.info("this = {}", this);
        logger.info("getPosition() = {}", getPosition());
        logger.info("indexKey.getPosition() = {}",indexKey.getPosition());
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