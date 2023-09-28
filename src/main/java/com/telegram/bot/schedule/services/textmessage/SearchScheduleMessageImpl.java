package com.telegram.bot.schedule.services.textmessage;

import com.telegram.bot.schedule.services.BotRequestMessage;
import com.telegram.bot.schedule.services.keyboards.InlineKeyboardService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SearchScheduleMessageImpl extends BotRequestMessage {

    private final InlineKeyboardService inlineKeyboardService;

    public SearchScheduleMessageImpl(InlineKeyboardService inlineKeyboardService) {
        this.inlineKeyboardService = inlineKeyboardService;
    }

    @Override
    public void sendRequestMessage(Message message) {
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getWeekInlineKeyboardMarkup(message.getText());
        SendMessage build = SendMessage.builder()
                .chatId(message.getChatId())
                .replyMarkup(inlineKeyboardMarkup)
                .text(String.format("Выбран преподаватель: %s \nВыберите неделю:", message.getText()))
                .build();
        try {
            execute(build);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
