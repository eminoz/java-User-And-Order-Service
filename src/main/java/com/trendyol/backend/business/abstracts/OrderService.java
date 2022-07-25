package com.trendyol.backend.business.abstracts;

import com.trendyol.backend.core.utilities.results.Result;
import com.trendyol.backend.entities.concretes.User;

public interface OrderService {

    Result createOrder(User user);
}
