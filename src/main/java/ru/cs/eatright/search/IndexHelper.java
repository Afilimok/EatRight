package ru.cs.eatright.search;

import ru.cs.eatright.model.Product;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IndexHelper {

    public IndexedData indexProducts(List<Product> products) {
        IndexedData indexedData = new IndexedData();
        products.forEach((product)-> {
                indexedData.update(indexString(product.getName(), 2), product);
                indexedData.update(indexString(product.getName(), 3), product);
            }
        );
        return indexedData;
    }

    public Set<Product> searchData(IndexedData indexedData, String str) {
        Set<Product> products = new HashSet<>();

        Set<Index> indexes = indexString(str, 2);
        indexes.addAll(indexString(str, 3));

        String token = "$" + str + "^";

        indexes.stream().map(
            index ->
                IntStream.range(index.getPosition() - 2, index.getPosition() + 3)
                  .mapToObj(i -> new Index(i, index.getNgramm()))
                  .filter(newInd -> newInd.getPosition() > 0 && newInd.getPosition() < token.length())
                  .collect(Collectors.toList())
        ).flatMap(Collection::stream)
        .forEach(index -> products.addAll(indexedData.getProductByIndex(index)));

        return products;
    }

    private Set<Index> indexString(String str, int ngramm) {
        Set<Index> indexedData = new HashSet<>();
        final String token = "$" + str + "^";
        IntStream.range(0, token.length() - ngramm + 1)
                 .forEach(i -> indexedData.add(new Index(i, token.substring(i, i + ngramm))));

        return indexedData;
    }
}
