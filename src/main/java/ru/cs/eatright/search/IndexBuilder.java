package ru.cs.eatright.search;

import ru.cs.eatright.model.Product;

import java.util.Collections;
import java.util.Set;

import static ru.cs.eatright.search.IndexHelper.*;

public class IndexBuilder {
    public static Index createIndex(Set<Product> products) {
        Index index = new Index();

        for (Product product: products) {
            //get all bigrams and 3grams of product
            Set<IndexKey> indexKeysForProduct = getNGramsByString(product.getName(), 2);
            indexKeysForProduct.addAll(getNGramsByString(product.getName(), 3));

            //update index
            indexKeysForProduct.forEach(key -> index.update(key, Collections.singleton(product)));
        }

        return index;
    }
}
