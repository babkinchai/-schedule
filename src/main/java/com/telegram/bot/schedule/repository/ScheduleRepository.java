package com.telegram.bot.schedule.repository;

import com.telegram.bot.schedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository <Schedule, Long> {

}
