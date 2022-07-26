package com.trendyol.backend.dataAccsess.abstracts;

import com.trendyol.backend.entities.concretes.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends MongoRepository<User, String> {

    User findUserById(String id);
    User findOneUserByEmail(String email);
    int deleteByEmail(String email);

}

