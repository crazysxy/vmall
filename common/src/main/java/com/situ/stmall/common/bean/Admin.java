package com.situ.stmall.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Integer id;//主键
    private String username;//用户名
    private String password;//用户名
    private String salt;//盐
    private String phone;//电话
    private String email;//电子邮箱
    private String realname;//真实姓名
    private Integer status;//状态：0正常 1禁用
}
