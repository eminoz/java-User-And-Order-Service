package com.trendyol.backend.business.concretes;

import com.trendyol.backend.business.abstracts.OrderService;
import com.trendyol.backend.core.utilities.results.*;
import com.trendyol.backend.dataAccsess.abstracts.UserDao;
import com.trendyol.backend.entities.concretes.Product;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.OrderDto;
import com.trendyol.backend.entities.dtos.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class OrderManager implements OrderService {
    private UserDao userDao;
    private final ModelMapper modelMapper;
    private ProductManager productManager;
    protected MongoTemplate mongoTemplate;


    @Autowired
    public OrderManager(UserDao userDao, ModelMapper modelMapper, ProductManager productManager) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.productManager = productManager;
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

    @Override
    public DataResult<OrderDto> getUserOrders(String id) {
        User userById = this.userDao.findUserById(id);//get user from user repo by id
        if (userById.getListOfOrders() == null || userById.getListOfOrders().isEmpty()) {
            ErrorDataResult<OrderDto> orderDtoErrorDataResult = new ErrorDataResult<>(null, "order is empty");
            return orderDtoErrorDataResult;
        }
        AtomicInteger sum = new AtomicInteger();// introduce a atomic integer to sum total price
        /*
         * I fetched product price from product repository  for multiplication with quantity per order
         *
         * */
        userById.getListOfOrders().stream().map(e -> {
            DataResult<Product> byProductName = this.productManager.getByProductName(e.getProductId());
            Float price = byProductName.getData().getPrice();
            float v = e.getQuantity() * price;
            sum.addAndGet((int) v);
            return sum;
        }).collect(Collectors.toList());
        userById.setTotalPrice(sum.get()); //set current user price
        OrderDto map = this.modelMapper.map(userById, OrderDto.class); // map user to orderDto
        SuccessDataResult<OrderDto> user_orders = new SuccessDataResult<>(map, "user orders");
        return user_orders;
    }

    public void addOrder(User user) {
        User id = mongoTemplate.findOne(Query.query(Criteria.where("id").is(user.getId())), User.class);
        System.out.println(id);
        user.getListOfOrders().forEach((j) -> {
            System.out.println(j);
            mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(user.getId())), new Update().push("listOfOrders", j), User.class);
        });
    }


}


