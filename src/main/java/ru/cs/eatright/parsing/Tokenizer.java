package ru.cs.eatright.parsing;

import java.io.IOException;
import java.util.List;

public interface Tokenizer {
    List<Token> tokenize(String text) throws IOException;
}
