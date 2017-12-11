package ru.cs.eatright.nlp;

import ru.cs.eatright.nlp.signatures.Token;

import java.io.IOException;
import java.util.List;

public interface Tokenizer {
    List<Token> tokenize(String text) throws IOException;
}
