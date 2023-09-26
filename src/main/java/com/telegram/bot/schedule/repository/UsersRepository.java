package com.telegram.bot.schedule.repository;

import com.telegram.bot.schedule.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository <Users, Long> {

}
