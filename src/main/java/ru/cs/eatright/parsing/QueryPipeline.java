package ru.cs.eatright.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cs.eatright.nlp.*;
import ru.cs.eatright.nlp.signatures.Phrase;
import ru.cs.eatright.nlp.signatures.Query;
import ru.cs.eatright.nlp.signatures.Token;
import ru.cs.eatright.nlp.signatures.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueryPipeline {
    private static final Logger logger = LoggerFactory.getLogger(QueryPipeline.class);

    private final Tokenizer tokenizer;
    private final Chunker chunker;
    private final Stemmer stemmer;

    public QueryPipeline(Tokenizer tokenizer, Chunker chunker, Stemmer stemmer) {
        this.tokenizer = tokenizer;
        this.chunker = chunker;
        this.stemmer = stemmer;
    }

    public List<Query> convertRequest2StemmedQuery(String text, boolean excludeStopWords) throws IOException {
        logger.info("Text: " + text);
        List<Token> tokens = tokenizer.tokenize(text);
        logger.info("Tokens: " + tokens);
        String cleanedText = filterString(tokens, excludeStopWords);
        logger.info("Cleaned Text: " + cleanedText);
        List<Phrase> phrases = chunker.getPhrases(cleanedText);
        logger.info("Phrases: " + phrases);
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
            List<Word> words = nounPhrase.getWords();
            List<Word> modifiedWords = new ArrayList<>();
            for (Word word : words) {
                modifiedWords.add(new Word(stemmer.getStemmedWord(word.getWord()), word.getPos()));
            }
            queries.add(new Query(modifiedWords));
        }
        return queries;
    }
}
