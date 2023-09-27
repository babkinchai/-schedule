package com.telegram.bot.schedule.services.callback;

import com.telegram.bot.schedule.services.keyboards.InlineKeyboardService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class DayReturnCallbackServiceImpl extends CallbackService {

    private final InlineKeyboardService keyboardService;
    protected DayReturnCallbackServiceImpl(InlineKeyboardService keyboardService) {
        super(new DefaultBotOptions());
        this.keyboardService = keyboardService;
    }

    @Override
    public void getCallback(CallbackQuery callbackQuery) {
        EditMessageText build = EditMessageText.builder()
                .chatId(String.valueOf(callbackQuery.getFrom().getId()))
                .messageId(callbackQuery.getMessage().getMessageId())
                .replyMarkup(keyboardService.getWeekInlineKeyboardMarkup(keyboardService.getDataFromCallback(callbackQuery).getName()))
                .text(callbackQuery.getMessage().getText() + " "+keyboardService.getDataFromCallback(callbackQuery).getWeek().toString())
                .build();
        try {
            execute(build);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
