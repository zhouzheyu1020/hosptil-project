package com.hospital.service;

import com.hospital.entity.User;

public interface UserService {
    User login(String username, String password);

    // 注册方法：返回 true 表示注册成功，false 表示用户名已存在
    boolean register(User user);
}