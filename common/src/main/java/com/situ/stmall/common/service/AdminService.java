package com.situ.stmall.common.service;

import com.situ.stmall.common.bean.Admin;

public interface AdminService {
    Admin login(String username,String password) throws Exception;
    Admin selectById(Integer id) throws Exception;
    int updateInfo(Admin admin) throws Exception;
    int updatePassword(String username ,String password,String newPassword) throws Exception;
}
