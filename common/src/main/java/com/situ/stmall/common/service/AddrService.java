package com.situ.stmall.common.service;

import com.situ.stmall.common.bean.Addr;

import java.util.List;

public interface AddrService {
    int insert(Addr addr);
    int insert(Addr addr,Integer userId);
    int delete(Integer id) throws Exception;
    int update(Addr addr);
    Addr selectById(Integer id);
    List<Addr> selectByUserId(Integer UserId);
    int updateStatus(Integer addrId);

}
