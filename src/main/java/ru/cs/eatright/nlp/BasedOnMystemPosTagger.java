package ru.cs.eatright.nlp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stachek66.nlp.mystem.holding.Factory;
import ru.stachek66.nlp.mystem.holding.MyStem;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;
import ru.stachek66.nlp.mystem.holding.Request;
import ru.stachek66.nlp.mystem.model.Info;
import scala.Array;
import scala.Option;
import scala.collection.JavaConversions;

import java.io.File;
import java.util.ArrayList;

public class BasedOnMystemPosTagger {
    private static final Logger logger = LoggerFactory.getLogger(BasedOnMystemPosTagger.class);
    private final static MyStem mystemAnalyzer =
            new Factory("-igd --eng-gr --format json --weight")
                    .newMyStem("3.0", Option.<File>empty()).get();

    // todo: Debug example
    public static void main(final String[] args) throws MyStemApplicationException {

        final Iterable<Info> result =
                JavaConversions.asJavaIterable(
                        mystemAnalyzer
                                .analyze(Request.apply("И вырвал грешный мой язык"))
                                .info()
                                .toIterable());

        for (final Info info : result) {
            System.out.println(info.initial() + " -> " + info.lex() + " | " + info.rawResponse());
        }
    }

    // todo: rewrite null returning
    public ArrayList<String> posTag(String text) {
        try {
            Iterable<Info> result = JavaConversions.asJavaIterable(
                    mystemAnalyzer
                            .analyze(Request.apply(text))
                            .info()
                            .toIterable());

            ArrayList<String> posTags = new ArrayList<>();
            for (final Info info : result) {
                posTags.add(String.valueOf(info.lex()));
            }
            return posTags;
        } catch (MyStemApplicationException e) {
            logger.error("Exception occur during parsing text pos-tagging.", e);
            return null;
        }
    }
}
