package ru.cs.eatright.parsing;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tartarus.snowball.ext.RussianStemmer;
import ru.cs.eatright.nlp.EatRightChunker;
import ru.cs.eatright.nlp.Phrase;
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
    private final RussianStemmer stemmer = new RussianStemmer();

    public QueryProcessor() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit");

        pipeline = new StanfordCoreNLP(properties);
        pipeline.addAnnotator(new RusPosAnnotator());
    }

    public List<Query> processText(String text, boolean excludeStopWords) {
        try {
            String cleanedText = tokenizeAndCleanText(text, excludeStopWords);
            List<Phrase> nounPhrases = extractNounPhrases(cleanedText);
            return getQueries(nounPhrases);
        } catch (IOException e) {
            logger.error("Exception occur during parsing text tokenization.", e);
        }
        return null;
    }

    private List<Query> getQueries(List<Phrase> nounPhrases) {
        List<Query> queries = new ArrayList<>();
        for (Phrase nounPhrase : nounPhrases) {
            queries.add(new Query(nounPhrase.getPhraseWords(), getStemmedWords(nounPhrase.getPhraseWords())));
        }
        return queries;
    }

    private List<String> getStemmedWords(List<String> phraseWords) {
        List<String> stemmedPhraseWords = new ArrayList<>();
        for (String phraseWord : phraseWords) {
            if (phraseWord.length() >= 5) {
                stemmer.setCurrent(phraseWord);
                stemmer.stem();
                stemmedPhraseWords.add(stemmer.getCurrent());
            } else {
                stemmedPhraseWords.add(phraseWord);
            }
        }
        return stemmedPhraseWords;
    }

    public String tokenizeAndCleanText(String text, boolean excludeStopWords) throws IOException {
        List<Token> tokens = tokenizer.tokenize(text);
        List<String> tokenValues = filterTokens(tokens, excludeStopWords);
        return String.join(" ", tokenValues);
    }

    private List<String> filterTokens(List<Token> tokens, boolean excludeStopWords) {
        List<String> filteredTokens = new ArrayList<>();
        for (Token token : tokens) {
            if (!token.isStopWord() || (!excludeStopWords && token.isStopWord())) {
                filteredTokens.add(token.getData());
            }
        }

        if (filteredTokens.isEmpty()) {
            filteredTokens.add("");
        }

        return filteredTokens;
    }

    public List<Phrase> extractNounPhrases(String text) {
        List<CoreMap> sentences = getSentenceAnnotations(text);
        return getPhrases(sentences);
    }

    private List<Phrase> getPhrases(List<CoreMap> sentences) {
        List<Phrase> phrases = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            phrases.addAll(chunker.getNounPhrases(sentence.get(CoreAnnotations.TokensAnnotation.class)));
        }
        return phrases;
    }

    private List<CoreMap> getSentenceAnnotations(String text) {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        return annotation.get(CoreAnnotations.SentencesAnnotation.class);
    }
}
