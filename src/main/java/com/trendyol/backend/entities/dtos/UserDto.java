package com.trendyol.backend.entities.dtos;

import com.trendyol.backend.entities.concretes.ListOfOrder;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private List<ListOfOrder> listOfOrders;
}
