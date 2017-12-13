package ru.cs.eatright.search;

import ru.cs.eatright.nlp.signatures.Query;

import java.util.List;

public interface KnowledgeApplier {
    String applyKnowledgeBaseToQuery(List<Query> queries);
}
