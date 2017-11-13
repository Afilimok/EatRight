package ru.cs.eatright.search;

import ru.cs.eatright.model.Product;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class IndexerTest {
    //todo: create test factory & initialize products properly
    private final Product potato = new Product("картошка", 0, 0, 0, 0, 0, 0);
    private final Product cabbage = new Product("капуста", 0, 0, 0, 0, 0, 0);
    private final Product meat = new Product("мясо", 0, 0, 0, 0, 0, 0);
    private final Product milk = new Product("молоко", 0, 0, 0, 0, 0, 0);
    private final Product carrot = new Product("морковь", 0, 0, 0, 0, 0, 0);
    private final Product poison = new Product("яд", 0, 0, 0, 0, 0, 0);


    @Test
    public void createIndex() throws Exception {
        List<Product> products = Collections.singletonList(meat);
        IndexHelper indexer = new IndexHelper();

        IndexedData trueResult = new IndexedData();
        Set<Index> indexes = new HashSet<>();
        indexes.add(new Index(0, "$м"));
        indexes.add(new Index(1, "мя"));
        indexes.add(new Index(2, "яс"));
        indexes.add(new Index(3, "со"));
        indexes.add(new Index(4, "о^"));
        indexes.add(new Index(0, "$мя"));
        indexes.add(new Index(1, "мяс"));
        indexes.add(new Index(2, "ясо"));
        indexes.add(new Index(3, "со^"));
        trueResult.update(indexes, meat);

        assertEquals(trueResult, indexer.indexProducts(products));
    }

    @Test
    public void search() throws Exception {
        List<Product> products = Arrays.asList(carrot, milk, cabbage, poison, potato);
        IndexHelper indexer = new IndexHelper();
        IndexedData indexedData = indexer.indexProducts(products);

        assertEquals(new HashSet<>(Collections.singletonList(carrot)), indexer.searchData(indexedData, "кккквь"));
        assertEquals(new HashSet<>(Arrays.asList(carrot, milk, potato)), indexer.searchData(indexedData, "мохито"));
    }
}