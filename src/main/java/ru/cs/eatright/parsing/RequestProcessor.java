package ru.cs.eatright.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cs.eatright.nlp.signatures.Query;

import java.util.List;

public class RequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RequestProcessor.class);

    private final QueryPipeline pipeline;

    public RequestProcessor(QueryPipeline pipeline) {
        this.pipeline = pipeline;
    }

    public String process(String request) {
        try {
            List<Query> queries = pipeline.convertRequest2StemmedQuery(request, false);
            return String.valueOf(queries);
        } catch (Exception e) {
            logger.error("Exception occur during parsing text tokenization.", e);
            return "Failed to parse request";
        }
    }
}