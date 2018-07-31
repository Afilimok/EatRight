package ru.cs.eatright.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.HashSet;
import java.util.stream.IntStream;

public class IndexHelper {
    private static final Logger logger = LoggerFactory.getLogger(IndexHelper.class);

    public static Set<IndexKey> getNGramsByString(String str, int ngramm) {

        //logger.info(" int ngramm {}", ngramm);
        //logger.info(" str - {}", str);
        Set<IndexKey> indexKeys = new HashSet<>();
        final String token = "$" + str + "^";
        IntStream.range(0, token.length() - ngramm + 1)
                .forEach(i -> indexKeys.add(new IndexKey(i, token.substring(i, i + ngramm))));

        //logger.info("__indexKeys {}", indexKeys);
        return indexKeys;
    }
}
