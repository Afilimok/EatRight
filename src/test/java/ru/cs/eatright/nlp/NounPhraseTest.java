package ru.cs.eatright.nlp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.cs.eatright.parsing.Query;
import ru.cs.eatright.parsing.QueryProcessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class NounPhraseTest {

    private QueryProcessor pipeline = null;

    @Before
    public void setPipeline() {
        pipeline = new QueryProcessor();
    }

    @Test
    public void extractNounPhrases() throws Exception {
        List<Phrase> expected = Arrays.asList(new Phrase(Collections.singletonList("вася"),
                                                            Collections.singletonList("NN")),
                                            new Phrase(Arrays.asList("вкусную", "кашу"), Arrays.asList("VB", "NN")));

        assertEquals(expected, pipeline.extractNounPhrases(
                pipeline.tokenizeAndCleanText("Вася ест вкусную кашу", true)));

        //todo fix next test case, it fails due to pos-tagger, expected list would be a true parse
        expected = Arrays.asList(new Phrase(Collections.singletonList("грибов"),
                        Collections.singletonList("NN")),
                new Phrase(Arrays.asList("жареной", "картошкой"), Arrays.asList("JJ", "NN")),
                new Phrase(Collections.singletonList("курицей"), Collections.singletonList("NN")));

        List<Phrase> expected2 = Arrays.asList(new Phrase(Arrays.asList("съем", "грибов"),
                        Arrays.asList("NN", "NN")),
                new Phrase(Arrays.asList("жареной", "картошкой"), Arrays.asList("NN", "NN")),
                new Phrase(Collections.singletonList("курицей"), Collections.singletonList("NN")));
        assertEquals(expected2, pipeline.extractNounPhrases(
                pipeline.tokenizeAndCleanText("Съем грибов с жареной картошкой и курицей", false)));
    }

    @Test
    public void testStemmedQueries() {
        List<Query> expected = Arrays.asList(new Query(Collections.singletonList("вася"),
                        Collections.singletonList("вася")),
                new Query(Arrays.asList("вкусную", "кашу"), Arrays.asList("вкусн", "кашу")));

        assertEquals(expected, pipeline.processText("Вася ест вкусную кашу", true));

        List<Query> expected2 = Arrays.asList(new Query(Arrays.asList("съем", "грибов"),
                        Arrays.asList("съем", "гриб")),
                new Query(Arrays.asList("жареной", "картошкой"), Arrays.asList("жарен", "картошк")),
                new Query(Collections.singletonList("курицей"), Collections.singletonList("куриц")));
        assertEquals(expected2, pipeline.processText("Съем грибов с жареной картошкой и курицей", false));
    }
}
