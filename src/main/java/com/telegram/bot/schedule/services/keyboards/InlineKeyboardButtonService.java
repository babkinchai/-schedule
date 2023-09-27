package com.telegram.bot.schedule.services.keyboards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegram.bot.schedule.config.DayOfWeek;
import com.telegram.bot.schedule.dto.InlineKeyboardCallbackDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.telegram.bot.schedule.config.MappingConst.*;

@Service
public class InlineKeyboardButtonService {

    private final ObjectMapper objectMapper;

    public InlineKeyboardButtonService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public InlineKeyboardButton getTodayButton(String message) {
        try {
            return InlineKeyboardButton.builder()
                    .text("Сегодня")
                    .callbackData(objectMapper.writeValueAsString(new InlineKeyboardCallbackDto(message, null, null, TODAY)))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public InlineKeyboardButton getTomorrowButton(String message) {
        try {
            return InlineKeyboardButton.builder()
                    .text("Завтра")
                    .callbackData(objectMapper.writeValueAsString(new InlineKeyboardCallbackDto(message, null, null, TOMORROW)))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public InlineKeyboardButton getReturnWeekButton(String message) {
        try {
            return InlineKeyboardButton.builder()
                    .text("Назад")
                    .callbackData(objectMapper.writeValueAsString(new InlineKeyboardCallbackDto(message, null, null, WEEK_RETURN)))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public InlineKeyboardButton getReturnDayButton(InlineKeyboardCallbackDto message) {
        try {
            return InlineKeyboardButton.builder()
                    .text("Назад")
                    .callbackData(objectMapper.writeValueAsString(new InlineKeyboardCallbackDto(message.getName(), message.getWeek(), null, DAY_RETURNS)))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public List<InlineKeyboardButton> getWeekButtons(String message) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (Integer i = 1; i < 7; i++) {
            try {
                InlineKeyboardButton sub = new InlineKeyboardButton(i.toString());
                sub.setCallbackData(objectMapper.writeValueAsString(new InlineKeyboardCallbackDto(message, i.longValue(), null, WEEK)));
                buttons.add(sub);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return buttons;
    }



    @NotNull
    public List<InlineKeyboardButton> dayButtonList(CallbackQuery callbackQuery) throws JsonProcessingException {
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        List<DayOfWeek> values = Arrays.stream(DayOfWeek.values()).toList();

        InlineKeyboardCallbackDto callbackDto = null;
        callbackDto = objectMapper.readValue(callbackQuery.getData(), InlineKeyboardCallbackDto.class);
        for (int i = 0; i < values.size(); i++) {
            inlineKeyboardButtons.add(
                    InlineKeyboardButton.builder()
                            .text(values.get(i).getTitle())
                            .callbackData(objectMapper.writeValueAsString(new InlineKeyboardCallbackDto(callbackDto.getName(), callbackDto.getWeek(), (long) i, DAYS)))
                            .build()
            );
        }
        return inlineKeyboardButtons;
    }

}
