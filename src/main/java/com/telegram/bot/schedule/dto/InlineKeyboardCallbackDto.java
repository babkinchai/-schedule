package com.telegram.bot.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class InlineKeyboardCallbackDto {
    private String name;
    private Long week;
    private Long day;
    private String method;
}
