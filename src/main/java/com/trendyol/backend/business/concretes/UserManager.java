package com.trendyol.backend.business.concretes;

import com.trendyol.backend.business.abstracts.UserService;
import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.core.utilities.results.SuccessDataResult;
import com.trendyol.backend.dataAccsess.abstracts.UserDao;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserManager implements UserService {
    private UserDao userDao;

    @Autowired
    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public DataResult<User> add(User user) {
        return new
                SuccessDataResult<>(this.userDao.insert(user), "user created");
    }

    @Override
    public DataResult<UserDto> getUserById(String id) {

        return new SuccessDataResult<>(this.userDao.findUserById(id), "user found");
    }

    @Override
    public DataResult<UserDto> getUserByEmail(String email) {
        SuccessDataResult<UserDto> user_found_by_email = new SuccessDataResult<>(this.userDao.findUserByEmail(email), "user found by email");
        return user_found_by_email;
    }
}
