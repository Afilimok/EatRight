package ru.cs.eatright.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cs.eatright.nlp.signatures.Query;
import ru.cs.eatright.search.IndexSearcher;
import ru.cs.eatright.search.KnowledgeApplier;

import java.util.List;

public class RequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RequestProcessor.class);

    private final QueryPipeline pipeline;
    private final KnowledgeApplier knowledgeApplier;

    public RequestProcessor(QueryPipeline pipeline, KnowledgeApplier knowledgeApplier) {
        this.pipeline = pipeline;
        this.knowledgeApplier = knowledgeApplier;
    }

    public String process(String request) {
        try {
            List<Query> queries = pipeline.convertRequest2StemmedQuery(request, false);
            return knowledgeApplier.applyKnowledgeBaseToQuery(queries);
        } catch (Exception e) {
            logger.error("Exception occur during parsing text tokenization.", e);
            return "Failed to parse request";
        }
    }
}