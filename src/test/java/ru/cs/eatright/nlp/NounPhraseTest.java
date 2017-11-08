package ru.cs.eatright.nlp;

import edu.stanford.nlp.util.CoreMap;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class NounPhraseTest {

    private QueryProcessPipeline pipeline = null;

    @Before
    public void setPipeline() {
        pipeline = new QueryProcessPipeline();
    }

    @Test
    public void extractNounPhrases() throws Exception {
        List<CoreMap> expected = Arrays.asList();
        assertEquals(expected, pipeline.processText("Вася ест вкусную кашу"));

        expected = Arrays.asList();
        assertEquals(expected, pipeline.processText("Съем грибов с жареной картошкой и курицей"));
    }
}
