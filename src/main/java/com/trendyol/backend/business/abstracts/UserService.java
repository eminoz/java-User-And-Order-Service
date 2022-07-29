package com.trendyol.backend.business.abstracts;

import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.core.utilities.results.Result;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.UserDto;

import java.util.List;

public interface UserService {

    DataResult<User> getUserById(String id);

    DataResult<UserDto> getUserByEmail(String email);

    DataResult<List<UserDto>> getAllUser();

    Result deleteUserByEmail(String email);

    Result updateEmailUserById(User user, String id);

}
