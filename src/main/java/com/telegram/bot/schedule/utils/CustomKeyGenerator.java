package com.telegram.bot.schedule.utils;

import com.telegram.bot.schedule.dto.InlineKeyboardCallbackDto;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class CustomKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return String.valueOf(new InlineKeyboardCallbackDto((String) params[0], (Long) params[1], (Long) params[2], (String) params[3]).hashCode());
    }
}
