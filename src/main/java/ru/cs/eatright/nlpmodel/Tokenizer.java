package ru.cs.eatright.nlpmodel;

import java.io.IOException;
import java.util.List;

public interface Tokenizer {
    List<Token> tokenize(String text) throws IOException;
}
