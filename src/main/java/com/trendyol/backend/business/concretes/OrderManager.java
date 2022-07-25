package com.trendyol.backend.business.concretes;

import com.trendyol.backend.business.abstracts.OrderService;
import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.core.utilities.results.Result;
import com.trendyol.backend.core.utilities.results.SuccessDataResult;
import com.trendyol.backend.core.utilities.results.SuccessResult;
import com.trendyol.backend.dataAccsess.abstracts.UserDao;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderManager implements OrderService {
    private UserDao userDao;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderManager(UserDao userDao, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public Result createOrder(User user) {
        User userById = this.userDao.findUserById(user.getId());
        userById.setListOfOrders(user.getListOfOrders());
        this.userDao.save(userById);
        return new SuccessResult("Order created");
    }

    @Override
    public DataResult<UserDto> updateOrder(User user) {
        User userById = this.userDao.findUserById(user.getId());//find user
        userById.setListOfOrders(user.getListOfOrders());//set user order with post orders
        User save = this.userDao.save(userById);// update user order and save
        UserDto map = this.modelMapper.map(save, UserDto.class); // map user to userDto
        return new SuccessDataResult<>(map, "updated user");
    }
}
