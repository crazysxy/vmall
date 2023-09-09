package com.situ.stmall.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPic implements Serializable {
    private Integer id;
    private String url;
    private Integer goodsId;

}
