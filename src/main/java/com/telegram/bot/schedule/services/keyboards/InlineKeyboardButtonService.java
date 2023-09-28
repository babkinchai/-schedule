package com.telegram.bot.schedule.services.keyboards;

import com.telegram.bot.schedule.config.DayOfWeek;
import com.telegram.bot.schedule.dto.InlineKeyboardCallbackDto;
import com.telegram.bot.schedule.services.CallbackDtoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.telegram.bot.schedule.config.MappingConst.*;

@Service
public class InlineKeyboardButtonService {

    private final CallbackDtoService callbackDtoService;


    public InlineKeyboardButtonService(CallbackDtoService callbackDtoService) {
        this.callbackDtoService = callbackDtoService;
    }

    public InlineKeyboardButton getTodayButton(String message) {
        return InlineKeyboardButton.builder()
                .text("Сегодня")
                .callbackData(String.valueOf(callbackDtoService.createKeyboardCallbackDto(message, null, null, TODAY).hashCode()))
                .build();
    }

    public InlineKeyboardButton getTomorrowButton(String message) {
        return InlineKeyboardButton.builder()
                .text("Завтра")
                .callbackData(String.valueOf(callbackDtoService.createKeyboardCallbackDto(message, null, null, TOMORROW).hashCode()))
                .build();
    }

    public InlineKeyboardButton getReturnWeekButton(String message) {
        return InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData(String.valueOf(callbackDtoService.createKeyboardCallbackDto(message, null, null, WEEK_RETURN).hashCode()))
                .build();
    }

    public InlineKeyboardButton getReturnDayButton(InlineKeyboardCallbackDto message) {
        return InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData(String.valueOf(callbackDtoService.createKeyboardCallbackDto(message.getName(), message.getWeek(), null, DAY_RETURNS).hashCode()))
                .build();
    }

    @NotNull
    public List<InlineKeyboardButton> getWeekButtons(String message) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (Integer i = 1; i < 7; i++) {
            InlineKeyboardButton sub;
            if (getNowWeek() == i) {
                sub = new InlineKeyboardButton(getActiveButtonText(String.valueOf(i)));
            } else
                sub = new InlineKeyboardButton(i.toString());
            sub.setCallbackData(String.valueOf(callbackDtoService.createKeyboardCallbackDto(message, i.longValue(), null, WEEK).hashCode()));
            buttons.add(sub);
        }
        return buttons;
    }


    @NotNull
    public List<InlineKeyboardButton> dayButtonList(InlineKeyboardCallbackDto callbackDto) {

        LocalDate format = LocalDate.now();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        List<DayOfWeek> values = Arrays.stream(DayOfWeek.values()).toList();
        for (int i = 0; i < values.size(); i++) {
            String buttonText = values.get(i).getTitle();
            if (callbackDto.getDay() == null) {
                buttonText = values.get(i).getTitle();
            } else if (callbackDto.getDay() == i)
                buttonText = getActiveButtonText(values.get(i).getTitle());
            else if(getNowWeek() == callbackDto.getWeek()){
                if(format.getDayOfWeek().getValue() - 1==i){
                    buttonText = getActiveButtonText(values.get(i).getTitle());
                }
            }
            inlineKeyboardButtons.add(
                    InlineKeyboardButton.builder()
                            .text(buttonText)
                            .callbackData(String.valueOf(callbackDtoService.createKeyboardCallbackDto(callbackDto.getName(), callbackDto.getWeek(), (long) i, DAYS).hashCode()))
                            .build()
            );
        }
        return inlineKeyboardButtons;
    }

    public int getNowWeek() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse("01.09.2023", formatter);
        int dayOfWeek = startDate.getDayOfWeek().getValue();
        LocalDate format = LocalDate.now();
        Period period = Period.between(startDate, format);
        return (period.getDays() + dayOfWeek + 7) / 7;
    }

    private String getActiveButtonText(String text) {
        return "◯" + text + "◯";
    }
}
