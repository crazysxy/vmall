package com.situ.stmall.common.service;

import com.situ.stmall.common.bean.Cart;

import java.util.List;

public interface CartService {
    boolean insert (Cart cart) throws Exception;
    boolean delete(Integer id,Integer userId) throws Exception;
    boolean update(Cart cart,Integer userId) throws Exception;
    List<Cart> selectByUserId(Integer userId);
    List<Cart> selectByIds(Integer[]ids,Integer userId) throws Exception;

}
