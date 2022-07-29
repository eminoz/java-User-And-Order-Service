package com.trendyol.backend.business.abstracts;

import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.entities.concretes.LoginUser;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.LoginUserDto;
import com.trendyol.backend.entities.dtos.UserDto;

import java.util.Optional;

public interface AuthService {
    DataResult<UserDto> createUser(User user);
    DataResult<LoginUserDto> login(LoginUser user);
}
