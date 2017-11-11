package ru.cs.eatright.nlp;

import org.junit.Test;
import ru.cs.eatright.nlpmodel.*;
import ru.cs.eatright.parsing.QueryPipeline;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PipelineTest {

    private final Tokenizer tokenizer = new StopWordTokenizer();
    private final Chunker chunker = new Chunker();
    private final QueryPipeline pipeline = new QueryPipeline();

    /*private static void pipelineAssert(List<Phrase> expected, List<Phrase> actual) {
        assertEquals(expected, actual);
    }

    @Test
    public void extractNounPhrases1() throws Exception {
        List<Phrase> expected = Arrays.asList(
                new Phrase(Collections.singletonList("вася"), Collections.singletonList("NN")),
                new Phrase(Arrays.asList("вкусную", "кашу"), Arrays.asList("VB", "NN"))
        );
        List<Phrase> actual = chunker.getPhrases(tokenizer.tokenize("Вася ест вкусную кашу"));

        pipelineAssert(expected, actual);
    }

    @Test
    public void extractNounPhrases2() throws Exception {
        List<Phrase> expected = Arrays.asList(
                new Phrase(Arrays.asList("съем", "грибов"), Arrays.asList("NN", "NN")),
                new Phrase(Arrays.asList("жареной", "картошкой"), Arrays.asList("NN", "NN")),
                new Phrase(Collections.singletonList("курицей"), Collections.singletonList("NN"))
        );

        List<Phrase> actual = pipeline.extractNounPhrases(
                pipeline.tokenizeAndCleanText("Съем грибов с жареной картошкой и курицей", false));

        pipelineAssert(expected, actual);
    }*/

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
