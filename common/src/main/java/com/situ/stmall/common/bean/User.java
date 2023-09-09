package com.situ.stmall.common.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;//主键
    private String username;//用户名
    private String password;//密码
    private String salt;//盐
    private String phone;//电话
    private String email;//电子邮箱
    private String realname;//真实姓名
    private String gender;//性别
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT")
    private  Date birthday;//生日
    private String idcard;//身份证
    @DecimalMin(value="0.00",message="余额最低为0")
    @DecimalMax(value="999999",message="余额最高为9999999")
    private BigDecimal money; //余额
    private String payPassword; //支付密码
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonFormat(pattern = "yyyy-MM-dd- HH:mm:ss",timezone = "GMT")
    private Date regTime;//注册时间
    private Integer status;//状态
    private Integer interested;

}
