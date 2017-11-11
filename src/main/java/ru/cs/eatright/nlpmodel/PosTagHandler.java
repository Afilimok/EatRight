package ru.cs.eatright.nlpmodel;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class PosTagHandler {

    private StanfordCoreNLP pipeline = null;

    public PosTagHandler() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit");

        this.pipeline = new StanfordCoreNLP(properties);
        pipeline.addAnnotator(new RusPosAnnotator());
    }

    public List<CoreMap> getSentenceAnnotations(String text) {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        if (sentences == null || sentences.isEmpty()) {
            return Collections.emptyList();
        }

        return sentences;
    }
}
