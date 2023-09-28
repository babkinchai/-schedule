package com.telegram.bot.schedule.services;

import com.telegram.bot.schedule.dto.InlineKeyboardCallbackDto;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class CallbackDtoService {

    @CachePut(cacheNames = "callbackDto", keyGenerator = "customKeyGenerator")
    public InlineKeyboardCallbackDto createKeyboardCallbackDto(String name, Long week, Long day, String method){
        return new InlineKeyboardCallbackDto(name, week, day, method);
    }
}
