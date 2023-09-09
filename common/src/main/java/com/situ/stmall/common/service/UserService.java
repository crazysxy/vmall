package com.situ.stmall.common.service;

import com.github.pagehelper.PageInfo;
import com.situ.stmall.common.bean.User;

import java.util.List;

public interface UserService {
    boolean insert(String username,String password,String repassword) throws Exception;
    boolean del(Integer id);
    boolean updatePassword(String username,String newPassword,String password) throws Exception;
    boolean updatePayPassword( String payPassword,  String newPayPassword,  String reNewPayPassword) throws Exception;
    Integer updStatus(Integer id) throws Exception;
    User selectById(Integer id) throws Exception;
    List<User> selectAll();
    PageInfo<User> selectAll(Integer pageNum, Integer pageSize);
    User login(String username,String password) throws Exception;

    boolean updateInfo(User user) throws Exception;

}
