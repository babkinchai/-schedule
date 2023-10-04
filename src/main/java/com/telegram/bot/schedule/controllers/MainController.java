package com.telegram.bot.schedule.controllers;

import com.telegram.bot.schedule.parser.Parser;
import com.telegram.bot.schedule.services.ScheduleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final ScheduleService usersService;
    private final Parser parser;


    public MainController(ScheduleService usersService, Parser parser) {
        this.usersService = usersService;
        this.parser = parser;
    }

    @GetMapping("/get")
    String testMethod(){
        parser.pars(1);
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
