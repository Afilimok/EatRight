package ru.cs.eatright.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class RequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RequestProcessor.class);

    private final Tokenizer tokenizer;

    public RequestProcessor(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public String process(String request) {
        try {
            List<Token> tokens = tokenizer.tokenize(request);
            //todo: add other services to chain (such as PosTagger, lemmatizer, ..) and return result answer to bot

            //for now it's just return tokens from request
            return String.valueOf(tokens);
        } catch (Exception e) {
            logger.error("Exception occur during parsing text tokenization.", e);
            return "Failed to parse request";
        }
    }
}