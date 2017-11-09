package ru.cs.eatright.parsing;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.snowball.SnowballFilter;
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

    public QueryProcessor() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit");

        pipeline = new StanfordCoreNLP(properties);
        pipeline.addAnnotator(new RusPosAnnotator());
    }

    public List<Phrase> processText(String text, boolean excludeStopWords) {
        List<Phrase> nounPhrases = null;
        try {
            List<Token> tokens = tokenizer.tokenize(text);
            List<String> tokenValues = filterTokens(tokens, excludeStopWords);
            String cleanedText = String.join(" ", tokenValues);
            nounPhrases = extractNounPhrases(cleanedText);
            //todo stemmer for long words
            /*for (Phrase nounPhrase : nounPhrases) {
                String nounPhrase2String = nounPhrase.getPhrase();
                TokenStream tokenStream = new SimpleAnalyzer().tokenStream(Tokenizer.class.getName(), nounPhrase2String);
                TokenStream result = new SnowballFilter(tokenStream, new RussianStemmer());

            }*/
        } catch (IOException e) {
            logger.error("Exception occur during parsing text tokenization.", e);
        }
        return nounPhrases;
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

    private List<Phrase> extractNounPhrases(String text) {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        List<Phrase> phrases = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            phrases.addAll(chunker.getNounPhrases(sentence.get(CoreAnnotations.TokensAnnotation.class)));
        }

        return phrases;
    }
}
