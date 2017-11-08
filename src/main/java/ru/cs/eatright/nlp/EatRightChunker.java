package ru.cs.eatright.nlp;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.TokenSequenceMatcher;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;

public class EatRightChunker {

    private final TokenSequencePattern tokenPattern =
            TokenSequencePattern.compile("([{tag:/(JJ.*|NUM|NN.*)/}]\\s)*[{tag:/NN.*/}]");

    public List<CoreMap> getNounPhrases(List<CoreLabel> annotatedText) {
        List<CoreMap> phrases = new ArrayList<>();
        TokenSequenceMatcher tokenMatcher = tokenPattern.getMatcher(annotatedText);
        while (tokenMatcher.find()){
            List<CoreMap> matches = tokenMatcher.groupNodes();
            phrases.addAll(matches);
        }
        return phrases;
    }
}
