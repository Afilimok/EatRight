package ru.cs.eatright.search;

import ru.cs.eatright.knowledgebase.Product;
import java.util.Map;
import java.util.Optional;

public class Scorer {
    //todo: add calculation of Levenshtein distance
    //todo: add unit-tests
    public static Optional<Product> getMostSuitableProduct(String word, Map<Product, Integer> candidateFrequencies) {
        if (candidateFrequencies.isEmpty()) return Optional.empty();

        int max = candidateFrequencies.entrySet().stream()
                .max((x, y) -> Integer.compare(x.getValue(), y.getValue()))
                .map(Map.Entry::getValue)
                .get();

        if (max < word.length() + 2) return Optional.empty();

        return candidateFrequencies.entrySet().stream()
                .filter(x -> x.getValue() == max)
                .findFirst()
                .map(Map.Entry::getKey);
    }
}
