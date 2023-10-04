package com.telegram.bot.schedule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Schedule {
    @Id
    @Column(name = "id_users")
    @GeneratedValue
    private Long id;
    private Date date;
    private String time;
    private String group;
    private String teacher;
    private String subject;
}
