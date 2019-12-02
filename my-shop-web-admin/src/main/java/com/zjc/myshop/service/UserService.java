package com.zjc.myshop.service;


import com.zjc.myshop.domain.User;

public interface UserService {
    public User login(String email, String password);
}
