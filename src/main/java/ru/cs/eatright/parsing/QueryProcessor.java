package ru.cs.eatright.parsing;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cs.eatright.nlp.EatRightChunker;
import ru.cs.eatright.nlp.RusPosAnnotator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QueryProcessor {
    private static final Logger logger = LoggerFactory.getLogger(QueryProcessor.class);

    private StanfordCoreNLP pipeline = null;
    private EatRightChunker chunker = new EatRightChunker();
    private Tokenizer tokenizer = new StopWordTokenizer();

    public QueryProcessor() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, pos");

        pipeline = new StanfordCoreNLP(properties);
        pipeline.addAnnotator(new RusPosAnnotator());
    }

    public List<CoreMap> processText(String text) {
        List<CoreMap> nounPhrases = null;
        try {
            List<Token> tokens = tokenizer.tokenize(text);
            List<String> tokensWithoutStopwords = filterTokens(tokens);
            String cleanedText = String.join(" ", tokensWithoutStopwords);
            nounPhrases = extractNounPhrases(cleanedText);
            //todo stemmer for long words
        } catch (IOException e) {
            logger.error("Exception occur during parsing text tokenization.", e);
        }
        return nounPhrases;
    }

    private List<String> filterTokens(List<Token> tokens) {
        List<String> filteredTokens = new ArrayList<>();
        for (Token token : tokens) {
            if (!token.isStopWord()) {
                filteredTokens.add(token.getData());
            }
        }

        if (filteredTokens.isEmpty()) {
            filteredTokens.add("");
        }

        return filteredTokens;
    }

    private List<CoreMap> extractNounPhrases(String text) {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        List<CoreMap> textElements = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            textElements.addAll(chunker.getNounPhrases(sentence.get(CoreAnnotations.TokensAnnotation.class)));
        }

        return textElements;
    }
}
