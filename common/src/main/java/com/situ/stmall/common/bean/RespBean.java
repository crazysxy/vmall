package com.situ.stmall.common.bean;

import lombok.Data;

/**
 * 表示返回结果的bean
 */
@Data
public class RespBean {
    //状态码 10000-成功 10001-失败
    private Integer code;
    //返回的附件信息
    private String msg;
    //返回的数据
    private Object data;

    public static RespBean ok() {
        RespBean respBean = new RespBean();
        respBean.setCode(10000);

        return respBean;
    }

    public static RespBean ok(String msg) {
        RespBean respBean = new RespBean();
        respBean.setCode(10000);
        respBean.setMsg(msg);

        return respBean;
    }

    public static RespBean ok(Object data) {
        RespBean respBean = new RespBean();
        respBean.setCode(10000);
        respBean.setData(data);

        return respBean;
    }

    public static RespBean ok(String msg, Object data) {
        RespBean respBean = new RespBean();
        respBean.setCode(10000);
        respBean.setMsg(msg);
        respBean.setData(data);

        return respBean;
    }

    public static RespBean error() {
        RespBean respBean = new RespBean();
        respBean.setCode(10001);

        return respBean;
    }

    public static RespBean error(String msg) {
        RespBean respBean = new RespBean();
        respBean.setCode(10001);
        respBean.setMsg(msg);

        return respBean;
    }

    public static RespBean error(String msg, Object data) {
        RespBean respBean = new RespBean();
        respBean.setCode(10001);
        respBean.setMsg(msg);
        respBean.setData(data);

        return respBean;
    }
}
