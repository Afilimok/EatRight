package ru.cs.eatright.search;

import org.junit.Test;
import ru.cs.eatright.model.Product;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import static org.junit.Assert.*;

public class IndexSearcherTest {

    private final Product potato = new Product("картошка", 0, 0, 0, 0, 0, 0);
    private final Product cabbage = new Product("капуста", 0, 0, 0, 0, 0, 0);
    private final Product meat = new Product("мясо", 0, 0, 0, 0, 0, 0);
    private final Product milk = new Product("молоко", 0, 0, 0, 0, 0, 0);
    private final Product carrot = new Product("морковь", 0, 0, 0, 0, 0, 0);
    private final Product poison = new Product("яд", 0, 0, 0, 0, 0, 0);

    @Test
    public void search() {
        Set<Product> products = new HashSet<>();
        products.addAll(Arrays.asList(carrot, milk, cabbage, poison, potato));
        Index index = IndexBuilder.createIndex(products);

        Map<Product, Integer> expected = new HashMap<>();
        expected.put(carrot, 15);
        expected.put(milk, 4);
        assertEquals(expected, IndexSearcher.searchData(index, "морковь"));

        expected = new HashMap<>();
        expected.put(carrot, 3);
        expected.put(milk, 4);
        expected.put(potato, 1);
        assertEquals(expected, IndexSearcher.searchData(index, "мохито"));
    }
}