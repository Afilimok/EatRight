package telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import parsing.RequestProcessor;

public class EatRightBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(EatRightBot.class);

    private final RequestProcessor requestProcessor;
    private final String username;
    private final String token;

    public EatRightBot(String username, String token, RequestProcessor requestProcessor) {
        this.username = username;
        this.token = token;
        this.requestProcessor = requestProcessor;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            logger.info("New update received: {}", update.getMessage());

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            String response = requestProcessor.processRequest(messageText);

            SendMessage message = new SendMessage()
                    .setChatId(chatId)
                    .setText(response);
            try {
                sendMessage(message);
            } catch (TelegramApiException e) {
                logger.error("Exception while sending message to chat '{}'", chatId, e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
