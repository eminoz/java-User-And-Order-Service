package com.trendyol.backend.dataAccsess.abstracts;

import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.UserDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;


public interface UserDao extends MongoRepository<User, String> {
    User findUserById(String id);


    User findOneUserByEmail(String email);

    int deleteByEmail(String email);
}

