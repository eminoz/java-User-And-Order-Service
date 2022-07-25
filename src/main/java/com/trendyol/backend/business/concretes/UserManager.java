package com.trendyol.backend.business.concretes;

import com.trendyol.backend.business.abstracts.UserService;
import com.trendyol.backend.core.utilities.results.*;
import com.trendyol.backend.dataAccsess.abstracts.UserDao;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public DataResult<User> getUserById(String id) {
        User userById = this.userDao.findUserById(id);
        if (userById == null) {
            final ErrorDataResult<User> user_not_found = new ErrorDataResult<>(null, "user not found");
            return user_not_found;
        }
        return new SuccessDataResult<>(userById, "user found");
    }

    @Override
    public DataResult<UserDto> getUserByEmail(String email) {
        User userByEmail = this.userDao.findOneUserByEmail(email);
        if (userByEmail == null) {
            final ErrorDataResult<UserDto> user_not_found = new ErrorDataResult<>(null, "user not found");
            return user_not_found;
        }
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

    @Override
    public Result updateEmailUserById(User user, String id) {
        User oneUserByEmail = this.userDao.findOneUserByEmail(user.getEmail());
        if (oneUserByEmail != null) {
            return new ErrorResult("already exist in this emial");
        }
        User userById = this.userDao.findUserById(id);//find user
        userById.setEmail(user.getEmail());//set user email who found
        this.userDao.save(userById);//save updated user

        //  this.userDao.save(user);// if user exist update user but not exist insert one user
        return new SuccessResult("user updated");
    }
}
