package com.telegram.bot.schedule.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.telegram.bot.schedule.services.BotRequestMessage;
import com.telegram.bot.schedule.services.MyLongPulling;
import com.telegram.bot.schedule.services.callback.*;
import com.telegram.bot.schedule.services.textmessage.SearchScheduleMessageImpl;
import com.telegram.bot.schedule.services.textmessage.StartMessageRequestImpl;
import com.telegram.bot.schedule.utils.CustomKeyGenerator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.HashMap;

import static com.telegram.bot.schedule.config.MappingConst.*;

@Configuration
public class TelegramBotApiConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(MyLongPulling myLongPulling) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(myLongPulling);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return botsApi;
    }

    @Bean
    public MyLongPulling myLongPulling(StartMessageRequestImpl startMessageRequest, WeekCallbackServiceImpl weekCallbackServiceInterface,
                                       SearchScheduleMessageImpl searchScheduleMessage, DayReturnCallbackServiceImpl dayReturnCallbackService,
                                       DayCallbackServiceImpl dayCallbackService, TodayCallbackServiceImpl todayCallbackService,
                                       TomorrowCallbackServiceImpl tomorrowCallbackService
    ) {
        HashMap<String, BotRequestMessage> botRequestMessageHashMap = new HashMap<>();
        botRequestMessageHashMap.put(START, startMessageRequest);
        botRequestMessageHashMap.put(OTHER, searchScheduleMessage);
        HashMap<String, CallbackService> callbackServiceHashMap = new HashMap<>();
        callbackServiceHashMap.put(WEEK, weekCallbackServiceInterface);
        callbackServiceHashMap.put(DAY_RETURNS, dayReturnCallbackService);
        callbackServiceHashMap.put(DAYS, dayCallbackService);
        callbackServiceHashMap.put(TODAY, todayCallbackService);
        callbackServiceHashMap.put(TOMORROW, tomorrowCallbackService);
        return new MyLongPulling(botRequestMessageHashMap, callbackServiceHashMap, defaultCacheManager());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean("defaultCacheManager")
    public CacheManager defaultCacheManager() {
        var cacheManager = new CaffeineCacheManager("callbackDto");
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    public Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .maximumSize(5000);
    }

    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new CustomKeyGenerator();
    }
/*    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("directory"),
                new ConcurrentMapCache("addresses")));
        cacheManager.initializeCaches();
        return cacheManager;
    }*/

}