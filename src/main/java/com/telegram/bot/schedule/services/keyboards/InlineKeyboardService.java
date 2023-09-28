package com.telegram.bot.schedule.services.keyboards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.benmanes.caffeine.cache.Cache;
import com.telegram.bot.schedule.dto.InlineKeyboardCallbackDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
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

    private final InlineKeyboardButtonService buttonService;
    private final CaffeineCacheManager caffeineCacheManager;

    public InlineKeyboardService(InlineKeyboardButtonService buttonService, CacheManager caffeineCacheManager) {
        this.buttonService = buttonService;
        this.caffeineCacheManager = (CaffeineCacheManager) caffeineCacheManager;
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
    public List<List<InlineKeyboardButton>> getDayButtons(CallbackQuery callbackQuery) {
        List<InlineKeyboardButton> dayButtonList = buttonService.dayButtonList(getDataFromCallback(callbackQuery));
        List<InlineKeyboardButton> inlineKeyboardButtons = Collections.singletonList(
                buttonService.getReturnDayButton(getDataFromCallback(callbackQuery))
        );
        return Arrays.asList(dayButtonList.subList(0, 3), dayButtonList.subList(3, 6), inlineKeyboardButtons);
    }

    @NotNull
    public List<List<InlineKeyboardButton>> getTodayDayButtons(CallbackQuery callbackQuery) {
        InlineKeyboardCallbackDto dataFromCallback = getDataFromCallback(callbackQuery);
        dataFromCallback.setWeek((long) buttonService.getNowWeek());
        List<InlineKeyboardButton> dayButtonList = buttonService.dayButtonList(dataFromCallback);
        List<InlineKeyboardButton> inlineKeyboardButtons = Collections.singletonList(
                buttonService.getReturnDayButton(getDataFromCallback(callbackQuery))
        );
        return Arrays.asList(dayButtonList.subList(0, 3), dayButtonList.subList(3, 6), inlineKeyboardButtons);
    }

    public InlineKeyboardCallbackDto getDataFromCallback(CallbackQuery callbackQuery) {
        CaffeineCache cache = (CaffeineCache) caffeineCacheManager.getCache("callbackDto");
        Cache<Object, Object> caffeine = cache.getNativeCache();
        return (InlineKeyboardCallbackDto) caffeine.asMap().get(callbackQuery.getData());
    }
}
