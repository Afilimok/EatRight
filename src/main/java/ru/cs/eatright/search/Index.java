package ru.cs.eatright.search;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cs.eatright.knowledgebase.Product;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Index {

    private static final Logger logger = LoggerFactory.getLogger(Index.class);


    private final Map<IndexKey, Set<Product>> data;

    public Index() {
        data = new HashMap<>();
    }

    public void update(IndexKey key, Set<Product> newProducts) {

        logger.info("key = ", key);
        Set<Product> products = data.getOrDefault(key, new HashSet<>());
        products.addAll(newProducts);
        data.put(key, products);
    }

    public Map<IndexKey, Set<Product>> getData() {
        return data;
    }

    public Set<Product> getProductsByKey(IndexKey key) {
        return data.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Index)) return false;
        Index index = (Index) o;
        return Objects.equal(getData(), index.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getData());
    }
}