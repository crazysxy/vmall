package com.situ.stmall.common.mapper;

import com.situ.stmall.common.bean.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDetailMapper {
    //添加
    int insert( @Param("orderDetails") List<OrderDetail> orderDetails);
    //修改

    //查询
    List<OrderDetail> selectByOid(String oid);

    List<OrderDetail> selectByOrderId(String oid);
    OrderDetail selectById(Integer id);
    List<OrderDetail> selectByGoodsId(Integer goodsId);




}
