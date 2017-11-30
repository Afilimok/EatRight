package ru.cs.eatright.search;

import java.util.Set;
import java.util.HashSet;
import java.util.stream.IntStream;

public class IndexHelper {

    public static Set<IndexKey> getNGramsByString(String str, int ngramm) {
        Set<IndexKey> indexKeys = new HashSet<>();
        final String token = "$" + str + "^";
        IntStream.range(0, token.length() - ngramm + 1)
                .forEach(i -> indexKeys.add(new IndexKey(i, token.substring(i, i + ngramm))));

        return indexKeys;
    }
}
