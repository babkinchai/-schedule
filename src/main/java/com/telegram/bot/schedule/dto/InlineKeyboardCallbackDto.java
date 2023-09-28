package com.telegram.bot.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Cacheable(cacheNames={"callbackDto"})
public class InlineKeyboardCallbackDto {
    private String name;
    private Long week;
    private Long day;
    private String method;
}
