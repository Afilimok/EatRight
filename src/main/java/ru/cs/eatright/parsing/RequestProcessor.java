package ru.cs.eatright.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.cs.eatright.nlp.signatures.Query;
import ru.cs.eatright.nlp.signatures.Token;
import ru.cs.eatright.nlp.Tokenizer;

import java.util.List;

public class RequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RequestProcessor.class);
    private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    private final Tokenizer tokenizer;
    private final QueryPipeline pipeline;

    public RequestProcessor(Tokenizer tokenizer, QueryPipeline pipeline) {
        this.tokenizer = tokenizer;
        this.pipeline = pipeline;
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