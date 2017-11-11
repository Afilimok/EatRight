package ru.cs.eatright.nlpmodel;

import ru.cs.eatright.nlpmodel.signatures.Token;

import java.io.IOException;
import java.util.List;

public interface Tokenizer {
    List<Token> tokenize(String text) throws IOException;
}
