package com.situ.stmall.manager.controller;


import com.situ.stmall.common.bean.RespBean;
import com.situ.stmall.common.bean.User;
import com.situ.stmall.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/UserController")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/selectAll")
    public RespBean selectAll(Integer pageNum, Integer pageSize){
        Object result;
        if(pageNum!=null){
            result = userService.selectAll(pageNum, pageSize);
        }else {
            result = userService.selectAll();
        }
        return  RespBean.ok("查询成功", result);
    }

    //根据id查询
    @GetMapping("/one/{id}")
    public RespBean findByid(@PathVariable("id")Integer id) throws Exception {
        User user = userService.selectById(id);
        return  RespBean.ok("查询成功",user);

    }

    @DeleteMapping("/{id}")
    public RespBean deleteById(@PathVariable("id")Integer id) throws Exception {
        Integer resp = userService.updStatus(id);
        String msg;
        if (resp==1){
            msg="已禁用~";
        }else {
            msg="已启用~";
        }
        return  RespBean.ok(msg);

    }


}
