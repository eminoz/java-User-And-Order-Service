package com.trendyol.backend.business.abstracts;

import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.core.utilities.results.Result;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.UserDto;

public interface OrderService {

    Result createOrder(User user);
    DataResult<UserDto> updateOrder(User user);
}
