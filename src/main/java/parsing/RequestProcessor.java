package parsing;

import java.util.List;

public class RequestProcessor {

    private final TokenizerService tokenizer;

    public RequestProcessor(TokenizerService tokenizer) {
        this.tokenizer = tokenizer;
    }

    public String processRequest(String request) {
        List<Token> tokens = tokenizer.tokenize(request);
        //todo: add other services to chain (such as PosTagger, lemmatizer, ..) and return result answer to bot

        //for now it's just return tokens from request
        return String.valueOf(tokens);
    }
}
