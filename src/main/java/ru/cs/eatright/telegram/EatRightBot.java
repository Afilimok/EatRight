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

/*
            InlineKeyboardButton dk1 = new InlineKeyboardButton().setText("like").setCallbackData("change_the_label");
            InlineKeyboardButton dk2 = new InlineKeyboardButton().setText("like").setCallbackData("change_the_label");

            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(dk1);
            rowsInline.add(rowInline);
            markupInline.setKeyboard(rowsInline);
            message.setReplyMarkup(markupInline);
            */
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton().setText("Dislike").setCallbackData("d_update_msg_text"));
            rowInline.add(new InlineKeyboardButton().setText("Like").setCallbackData("l_update_msg_text"));
            rowInline.add(new InlineKeyboardButton().setText("Учесть").setCallbackData("y_update_msg_text"));

            // Set the keyboard to the markup
            rowsInline.add(rowInline);
            // Add it to the message
            markupInline.setKeyboard(rowsInline);
            message.setReplyMarkup(markupInline);


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
