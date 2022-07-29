package com.trendyol.backend.entities.dtos;

import com.trendyol.backend.entities.concretes.ListOfOrder;
import lombok.Data;

import java.util.List;


@Data
public class OrderDto {

    private float totalPrice;
    private List<ListOfOrder> listOfOrders;
}
