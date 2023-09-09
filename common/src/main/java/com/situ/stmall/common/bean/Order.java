package com.situ.stmall.common.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;//主键
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;//创建时间
    private Integer userId;//用户id
    private Integer addrId;//地址id
    private Integer status;//订单状态：0：未发货 1：已发货 2：已收到
    private User user;//用户
    private Addr addr;//地址
    private List<OrderDetail> orderDetails;//订单内的详细内容
    private String userName;

}
