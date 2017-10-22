package parsing;

import java.util.List;

public interface TokenizerService {
    List<Token> tokenize(String text);
}
