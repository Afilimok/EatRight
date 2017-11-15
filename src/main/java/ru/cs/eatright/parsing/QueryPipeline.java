package ru.cs.eatright.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cs.eatright.nlp.*;
import ru.cs.eatright.nlp.signatures.Phrase;
import ru.cs.eatright.nlp.signatures.Query;
import ru.cs.eatright.nlp.signatures.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueryPipeline {
    private static final Logger logger = LoggerFactory.getLogger(QueryPipeline.class);

    private Chunker chunker;
    private Stemmer stemmer;

    public QueryPipeline(Chunker chunker, Stemmer stemmer) {
        this.chunker = chunker;
        this.stemmer = stemmer;
    }

    public List<Query> convertRequest2StemmedQuery(List<Token> tokens, boolean excludeStopWords) {
        String cleanedText = filterString(tokens, excludeStopWords);
        List<Phrase> phrases = chunker.getPhrases(cleanedText);
        return getQueries(phrases);
    }

    private String filterString(List<Token> tokens, boolean excludeStopWords) {
        List<String> tokenValues = filterTokens(tokens, excludeStopWords);
        return String.join(" ", tokenValues);
    }

    private List<String> filterTokens(List<Token> tokens, boolean excludeStopWords) {
        List<String> filteredTokens = new ArrayList<>();
        for (Token token : tokens) {
            if (!token.isStopWord() || !excludeStopWords) {
                filteredTokens.add(token.getData());
            }
        }

        if (filteredTokens.isEmpty()) {
            return Collections.emptyList();
        }

        return filteredTokens;
    }

    private List<Query> getQueries(List<Phrase> nounPhrases) {
        List<Query> queries = new ArrayList<>();
        for (Phrase nounPhrase : nounPhrases) {
            queries.add(new Query(nounPhrase.getPhraseWords(), stemmer.getStemmedWords(nounPhrase.getPhraseWords())));
        }
        return queries;
    }
}
