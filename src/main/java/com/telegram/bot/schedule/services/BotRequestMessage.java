package com.telegram.bot.schedule.services;


import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class BotRequestMessage extends DefaultAbsSender {

    @Value("${bot.token}")
    private String token;

    protected BotRequestMessage() {
        super(new DefaultBotOptions());
    }

    public abstract void sendRequestMessage(Message message);

    @Override
    public String getBotToken() {
        return token;
    }
}
