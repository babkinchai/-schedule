package com.telegram.bot.schedule.services;

import com.telegram.bot.schedule.model.Schedule;
import com.telegram.bot.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void saveUsers(List<Schedule> scheduleList) {
        scheduleRepository.saveAll(scheduleList);
    }

    @Override
    public List<Schedule> getUsers() {
        return scheduleRepository.findAll();
    }
}
