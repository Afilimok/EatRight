package ru.cs.eatright.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import ru.cs.eatright.search.Index;
import ru.cs.eatright.search.IndexBuilder;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Bootstrapper {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrapper.class);

    public static Index buildKnowledgeBase() {
        //todo: we should stemm product names
        Set<Product> products = new HashSet<>();
        try {
            File folder = new ClassPathResource("food").getFile();
            for (File file: folder.listFiles()) {
                products.add(ProductReader.readProduct(file));
            }
            return IndexBuilder.createIndex(products);
        } catch (Exception e) {
            logger.error("Exception during knowledge base bootstrapping", e);
            return null;
        }
    }
}
