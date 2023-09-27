package com.telegram.bot.schedule.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegram.bot.schedule.services.*;
import com.telegram.bot.schedule.services.callback.CallbackService;
import com.telegram.bot.schedule.services.callback.DayReturnCallbackServiceImpl;
import com.telegram.bot.schedule.services.callback.WeekCallbackServiceImpl;
import com.telegram.bot.schedule.services.textmessage.SearchScheduleMessageImpl;
import com.telegram.bot.schedule.services.textmessage.StartMessageRequestImpl;
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
                                       SearchScheduleMessageImpl searchScheduleMessage, DayReturnCallbackServiceImpl dayReturnCallbackService){
        HashMap<String, BotRequestMessage> botRequestMessageHashMap=new HashMap<>();
        botRequestMessageHashMap.put(START, startMessageRequest);
        botRequestMessageHashMap.put(OTHER,searchScheduleMessage);
        HashMap<String, CallbackService> callbackServiceHashMap=new HashMap<>();
        callbackServiceHashMap.put(WEEK, weekCallbackServiceInterface);
        callbackServiceHashMap.put(DAY_RETURNS, dayReturnCallbackService);
        return new MyLongPulling(botRequestMessageHashMap, callbackServiceHashMap);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}