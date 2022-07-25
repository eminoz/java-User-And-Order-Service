package com.trendyol.backend.business.concretes;

import com.trendyol.backend.business.abstracts.OrderService;
import com.trendyol.backend.core.utilities.results.Result;
import com.trendyol.backend.core.utilities.results.SuccessResult;
import com.trendyol.backend.dataAccsess.abstracts.UserDao;
import com.trendyol.backend.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderManager implements OrderService {
    private UserDao userDao;

    @Autowired
    public OrderManager(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Result createOrder(User user) {
        User userById = this.userDao.findUserById(user.getId());
        userById.setListOfOrders(user.getListOfOrders());
        this.userDao.save(userById);
        return new SuccessResult("Order created");
    }
}
