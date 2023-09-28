package com.telegram.bot.schedule.services;

import com.github.benmanes.caffeine.cache.Cache;
import com.telegram.bot.schedule.dto.InlineKeyboardCallbackDto;
import com.telegram.bot.schedule.services.callback.CallbackService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;

import static com.telegram.bot.schedule.config.MappingConst.OTHER;


@NoArgsConstructor
public class MyLongPulling extends TelegramLongPollingBot {

    public static Logger logger = LoggerFactory.getLogger(MyLongPulling.class);

    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String botName;

    private HashMap<String, BotRequestMessage> botRequestMessageHashMap;
    private HashMap<String, CallbackService> callbackServiceHashMap;
    private CaffeineCacheManager caffeineCacheManager;
    public MyLongPulling(HashMap<String, BotRequestMessage> botRequestMessageHashMap, HashMap<String, CallbackService> callbackServiceHashMap, CacheManager caffeineCacheManager) {
        this.botRequestMessageHashMap = botRequestMessageHashMap;
        this.callbackServiceHashMap = callbackServiceHashMap;
        this.caffeineCacheManager = (CaffeineCacheManager) caffeineCacheManager;
    }


    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(update -> {
            if (update.hasMessage()) {
                messageRequest(update.getMessage());
            } else if (update.hasCallbackQuery()) {
                callbackQueryRequest(update.getCallbackQuery());
            }
        });
    }

    private void callbackQueryRequest(CallbackQuery callbackQuery) {
        CaffeineCache cache = (CaffeineCache) caffeineCacheManager.getCache("callbackDto");
        Cache<Object, Object> caffeine = cache.getNativeCache();
        InlineKeyboardCallbackDto inlineKeyboardCallbackDto= (InlineKeyboardCallbackDto) caffeine.asMap().get(callbackQuery.getData());
        if (callbackServiceHashMap.containsKey(inlineKeyboardCallbackDto.getMethod())) {
            callbackServiceHashMap.get(inlineKeyboardCallbackDto.getMethod()).getCallback(callbackQuery);
        } else {
            callbackServiceHashMap.get(OTHER).getCallback(callbackQuery);
        }

    }

    private void messageRequest(Message message) {
        if (botRequestMessageHashMap.containsKey(message.getText())) {
            botRequestMessageHashMap.get(message.getText()).sendRequestMessage(message);
        } else
            botRequestMessageHashMap.get(OTHER).sendRequestMessage(message);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
