package parsing;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TokenizerSeviceTest {

    private final TokenizerService tokenizerSevice = new TokenizerSeviceImpl();

    @Test
    public void simpleTest() throws Exception {
        List<Token> expected = Arrays.asList(new Token("мороз", false), new Token("и", true), new Token("солнце", false),
                new Token("день", false), new Token("чудесный", false));
        assertEquals(expected, tokenizerSevice.tokenize("МоРОЗ и солнце, день чудесный!"));

        expected = Arrays.asList(new Token("а", true), new Token("вы", true), new Token("ноктюрны", false),
                new Token("сыграть", false), new Token("могли", false), new Token("бы", true));
        assertEquals(expected, tokenizerSevice.tokenize("А вы ноктюрны сыграть могли бы"));
    }
}