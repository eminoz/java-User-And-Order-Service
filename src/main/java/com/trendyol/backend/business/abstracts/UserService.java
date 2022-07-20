package com.trendyol.backend.business.abstracts;

import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.core.utilities.results.Result;
import com.trendyol.backend.core.utilities.results.SuccessDataResult;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.UserDto;

import java.util.List;

public interface UserService {
    DataResult<User> add(User user);

    DataResult<UserDto> getUserById(String id);

    DataResult<UserDto> getUserByEmail(String email);
}
