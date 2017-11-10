package ru.cs.eatright.parsing;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cs.eatright.nlp.Chunker;
import ru.cs.eatright.nlp.Phrase;
import ru.cs.eatright.nlp.RusPosAnnotator;
import ru.cs.eatright.nlp.Stemmer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class RequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RequestProcessor.class);

    private final Tokenizer tokenizer;
    private StanfordCoreNLP pipeline = null;
    private Chunker chunker = new Chunker();
    private Stemmer stemmer = new Stemmer();

    public RequestProcessor(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;

        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit");

        pipeline = new StanfordCoreNLP(properties);
        pipeline.addAnnotator(new RusPosAnnotator());
    }

    public String process(String request) {
        try {
            List<Query> queries = convertRequest2StemmedQuery(request);
            return String.valueOf(queries);
        } catch (Exception e) {
            logger.error("Exception occur during parsing text tokenization.", e);
            return "Failed to parse request";
        }
    }

    private List<Query> convertRequest2StemmedQuery(String request) throws IOException {
        List<Token> tokens = tokenizer.tokenize(request);
        String cleanedText = cleanText(tokens);
        List<CoreMap> sentences = getSentenceAnnotations(cleanedText);
        List<Phrase> nounPhrases = getPhrases(sentences);
        return getQueries(nounPhrases);
    }

    private List<Query> getQueries(List<Phrase> nounPhrases) {
        List<Query> queries = new ArrayList<>();
        for (Phrase nounPhrase : nounPhrases) {
            queries.add(new Query(nounPhrase.getPhraseWords(), stemmer.getStemmedWords(nounPhrase.getPhraseWords())));
        }
        return queries;
    }

    private String cleanText(List<Token> tokens) {
        List<String> tokenValues = filterTokens(tokens);
        return String.join(" ", tokenValues);
    }

    private List<String> filterTokens(List<Token> tokens) {
        List<String> filteredTokens = new ArrayList<>();
        for (Token token : tokens) {
            if (!token.isStopWord()) {
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
        List<CoreMap> sentencesAnnotations = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        if (sentencesAnnotations == null || sentencesAnnotations.isEmpty()) {
            return Collections.emptyList();
        }

        return sentencesAnnotations;
    }

    private List<Phrase> getPhrases(List<CoreMap> sentences) {
        List<Phrase> phrases = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            phrases.addAll(chunker.getNounPhrases(sentence.get(CoreAnnotations.TokensAnnotation.class)));
        }

        if (phrases.isEmpty()) {
            return Collections.emptyList();
        }

        return phrases;
    }
}