package com.telegram.bot.schedule.services;

import com.telegram.bot.schedule.model.Schedule;

import java.util.List;

public interface ScheduleService {
    void saveUsers(List<Schedule> scheduleList);
    List<Schedule> getUsers();
}
