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

    private final TokenSequencePattern tokenPattern =
            TokenSequencePattern.compile("([{tag:/(JJ.*|NUM|NN.*|VB)/}] )*[{tag:/NN.*/}]");

    private static final Logger logger = LoggerFactory.getLogger(Chunker.class);

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
            logger.info("Sentence pos tags: " + sentence.get(CoreAnnotations.PartOfSpeechAnnotation.class));
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
        List<Phrase> phrases = new ArrayList<>();
        TokenSequenceMatcher tokenMatcher = tokenPattern.getMatcher(annotatedText);
        while (tokenMatcher.find()){
            List<CoreMap> matches = tokenMatcher.groupNodes();

            List<Word> words = new ArrayList<>();
            for (CoreMap match : matches) {
                String token = match.get(CoreAnnotations.TextAnnotation.class);
                PosTag postag = convertTag(match.get(CoreAnnotations.PartOfSpeechAnnotation.class));
                words.add(new Word(token, postag));
            }

            phrases.add(new Phrase(words));
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

        return sentences;
    }
}
