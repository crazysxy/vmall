package com.situ.stmall.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.stmall.common.bean.MD5Util;
import com.situ.stmall.common.bean.User;
import com.situ.stmall.common.mapper.UserMapper;
import com.situ.stmall.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean insert(String username, String password, String repassword) throws Exception {
        User user = userMapper.selectByUserame(username);

        if(!password.equals(repassword)){
            throw new Exception("两次密码输入不一致！");
        }

        if(user!=null){
            throw new Exception("该用户已存在");
        }
        //生成盐
        String salt = UUID.randomUUID().toString().substring(0, 8);
        String md5Password = MD5Util.getMD5(password + salt);
        String md5PayPassword = MD5Util.getMD5("123456" + salt);
        //对密码进行加密
         user = new User();
         user.setUsername(username);
         user.setPassword(md5Password);
         user.setSalt(salt);
         user.setPayPassword(md5PayPassword);

        return userMapper.insert(user)==1?true:false;
    }

    @Override
    public boolean del(Integer id) {
        return false;
    }

    @Override
    public boolean updatePassword(String username, String password,String newPassword) throws Exception {
        User user = userMapper.selectByUserame(username);
        if(user==null){
            throw new Exception("该用户不存在");
        }

        Integer id = user.getId();
        String salt = user.getSalt();
        String password1 = user.getPassword();
        System.out.println(password1);
        String md5 = MD5Util.getMD5(password + salt);
        System.out.println(md5);
        System.out.println(user.getPassword());
        if(!md5.equals(user.getPassword())){
            throw new Exception("密码错误，请重新输入");
        }

        user = new User();
        user.setId(id);
        user.setUsername(username);
        String newPassword1 = MD5Util.getMD5(newPassword + salt);
        user.setPassword(newPassword1);

        return userMapper.update(user)==1?true:false;
    }

    @Override
    public boolean updatePayPassword( String username,String payPassword, String newPayPassword) throws Exception {
        User user = userMapper.selectByUserame(username);
        if(user==null){
            throw new Exception("该用户不存在");
        }

        Integer id = user.getId();
        String salt = user.getSalt();
        String payPassword1 = user.getPayPassword();
        System.out.println(payPassword1);
        String md5 = MD5Util.getMD5(payPassword + salt);
        System.out.println(md5);

        if(!md5.equals(user.getPayPassword())){
            throw new Exception("密码错误，请重新输入");
        }

        user = new User();
        user.setId(id);
        user.setUsername(username);
        String newPayPassword1 = MD5Util.getMD5(newPayPassword + salt);
        user.setPayPassword(newPayPassword1);

        return userMapper.update(user)==1?true:false;
    }


    @Override
    public Integer updStatus(Integer id) throws Exception {
        User user = userMapper.selectById(id);
        if(user==null){
            throw  new Exception("该用户不存在");
        }
        Integer status=user.getStatus();
        if(status==0){
            status=1;
        }else {
            status=0;
        }
        User user1 = new User();
        user1.setId(id);
        user1.setStatus(status);
        userMapper.update(user1);
        return status;
    }

    @Override
    public User selectById(Integer id) throws Exception {
        User user = userMapper.selectById(id);
        if(user==null){
            throw  new Exception("该用户不存在");
        }
        return user;
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public PageInfo<User> selectAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> users = userMapper.selectAll();
        PageInfo<User> goodsPageInfo = new PageInfo<>(users);
        return goodsPageInfo;
    }

    @Override
    public User login(String username,String password) throws Exception {
        User user = userMapper.selectByUserame(username);
        if(user==null){
            throw new Exception("用户名或密码错误");
        }
        String salt = user.getSalt();
        String md5Password = MD5Util.getMD5(password + salt);

        if(!md5Password.equals(user.getPassword())){
            throw new Exception("用户名或密码错误");
        }
        if(user.getStatus()==1){
            throw new Exception("该用户已被禁用");
        }

        return user;
    }

    @Override
    public boolean updateInfo(User user) throws Exception {
        User user1 = userMapper.selectByUserame(user.getUsername());
        if(user1!=null&&!user1.getId().equals(user.getId())){
            throw  new Exception("该用户名已存在，请重新修改用户名");
        }
        //user.setId(user1.getId());
        System.out.println(user1);

        return userMapper.update(user)==1?true:false;
    }
}
