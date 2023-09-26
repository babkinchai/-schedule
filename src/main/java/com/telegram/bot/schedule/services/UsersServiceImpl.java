package com.telegram.bot.schedule.services;

import com.telegram.bot.schedule.model.Users;
import com.telegram.bot.schedule.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void saveUsers(List<Users> usersList) {
        usersRepository.saveAll(usersList);
    }

    @Override
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }
}
