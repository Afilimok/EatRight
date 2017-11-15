package ru.cs.eatright.nlp;

import org.junit.Test;
import ru.cs.eatright.nlp.signatures.Phrase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ChunkerTest {

    private final Chunker chunker = new Chunker();

    @Test
    public void extractNounPhrases1() throws Exception {
        List<Phrase> expected = Arrays.asList(
                new Phrase(Collections.singletonList("вася"), Collections.singletonList("NN")),
                new Phrase(Arrays.asList("вкусную", "кашу"), Arrays.asList("VB", "NN"))
        );
        List<Phrase> actual = chunker.getPhrases("вася ест вкусную кашу");

        assertEquals(expected, actual);
    }

    @Test
    public void extractNounPhrases2() throws Exception {
        List<Phrase> expected = Arrays.asList(
                new Phrase(Arrays.asList("съем", "грибов"), Arrays.asList("NN", "NN")),
                new Phrase(Arrays.asList("жареной", "картошкой"), Arrays.asList("NN", "NN")),
                new Phrase(Collections.singletonList("курицей"), Collections.singletonList("NN"))
        );
        List<Phrase> actual = chunker.getPhrases("съем грибов с жареной картошкой и курицей");

        assertEquals(expected, actual);
    }
}
