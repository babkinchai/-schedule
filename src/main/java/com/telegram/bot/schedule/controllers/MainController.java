package com.telegram.bot.schedule.controllers;

import com.telegram.bot.schedule.model.Schedule;
import com.telegram.bot.schedule.parser.Parser;
import com.telegram.bot.schedule.services.ScheduleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class MainController {
    private final ScheduleService usersService;


    public MainController(ScheduleService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/get")
    String testMethod(){
        Parser.pars(1);
        /*
        Schedule us1 = Schedule.builder()
                .id(1L)
                .userName("aa")
                .password("aaa")
                .age(12L)
                .build();
        usersService.saveUsers(Collections.singletonList(us1));
        List<Schedule> users = usersService.getUsers();
        return users.toString();

         */


        return "sad";

    }



}
