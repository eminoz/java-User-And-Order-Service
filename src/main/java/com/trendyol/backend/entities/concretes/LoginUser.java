package com.trendyol.backend.entities.concretes;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginUser {
    String email;
    String userName;
    String password;
}
