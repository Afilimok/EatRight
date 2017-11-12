package ru.cs.eatright.nlp;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cs.eatright.nlp.signatures.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StopWordTokenizer implements Tokenizer {

    private static final Logger logger = LoggerFactory.getLogger(Tokenizer.class);

    private final CharArraySet stopwordSet;

    public StopWordTokenizer() {
        stopwordSet = new RussianAnalyzer().getStopwordSet();
    }

    @Override
    public List<Token> tokenize(String text) throws IOException {
        List<Token> tokens = new ArrayList<>();

        try (TokenStream tokenStream = new SimpleAnalyzer().tokenStream(Tokenizer.class.getName(), text)) {
            CharTermAttribute cattr = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                String word = cattr.toString();
                tokens.add(new Token(word, stopwordSet.contains(word)));
            }
        }

        return tokens;
    }
}