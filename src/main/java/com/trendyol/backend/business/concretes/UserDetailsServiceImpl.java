package com.trendyol.backend.business.concretes;

import com.trendyol.backend.dataAccsess.abstracts.UserDao;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.jwt.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User oneUserByEmail = this.userDao.findOneUserByEmail(email);
        return JwtUserDetails.create(oneUserByEmail);
    }
    public UserDetails loadUserById(String id){
        User userById = this.userDao.findUserById(id);
        return JwtUserDetails.create(userById);
    }
}
