package com.telegram.bot.schedule.services.callback;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public abstract class CallbackService extends DefaultAbsSender {

    @Value("${bot.token}")
    private String token;

    protected CallbackService(DefaultBotOptions options) {
        super(options);
    }

    abstract public void getCallback(CallbackQuery callbackQuery);

    @Override
    public String getBotToken() {
        return token;
    }
}
