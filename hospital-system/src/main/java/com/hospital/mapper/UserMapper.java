package com.hospital.mapper;

import com.hospital.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    // 根据用户名查询用户（登录用）
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);

    // 新增用户（注册用）
    @Insert("INSERT INTO user (username, password, role) VALUES (#{username}, #{password}, 'USER')")
    int insert(User user);
}