package com.trendyol.backend.business.concretes;

import com.trendyol.backend.business.abstracts.AuthService;
import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.core.utilities.results.ErrorDataResult;
import com.trendyol.backend.core.utilities.results.Result;
import com.trendyol.backend.core.utilities.results.SuccessDataResult;
import com.trendyol.backend.dataAccsess.abstracts.UserDao;
import com.trendyol.backend.entities.concretes.LoginUser;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.LoginUserDto;
import com.trendyol.backend.entities.dtos.UserDto;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;


@Service
public class AuthManager implements AuthService {
    private UserDao userDao;
    private ModelMapper modelMapper;


    public AuthManager(UserDao userDao, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;

    }

    @Override
    public DataResult<UserDto> createUser(User user) {

        String email = user.getEmail();
        User userByEmail = this.userDao.findOneUserByEmail(email);
        if (userByEmail != null) {
            final ErrorDataResult<UserDto> user_already_exist = new ErrorDataResult<>(null, "user already exist");
            return user_already_exist;
        }
        // user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        User insert = this.userDao.insert(user);
        UserDto userDto = this.modelMapper.map(insert, UserDto.class);//Burada kayıt frontende döneceğimiz verilere çevirmiş oluyoruz
        return new
                SuccessDataResult<>(userDto, "user created");
    }

    @Override
    public DataResult<LoginUserDto> login(LoginUser user) {
        return null;
    }


}
