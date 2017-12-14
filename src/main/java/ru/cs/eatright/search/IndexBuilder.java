package ru.cs.eatright.search;

import ru.cs.eatright.knowledgebase.Product;
import ru.cs.eatright.nlp.Stemmer;
import java.util.Collections;
import java.util.Set;

import static ru.cs.eatright.search.IndexHelper.*;

public class IndexBuilder {

    private final Stemmer stemmer;

    public IndexBuilder(Stemmer stemmer) {
        this.stemmer = stemmer;
    }

    public Index createIndex(Set<Product> products) {
        Index index = new Index();

        for (Product product: products) {
            String stemmedProductName = stemmer.getStemmedWord(product.getName().toLowerCase());

            //get all bigrams and 3grams of product
            Set<IndexKey> indexKeysForProduct = getNGramsByString(stemmedProductName, 2);
            indexKeysForProduct.addAll(getNGramsByString(stemmedProductName, 3));

            //update index
            indexKeysForProduct.forEach(key -> index.update(key, Collections.singleton(product)));
        }

        return index;
    }
}
