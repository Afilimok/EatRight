package ru.cs.eatright.parsing;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.cs.eatright.nlpmodel.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class QueryPipeline {
    private static final Logger logger = LoggerFactory.getLogger(QueryPipeline.class);

    private StanfordCoreNLP pipeline = null;
    private Chunker chunker = new Chunker();
    private Stemmer stemmer = new Stemmer();

    public QueryPipeline() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit");

        pipeline = new StanfordCoreNLP(properties);
        pipeline.addAnnotator(new RusPosAnnotator());
    }

    public List<Query> convertRequest2StemmedQuery(List<Token> tokens, boolean excludeStopWords) {
        String cleanedText = filterString(tokens, excludeStopWords);
        List<CoreMap> sentences = getSentenceAnnotations(cleanedText);
        List<Phrase> phrases = chunker.getPhrases(sentences);
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

    private List<CoreMap> getSentenceAnnotations(String text) {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        if (sentences == null || sentences.isEmpty()) {
            return Collections.emptyList();
        }

        return sentences;
    }

    private List<Query> getQueries(List<Phrase> nounPhrases) {
        List<Query> queries = new ArrayList<>();
        for (Phrase nounPhrase : nounPhrases) {
            queries.add(new Query(nounPhrase.getPhraseWords(), stemmer.getStemmedWords(nounPhrase.getPhraseWords())));
        }
        return queries;
    }
}
