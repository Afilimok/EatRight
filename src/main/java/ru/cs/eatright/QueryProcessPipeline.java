package ru.cs.eatright;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import ru.cs.eatright.nlp.EatRightChunker;
import ru.cs.eatright.nlp.RusPosAnnotator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QueryProcessPipeline {
    private StanfordCoreNLP pipeline = null;
    private EatRightChunker chunker = new EatRightChunker();

    public QueryProcessPipeline() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, pos");

        pipeline = new StanfordCoreNLP(properties);
        pipeline.addAnnotator(new RusPosAnnotator());
    }

    public List<CoreMap> processText(String text) {
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
