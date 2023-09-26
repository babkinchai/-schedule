package com.telegram.bot.schedule.controllers;

import com.telegram.bot.schedule.model.Users;
import com.telegram.bot.schedule.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class MainController {
    private final UsersService usersService;


    public MainController(UsersService usersService) {
        this.usersService = usersService;
    }
    @GetMapping("/get")
    String testMethod(){
        Users us1 = Users.builder()
                .id(1L)
                .userName("aa")
                .password("aaa")
                .age(12L)
                .build();
        usersService.saveUsers(Collections.singletonList(us1));
        List<Users> users = usersService.getUsers();
        return users.toString();
    }
}
