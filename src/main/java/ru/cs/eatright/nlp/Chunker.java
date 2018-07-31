package ru.cs.eatright.nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.TokenSequenceMatcher;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cs.eatright.nlp.signatures.Phrase;
import ru.cs.eatright.nlp.signatures.Word;

import java.util.*;

import static ru.cs.eatright.nlp.RuleBasedPosTagger.*;

public class Chunker {

    private static final Logger logger = LoggerFactory.getLogger(Chunker.class);

    private final TokenSequencePattern tokenPattern =
            TokenSequencePattern.compile("([{tag:/(A.*|NUM|S.*)/}] )*[{tag:/(S.*|ADV)/}]");

    private StanfordCoreNLP pipeline = null;

    public Chunker() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit");

        this.pipeline = new StanfordCoreNLP(properties);
        pipeline.addAnnotator(new RusPosAnnotator());
    }

    public List<Phrase> getPhrases(String text) {
        List<CoreMap> sentences = getSentenceAnnotations(text);
        logger.info("Recieved sentences: " + sentences);
        List<Phrase> phrases = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            logger.info("Sentence tokens annotation: " + sentence.get(CoreAnnotations.TokensAnnotation.class));
            List<Phrase> newPhrases = getNounPhrases(sentence.get(CoreAnnotations.TokensAnnotation.class));
            phrases.addAll(newPhrases);
            logger.info("Added phrase: " + newPhrases);
        }

        if (phrases.isEmpty()) {
            return Collections.emptyList();
        }

        return phrases;
    }

    private List<Phrase> getNounPhrases(List<CoreLabel> annotatedText) {
        logger.info("Annotated text: " + annotatedText);
        List<Phrase> phrases = new ArrayList<>();
        TokenSequenceMatcher tokenMatcher = tokenPattern.getMatcher(annotatedText);
        while (tokenMatcher.find()){
            List<CoreMap> matches = tokenMatcher.groupNodes();

            List<Word> words = new ArrayList<>();
            for (CoreMap match : matches) {
                logger.info("Matches");
                String token = match.get(CoreAnnotations.TextAnnotation.class);
                PosTag postag = convertTag(match.get(CoreAnnotations.PartOfSpeechAnnotation.class));
                logger.info("Token: " + token + ", POS Tag: " + postag);
                words.add(new Word(token, postag));
            }

            Phrase phrase = new Phrase(words);
            phrases.add(phrase);
            logger.info("Added phrase: " + phrase);
        }
        return phrases;
    }

    private PosTag convertTag(String tag) {
        for (PosTag posTag: PosTag.values()) {
            if (posTag.getPennTag().equals(tag)) return posTag;
        }
        throw new RuntimeException("Cannot convert pos tag " + tag);
    }


    private List<CoreMap> getSentenceAnnotations(String text) {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        if (sentences == null || sentences.isEmpty()) {
            return Collections.emptyList();
        }

        logger.info("Sentence annotations");
        for (CoreMap sentence : sentences) {
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

                logger.info("Token: " + word);
                logger.info("Part-Of-Speech-annotation: " + pos);
            }
        }

        return sentences;
    }
}
