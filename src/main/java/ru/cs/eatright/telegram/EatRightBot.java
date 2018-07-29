package ru.cs.eatright.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.cs.eatright.parsing.RequestProcessor;

import java.util.ArrayList;
import java.util.List;

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
            String response = requestProcessor.process(messageText);
            SendMessage message = new SendMessage()
                    .setChatId(chatId)
                    .setText(response);
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton().setText("\uD83D\uDC4E").setCallbackData("Dislike"));
            rowInline.add(new InlineKeyboardButton().setText("\uD83D\uDC4D").setCallbackData("Like"));
            rowInline.add(new InlineKeyboardButton().setText("\uD83D\uDC4C").setCallbackData("Add"));

            rowsInline.add(rowInline);
            markupInline.setKeyboard(rowsInline);
            message.setReplyMarkup(markupInline);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            if (call_data.equals("Add")) {

                String response = "Добавили это информацию к сегодняшний съеденной еде";
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText(response);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                logger.info("учесть___OK");
            }
            if (call_data.equals("Dislike")) {
                logger.info("Dislike___OK");
                String response = "Мы постараемся улучшить поиск";
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText(response);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (call_data.equals("Like")) {
                String response = "Мы рады, что вам нравится!";
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText(response);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                logger.info("Like___OK");
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