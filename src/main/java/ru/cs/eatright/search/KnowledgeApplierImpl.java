package ru.cs.eatright.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cs.eatright.knowledgebase.Bootstrapper;
import ru.cs.eatright.knowledgebase.Product;
import ru.cs.eatright.nlp.signatures.Query;
import ru.cs.eatright.nlp.signatures.Word;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.List;

import static ru.cs.eatright.nlp.RuleBasedPosTagger.*;

public class KnowledgeApplierImpl implements KnowledgeApplier {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeApplierImpl.class);
    private final IndexSearcher indexSearcher;

    public KnowledgeApplierImpl(IndexSearcher indexSearcher) {
        this.indexSearcher = indexSearcher;
    }

    /*for now just extract nouns from queries and search them in knowledge base*/
    @Override
    public String applyKnowledgeBaseToQuery(List<Query> queries) {
        List<ParsedQuery> parsedQueries = new ArrayList<>();


        logger.info("__queries = ", queries);
        for (Query query : queries) {
            logger.info("queries = ", queries);
            List<Product> products = new ArrayList<>();
            for (Word word : query.getWords()) {
                logger.info("++word = {} ", word);
                if (Objects.equals(word.getPos(), "S")) {
                    logger.info("я сюда не ходил");

                    searchIfNounIsProduct(word).ifPresent(products::add);
                }
                //if (word.getPos() == PosTag.OTHER) searchIfNounIsProduct(word).ifPresent(products::add);
                //todo: add checks for verbs, adverbs, ets
            }

            if (!products.isEmpty()) parsedQueries.add(new ParsedQuery(products));
        }

        return ResponseCreator.createResponse(parsedQueries);
    }

    private Optional<Product> searchIfNounIsProduct(Word word) {
        if (!Objects.equals(word.getPos(), "S"))
            throw new IllegalArgumentException(String.format("Word '%s' should be noun", word));

        Map<Product, Integer> productFrequencies = indexSearcher.searchData(word.getWord());
        return Scorer.getMostSuitableProduct(word.getWord(), productFrequencies);
    }
}
