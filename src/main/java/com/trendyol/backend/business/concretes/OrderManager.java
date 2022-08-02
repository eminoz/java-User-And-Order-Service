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

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


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
        AtomicInteger sum = new AtomicInteger();// introduce a atomic integer to sum total price
        /*
         * I fetched product price from product repository  for multiplication with quantity per order
         */
        HashMap<String, Float> ProductMap = new HashMap<>();
        DataResult<List<Product>> AllProduct = this.productManager.getAll();

        AllProduct.getData().stream().forEach(e -> {
            ProductMap.put(e.getProductName(), e.getPrice());
        });
        userById.getListOfOrders().stream().forEach(e -> {
            Float aFloat = ProductMap.get(e.getProductId());
            float v = e.getQuantity() * aFloat;
            sum.addAndGet((int) v);

        });
        userById.setTotalPrice(sum.get()); //set current user price
        OrderDto map = this.modelMapper.map(userById, OrderDto.class); // map user to orderDto
        return new SuccessDataResult<>(map, "user orders in here");


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


