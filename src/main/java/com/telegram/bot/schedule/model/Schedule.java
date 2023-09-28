package com.telegram.bot.schedule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Schedule {
    @Id
    @Column(name = "id_users")
    private Long id;
    private Date date;
    private String time;
    private String group;
    private String teacher;
    private String subject;
}
