package com.telegram.bot.schedule.services.textmessage;

import com.telegram.bot.schedule.services.BotRequestMessage;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class StartMessageRequestImpl extends BotRequestMessage {

    private final String START_MESSAGE="Привет!\n" +
            "Я бот, который поможет вам найти расписание любого преподавателя.\n" +
            "\n" +
            "Напишите мне его фамилию, примеры:\n" +
            "\n" +
            "Иванов\n" +
            "Карпов Д.А.\n" +
            "\n" +
            "Теперь доступен поиск по аудиториям и группам!\n" +
            "Для этого напишите слово ауд и номер аудитории, примеры:\n" +
            "\n" +
            "ауд И-202\n" +
            "ауд В-108\n" +
            "ауд И-202-а\n" +
            "\n" +
            "Для поиска по группам напишите название группы, примеры:\n" +
            "\n" +
            "ИВБО-20-23\n" +
            "КТСО-01-23";
    @Override
    public void sendRequestMessage(Message message) {
        try {
            execute(new SendMessage(message.getChatId().toString(),START_MESSAGE));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
