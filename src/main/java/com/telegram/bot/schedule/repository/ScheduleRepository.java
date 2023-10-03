package com.telegram.bot.schedule.repository;

import com.telegram.bot.schedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;

@Repository
public interface ScheduleRepository extends JpaRepository <Schedule, Long> {
    @Query("SELECT MAX (sch.date) from Schedule as sch")
    public Instant getMaxDate();

    @Query("SELECT MIN (sch.date) from Schedule as sch")
    public Instant getMinDate();
}
