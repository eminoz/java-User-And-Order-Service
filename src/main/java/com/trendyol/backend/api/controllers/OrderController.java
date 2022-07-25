package com.trendyol.backend.api.controllers;

import com.trendyol.backend.business.abstracts.OrderService;
import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.core.utilities.results.Result;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public Result createOrder(@RequestBody User user) {
        Result order = this.orderService.createOrder(user);
        return order;
    }

    @PutMapping("/updateUserOrders")
    public DataResult<UserDto> updateOrder(@RequestBody User user) {
        DataResult<UserDto> userDtoDataResult = this.orderService.updateOrder(user);

        return userDtoDataResult;
    }
}
