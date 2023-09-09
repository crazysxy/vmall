package com.situ.stmall.common.service.impl;

import com.situ.stmall.common.bean.Admin;
import com.situ.stmall.common.bean.MD5Util;
import com.situ.stmall.common.mapper.AdminMapper;
import com.situ.stmall.common.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin login(String username, String password) throws Exception {
        Admin admin = adminMapper.selectByUsername(username);
        if(admin==null){
            throw  new Exception("该用户不存在");
        }
        String md5 = MD5Util.getMD5(password + admin.getSalt());
        if(!admin.getPassword().equals(md5)){
            throw new Exception("密码错误~~");
        }
        return admin;
    }

    @Override
    public Admin selectById(Integer id) throws Exception {
        Admin admin = adminMapper.selectById(id);
        if (admin==null){
            throw  new Exception("该用户不存在");
        }
        return admin;
    }

    @Override
    public int updateInfo(Admin admin) throws Exception {
        Admin admin1 = adminMapper.selectByUsername(admin.getUsername());
        if(admin1!=null && admin1.getId()!=admin.getId()){
            throw new Exception("用户名重复，修改失败");
        }
        return adminMapper.update(admin);
    }

    @Override
    public int updatePassword(String username ,String password,String newPassword) throws Exception {
        Admin admin1 = adminMapper.selectByUsername(username);

        String salt = admin1.getSalt();
        String md5Password = MD5Util.getMD5(password + salt);
        if(!admin1.getPassword().equals(md5Password)){
           throw  new Exception("密码错误，修改失败");
        }


        String newMd5Password = MD5Util.getMD5(newPassword + salt);


        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(newMd5Password);
        admin.setId(admin1.getId());

        return adminMapper.update(admin);
    }
}
