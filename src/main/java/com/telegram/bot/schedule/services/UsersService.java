package com.telegram.bot.schedule.services;

import com.telegram.bot.schedule.model.Users;

import java.util.List;

public interface UsersService {
    void saveUsers(List<Users> usersList);
    List<Users> getUsers();
}
