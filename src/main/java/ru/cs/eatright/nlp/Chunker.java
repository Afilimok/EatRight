package ru.cs.eatright.nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.TokenSequenceMatcher;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import ru.cs.eatright.nlp.signatures.Phrase;
import ru.cs.eatright.nlp.signatures.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Chunker {

    private final TokenSequencePattern tokenPattern =
            TokenSequencePattern.compile("([{tag:/(JJ.*|NUM|NN.*|VB)/}] )*[{tag:/NN.*/}]");

    private StanfordCoreNLP pipeline = null;

    public Chunker() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit");

        this.pipeline = new StanfordCoreNLP(properties);
        pipeline.addAnnotator(new RusPosAnnotator());
    }

    public List<Phrase> getPhrases(String text) {
        List<CoreMap> sentences = getSentenceAnnotations(text);
        List<Phrase> phrases = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            phrases.addAll(getNounPhrases(sentence.get(CoreAnnotations.TokensAnnotation.class)));
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
                String pos = match.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                words.add(new Word(token, pos));
            }

            phrases.add(new Phrase(words));
        }
        return phrases;
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
