package com.telegram.bot.schedule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "SCHEDULE")
public class Schedule {
    @Id
    @Column(name = "OBJECT_ID")
    private Long id;
    @Column(name = "LESSON_DATE")
    private Instant date;
    @Column(name = "LESSON_TIME")
    private String time;
    @Column(name = "GROUP_NAME")
    private String group;
    private String teacher;
    private String subject;
}
