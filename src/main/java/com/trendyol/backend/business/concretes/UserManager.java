package com.trendyol.backend.business.concretes;

import com.trendyol.backend.business.abstracts.UserService;
import com.trendyol.backend.core.utilities.results.*;
import com.trendyol.backend.dataAccsess.abstracts.UserDao;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserManager implements UserService {
    private UserDao userDao;
    private final ModelMapper modelMapper;

    @Autowired
    public UserManager(UserDao userDao, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }


    @Override
    public DataResult<UserDto> add(User user) {
        String email = user.getEmail();
        User userByEmail = this.userDao.findOneUserByEmail(email);
        if (userByEmail != null) {
            final ErrorDataResult<UserDto> user_alredy_exist = new ErrorDataResult<>(null, "user alredy exist");
            return user_alredy_exist;
        }
        final User insert = this.userDao.insert(user);
        final UserDto userDto = this.modelMapper.map(insert, UserDto.class);//Burada kayıt frontende döneceğimiz verilere çevirmiş oluyoruz
        return new
                SuccessDataResult<>(userDto, "user created");
    }

    @Override
    public DataResult<UserDto> getUserById(String id) {
        return new SuccessDataResult<>(this.userDao.findUserById(id), "user found");
    }

    @Override
    public DataResult<UserDto> getUserByEmail(String email) {
        User userByEmail = this.userDao.findOneUserByEmail(email);

        UserDto map = this.modelMapper.map(userByEmail, UserDto.class);
        SuccessDataResult<UserDto> user_found_by_email = new SuccessDataResult<>(map, "user found by email");
        return user_found_by_email;


    }

    @Override
    public DataResult<List<UserDto>> getAllUser() {
        List<User> all = this.userDao.findAll();
        final List<UserDto> userDtos = all.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());//Bütün userları dtoya çevirdik
        final SuccessDataResult<List<UserDto>> listSuccessDataResult = new SuccessDataResult<>(userDtos, "user found");
        return listSuccessDataResult;
    }

    @Override
    public Result deleteUserByEmail(String email) {
        int s = this.userDao.deleteByEmail(email);
        if (s == 0) {
            ErrorResult errorResult = new ErrorResult("User could not delete");
            return errorResult;
        }
        SuccessResult successResult = new SuccessResult("User deleted");
        return successResult;
    }
}
