package com.situ.stmall.common.mapper;

import com.situ.stmall.common.bean.User;

import java.util.List;

public interface UserMapper {
    int insert(User user);
    int del(Integer id);
    int updStatus(Integer id);
    int update(User user);
    User selectById(Integer id);
    List<User> selectAll();
    String findName(Integer id);
    User selectByUserame(String username);
}
