package ru.cs.eatright.parsing;

import org.junit.Test;
import ru.cs.eatright.nlpmodel.*;
import ru.cs.eatright.nlpmodel.signatures.Query;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PipelineTest {

    private final Tokenizer tokenizer = new StopWordTokenizer();
    private final QueryPipeline pipeline = new QueryPipeline();

    @Test
    public void stemQueries1() throws IOException {
        List<Query> expected = Arrays.asList(
                new Query(Collections.singletonList("вася"), Collections.singletonList("вася")),
                new Query(Arrays.asList("вкусную", "кашу"), Arrays.asList("вкусн", "кашу"))
        );

        assertEquals(expected, pipeline.convertRequest2StemmedQuery(
                tokenizer.tokenize("Вася ест вкусную кашу"), false));
    }

    @Test
    public void stemQueries2() throws IOException {
        List<Query> expected = Arrays.asList(
                new Query(Arrays.asList("съем", "грибов"), Arrays.asList("съем", "гриб")),
                new Query(Arrays.asList("жареной", "картошкой"), Arrays.asList("жарен", "картошк")),
                new Query(Collections.singletonList("курицей"), Collections.singletonList("куриц"))
        );

        assertEquals(expected, pipeline.convertRequest2StemmedQuery(
                tokenizer.tokenize("Съем грибов с жареной картошкой и курицей"), false));
    }
}
