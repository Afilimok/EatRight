package ru.cs.eatright.search;

import edu.stanford.nlp.io.EncodingPrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cs.eatright.knowledgebase.Product;
import ru.cs.eatright.nlp.Stemmer;
import java.util.Collections;
import java.util.Set;

import static ru.cs.eatright.search.IndexHelper.*;

public class IndexBuilder {
    private static final Logger logger = LoggerFactory.getLogger(IndexBuilder.class);

    private final Stemmer stemmer;

    public IndexBuilder(Stemmer stemmer) {
        this.stemmer = stemmer;
    }

    public Index createIndex(Set<Product> products) {
        Index index = new Index();

        for (Product product: products) {

            String stemmedProductName = stemmer.getStemmedWord(product.getName().toLowerCase().trim());
            //logger.info("___product:" +   product, "__");
            //get all bigrams and 3grams of product
            Set<IndexKey> indexKeysForProduct = getNGramsByString(stemmedProductName, 2);
            indexKeysForProduct.addAll(getNGramsByString(stemmedProductName, 3));
            //logger.info("indexKeysForProduct: " +   indexKeysForProduct, "__");
            //update index
            indexKeysForProduct.forEach(key -> index.update(key, Collections.singleton(product)));
        }
        //logger.info("____index:  {}" , index);
        return index;
    }
}
