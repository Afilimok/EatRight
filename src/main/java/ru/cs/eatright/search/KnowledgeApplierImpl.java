package ru.cs.eatright.search;

import ru.cs.eatright.knowledgebase.Product;
import ru.cs.eatright.nlp.signatures.Query;
import ru.cs.eatright.nlp.signatures.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.cs.eatright.nlp.RuleBasedPosTagger.*;

public class KnowledgeApplierImpl implements KnowledgeApplier {
    private final IndexSearcher indexSearcher;

    public KnowledgeApplierImpl(IndexSearcher indexSearcher) {
        this.indexSearcher = indexSearcher;
    }

    /*for now just extract nouns from queries and search them in knowledge base*/
    @Override
    public String applyKnowledgeBaseToQuery(List<Query> queries) {
        List<ParsedQuery> parsedQueries = new ArrayList<>();

        for (Query query: queries) {
            List<Product> products = new ArrayList<>();
            for (Word word: query.getWords()) {
                if (word.getPos() == PosTag.NOUN) searchIfNounIsProduct(word).ifPresent(products::add);
                //todo: add checks for verbs, adverbs, ets
            }

            if (!products.isEmpty()) parsedQueries.add(new ParsedQuery(products));
        }

        return ResponseCreator.createResponse(parsedQueries);
    }

    private Optional<Product> searchIfNounIsProduct(Word word) {
        if (word.getPos() != PosTag.NOUN)
            throw new IllegalArgumentException(String.format("Word '%s' should be noun", word));

        Map<Product, Integer> productFrequencies = indexSearcher.searchData(word.getWord());
        return Scorer.getMostSuitableProduct(word.getWord(), productFrequencies);
    }
}
