package ru.cs.eatright.nlp;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;

public class RusPosAnnotator implements Annotator {

    private final BasedOnMystemPosTagger tagger = new BasedOnMystemPosTagger();

    @Override
    public void annotate(Annotation annotation) {
        List<String> postags = tagger.posTag(annotation.get(TextAnnotation.class));
        List<CoreLabel> list = annotation.get(TokensAnnotation.class);

        assert postags.size() == list.size();

        for (int i = 0; i < list.size(); i++) {
            CoreLabel token = list.get(i);
            token.set(CoreAnnotations.PartOfSpeechAnnotation.class, postags.get(i));
        }
    }

    @Override
    public Set<Requirement> requires() {
        return Collections.singleton(TOKENIZE_REQUIREMENT);
    }

    @Override
    public Set<Requirement> requirementsSatisfied() {
        return Collections.singleton(POS_REQUIREMENT);
    }
}
