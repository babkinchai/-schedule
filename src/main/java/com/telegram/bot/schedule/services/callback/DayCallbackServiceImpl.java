package com.telegram.bot.schedule.services.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telegram.bot.schedule.services.keyboards.InlineKeyboardService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class DayCallbackServiceImpl extends CallbackService {

    private final InlineKeyboardService keyboardService;

    protected DayCallbackServiceImpl(InlineKeyboardService keyboardService) {
        super(new DefaultBotOptions());
        this.keyboardService = keyboardService;
    }

    @Override
    public void getCallback(CallbackQuery callbackQuery) {
        try {
            EditMessageText build = EditMessageText.builder()
                    .chatId(String.valueOf(callbackQuery.getFrom().getId()))
                    .messageId(callbackQuery.getMessage().getMessageId())
                    .replyMarkup(new InlineKeyboardMarkup(keyboardService.getDayButtons(callbackQuery)))
                    .text("тут должно быть расписание"+callbackQuery.getData())
                    .build();

            execute(build);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
