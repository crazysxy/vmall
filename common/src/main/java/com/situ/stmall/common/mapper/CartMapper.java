package com.situ.stmall.common.mapper;

import com.situ.stmall.common.bean.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int insert(Cart cart);
    int update(Cart cart);
    int delete(Integer id);
    List<Cart> selectByUserId(Integer userId);
    Cart selectById(Integer id);
    Cart selectByUserIdAndGoodsId(@Param("goodsId") Integer goodsId, @Param("userId") Integer userId);
    List<Cart> selectByIds(Integer[] ids);

}
