package com.situ.stmall.common.service;

import com.github.pagehelper.PageInfo;
import com.situ.stmall.common.bean.Order;
import com.situ.stmall.common.bean.OrderDetail;
import com.situ.stmall.common.bean.User;

import java.util.List;

public interface OrderService {
    int insert( Order order ,Integer[] cartsId) throws Exception;
    List<Order> selectAll();
    PageInfo<Order> selectAll(Integer pageNum, Integer pageSize);
    Order selectById(String id) throws Exception;
    int update(Order order, User user) throws Exception;
    int updStatus(String id) throws Exception;
    List<OrderDetail> findByOid(String oid);
    OrderDetail findById(Integer id) throws Exception;
   List<Integer> getOrderDrawInfo();
   void pay(String orderId,Integer userId,String paypwd) throws Exception;
   List<Order> selectByUserId(Integer userId);
    List<Order> selectByUserIdAndStatus(Integer userId, Integer[]status);

   int cancel(String orderId);

    List<Order> selectByAddrId(Integer addrId);




}
