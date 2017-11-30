package ru.cs.eatright.search;

import ru.cs.eatright.model.Product;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ru.cs.eatright.search.IndexHelper.getNGramsByString;

public class IndexSearcher {
    /**
    * @return map of found products and their frequencies
    */
    public static Map<Product, Integer> searchData(Index index, String str) {
        Set<IndexKey> indexKeys = getNGramsByString(str, 2);
        indexKeys.addAll(getNGramsByString(str, 3));

        String token = "$" + str + "^";

        List<IndexKey> indexKeysForFuzzySearch = indexKeys.stream().map(
                indexKey ->
                        IntStream.range(indexKey.getPosition() - 2, indexKey.getPosition() + 3)
                                .mapToObj(i -> new IndexKey(i, indexKey.getNgramm()))
                                .filter(newInd -> newInd.getPosition() >= 0 && newInd.getPosition() < token.length())
                                .collect(Collectors.toList())
        ).flatMap(Collection::stream).collect(Collectors.toList());

        Map<Product, Integer> productsFrequency = new HashMap<>();
        indexKeysForFuzzySearch.stream()
                .map(index::getProductsByKey)
                .filter(products -> products != null)
                .flatMap(Collection::stream)
                .forEach(product -> {
                    int freq = productsFrequency.getOrDefault(product, 0);
                    productsFrequency.put(product, ++freq);
                });

        return productsFrequency;
    }
}
