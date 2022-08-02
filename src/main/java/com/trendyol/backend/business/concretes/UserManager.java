package com.trendyol.backend.business.concretes;

import com.trendyol.backend.business.abstracts.UserService;
import com.trendyol.backend.core.utilities.results.*;
import com.trendyol.backend.dataAccsess.abstracts.UserDao;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.UserDto;
import com.trendyol.backend.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public DataResult<UserDto> getUserById(String id) {
        User userById = this.userDao.findUserById(id);
        if (userById == null) {
            throw new NotFoundException("not found by this " + id + " id");
        }
        UserDto map = this.modelMapper.map(userById, UserDto.class);
        return new SuccessDataResult<>(map, "user found");
    }

    @Override
    public DataResult<UserDto> getUserByEmail(String email) {
        Optional<User> userByEmail = Optional.ofNullable(this.userDao.findOneUserByEmail(email));
        User user = userByEmail.orElseThrow(() -> new NotFoundException("user not found with this " + email + " email"));
        UserDto map = this.modelMapper.map(user, UserDto.class);
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
            throw new NotFoundException("User could not delete");
        }
        SuccessResult successResult = new SuccessResult("User deleted");
        return successResult;
    }

    @Override
    public Result updateEmailUserById(User user, String id) {
        User oneUserByEmail = this.userDao.findOneUserByEmail(user.getEmail());
        if (oneUserByEmail != null) {
            return new ErrorResult("already exist in this email");
        }
        User userById = this.userDao.findUserById(id);//find user
        userById.setEmail(user.getEmail());//set user email who found
        this.userDao.save(userById);//save updated user

        //  this.userDao.save(user);// if user exist update user but not exist insert one user
        return new SuccessResult("user updated");
    }
}
