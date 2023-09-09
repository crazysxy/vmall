package com.situ.stmall.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderDetail {
    private Integer id;//主键
    private Integer count;//数量
    private BigDecimal price;//价格
    private  Integer goodsId;//商品id
    private String orderId; //订单id
    private Goods goods;//商品
    private BigDecimal totalPrice;
    private Order order;

}
