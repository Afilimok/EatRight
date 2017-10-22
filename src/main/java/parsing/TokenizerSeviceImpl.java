package parsing;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class TokenizerSeviceImpl implements TokenizerService {

    private static final Logger logger = LoggerFactory.getLogger(TokenizerSeviceImpl.class);

    private final CharArraySet stopwordSet;

    public TokenizerSeviceImpl() {
        stopwordSet = new RussianAnalyzer().getStopwordSet();
    }

    @Override
    public List<Token> tokenize(String text) {
        List<Token> res = new ArrayList<>();
        try {
            TokenStream tokenStream = new SimpleAnalyzer().tokenStream(TokenizerService.class.getName(), text);
            CharTermAttribute cattr = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                String word = cattr.toString();
                res.add(new Token(word, stopwordSet.contains(word)));
            }
        } catch (Exception e) {
            logger.error("Unexpected exception occur during parsing text tokenization.", e);
        }

        return res;
    }
}
