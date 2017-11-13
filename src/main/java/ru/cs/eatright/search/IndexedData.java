package ru.cs.eatright.search;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import ru.cs.eatright.model.Product;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class IndexedData {

    private final HashMap<Index, Set<Product>> indexData = new HashMap<>();

    public Set<Product> getProductByIndex(Index index) {
        return indexData.containsKey(index) ? indexData.get(index) : new HashSet<>();
    }

    public void update(Set<Index> indexes, Product product) {
        indexes.forEach((index) -> {
            Set<Product> set = indexData.getOrDefault(index, new HashSet<>());
            set.add(product);
            indexData.put(index, set);
        });
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("indexData", indexData)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndexedData)) return false;
        IndexedData that = (IndexedData) o;
        return com.google.common.base.Objects.equal(indexData, that.indexData);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(indexData);
    }
}
