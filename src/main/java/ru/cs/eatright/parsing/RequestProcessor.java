package ru.cs.eatright.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cs.eatright.nlpmodel.signatures.Query;
import ru.cs.eatright.nlpmodel.signatures.Token;
import ru.cs.eatright.nlpmodel.Tokenizer;

import java.util.List;

public class RequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RequestProcessor.class);

    private final Tokenizer tokenizer;
    private final QueryPipeline pipeline = new QueryPipeline();

    public RequestProcessor(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public String process(String request) {
        try {
            List<Token> tokens = tokenizer.tokenize(request);
            List<Query> queries = pipeline.convertRequest2StemmedQuery(tokens, false);
            return String.valueOf(queries);
        } catch (Exception e) {
            logger.error("Exception occur during parsing text tokenization.", e);
            return "Failed to parse request";
        }
    }
}