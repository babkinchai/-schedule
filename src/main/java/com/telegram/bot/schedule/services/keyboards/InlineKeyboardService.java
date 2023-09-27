package com.telegram.bot.schedule.services.keyboards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegram.bot.schedule.dto.InlineKeyboardCallbackDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class InlineKeyboardService {

    private final ObjectMapper objectMapper;
    private final InlineKeyboardButtonService buttonService;

    public InlineKeyboardService(ObjectMapper objectMapper, InlineKeyboardButtonService buttonService) {
        this.objectMapper = objectMapper;
        this.buttonService = buttonService;
    }

    @NotNull
    public InlineKeyboardMarkup getWeekInlineKeyboardMarkup(String message) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> weekButtons = buttonService.getWeekButtons(message);
        for (int i = 0; i < weekButtons.size(); i += 4) {
            keyboard.add(weekButtons.subList(i, Math.min(i + 4, weekButtons.size())));
        }
        keyboard.add(Arrays.asList(buttonService.getTodayButton(message), buttonService.getTomorrowButton(message)));
        keyboard.add(Collections.singletonList(buttonService.getReturnWeekButton(message)));
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
    @NotNull
    public List<List<InlineKeyboardButton>> getDayButtons(CallbackQuery callbackQuery) throws JsonProcessingException {
        List<InlineKeyboardButton> dayButtonList = buttonService.dayButtonList(callbackQuery);
        List<InlineKeyboardButton> inlineKeyboardButtons = Collections.singletonList(
                buttonService.getReturnDayButton(objectMapper.readValue(callbackQuery.getData(), InlineKeyboardCallbackDto.class))
        );
        return Arrays.asList(dayButtonList.subList(0, 3), dayButtonList.subList(3, 6), inlineKeyboardButtons);
    }

    public InlineKeyboardCallbackDto getDataFromCallback(CallbackQuery callbackQuery) {
        try {
            return objectMapper.readValue(callbackQuery.getData(), InlineKeyboardCallbackDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
