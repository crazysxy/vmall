package com.situ.stmall.common.mapper;

import com.situ.stmall.common.bean.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int insert(Order order);
    //逻辑删除
    int updStatus(String id);
    int update(Order order);
    Order selectById(String id);
    List<Order> selectAll();
    List<Order> selectByUserId( Integer userId);
    List<Order> selectByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") Integer[] status);
    List<Order> selectByAddrId(Integer addrId);

}
