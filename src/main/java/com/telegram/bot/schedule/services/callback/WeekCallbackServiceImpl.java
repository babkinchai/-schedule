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
public class WeekCallbackServiceImpl extends CallbackService {


    private final InlineKeyboardService keyboardService;

    protected WeekCallbackServiceImpl(InlineKeyboardService keyboardService) {
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
                    .text(String
                            .format(
                                    "Выбран преподаватель: %s \nВыбранная неделя: %s \nВыберите день:",
                                    keyboardService.getDataFromCallback(callbackQuery).getName(),
                                    keyboardService.getDataFromCallback(callbackQuery).getWeek().toString()
                            )
                    )
                    .build();
            execute(build);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
