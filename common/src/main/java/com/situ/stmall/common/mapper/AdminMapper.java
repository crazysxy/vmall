package com.situ.stmall.common.mapper;

import com.situ.stmall.common.bean.Admin;

public interface AdminMapper {
    Admin selectByUsername(String username);
    Admin selectById(Integer id);
    int update( Admin admin);
}
