package com.situ.stmall.common.mapper;

import com.situ.stmall.common.bean.Addr;

import java.util.List;

public interface AddrMapper {
    int insert(Addr addr);
    int delete(Integer id);
    int update(Addr addr);
    Addr selectById(Integer id);
    List<Addr> selectByUserId(Integer UserId);
    int updateStatus(Integer userId);
}
