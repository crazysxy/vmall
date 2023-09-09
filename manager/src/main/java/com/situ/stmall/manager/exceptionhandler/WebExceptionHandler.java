package com.situ.stmall.manager.exceptionhandler;


import com.situ.stmall.common.bean.RespBean;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler({BindException.class})
    public RespBean handleMethodArgumentNotValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sbf = new StringBuilder("校验失败 ");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sbf.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = sbf.toString();
        int i = msg.lastIndexOf(", ");
        msg = msg.substring(0, i);
        return RespBean.error(msg);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public RespBean handleConstraintViolationException(ConstraintViolationException e) {
        return RespBean.error(e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public RespBean ViolationException(Exception e) {
        return RespBean.error(e.getMessage());
    }
}

