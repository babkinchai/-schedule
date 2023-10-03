package com.telegram.bot.schedule.services.keyboards;

import com.telegram.bot.schedule.config.DayOfWeek;
import com.telegram.bot.schedule.dto.InlineKeyboardCallbackDto;
import com.telegram.bot.schedule.repository.ScheduleRepository;
import com.telegram.bot.schedule.services.CallbackDtoService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.telegram.bot.schedule.config.MappingConst.*;

@Service
public class InlineKeyboardButtonService {

    private final CallbackDtoService callbackDtoService;
    private final ScheduleRepository scheduleRepository;

    public InlineKeyboardButtonService(CallbackDtoService callbackDtoService, ScheduleRepository scheduleRepository) {
        this.callbackDtoService = callbackDtoService;
        this.scheduleRepository = scheduleRepository;
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
        int weeks = getWeekBetween(scheduleRepository.getMaxDate());

        int nowWeek = getWeekBetween(null);

        for (Integer i = 1; i <= weeks; i++) {
            InlineKeyboardButton sub;
            if (nowWeek == i) {
                sub = new InlineKeyboardButton(geTodayButtonText(String.valueOf(i)));
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
            if (callbackDto.getDay() != null && callbackDto.getDay() == i)
                buttonText = getActiveButtonText(values.get(i).getTitle());
            else if (getWeekBetween(null) == callbackDto.getWeek()) {
                if (format.getDayOfWeek().getValue() - 1 == i) {
                    buttonText = geTodayButtonText(values.get(i).getTitle());
                }
            }
            inlineKeyboardButtons.add(
                    InlineKeyboardButton.builder()
                            .text(buttonText)
                            .callbackData(String.valueOf(callbackDtoService.createKeyboardCallbackDto(callbackDto.getName(), callbackDto.getWeek(), (long) i, DAYS).hashCode()))
                            .build());
        }
        return inlineKeyboardButtons;
    }

    public int getWeekBetween(Instant maxDate) {
        Instant minDate = scheduleRepository.getMinDate();
        int dayOfWeek = minDate.atZone(ZoneId.systemDefault()).getDayOfWeek().getValue();
        if (maxDate == null) {
            maxDate = Instant.now();
        }
        long between = ChronoUnit.DAYS.between(minDate, maxDate);
        return (int) ((between + dayOfWeek + 7) / 7);
    }

    private String geTodayButtonText(String text) {
        return "◯" + text + "◯";
    }

    private String getActiveButtonText(String text) {
        return "✅" + text + "✅";
    }
}
