package com.situ.stmall.common.service.impl;

import com.situ.stmall.common.bean.Addr;
import com.situ.stmall.common.bean.Order;
import com.situ.stmall.common.mapper.AddrMapper;
import com.situ.stmall.common.mapper.OrderMapper;
import com.situ.stmall.common.service.AddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddrServiceImpl implements AddrService {
    @Autowired
    private AddrMapper addrMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public int insert(Addr addr) {

        return addrMapper.insert(addr);
    }
    @Override
    public int insert(Addr addr,Integer userId) {

        addrMapper.updateStatus(userId);

        return addrMapper.insert(addr);
    }

    @Override
    public int delete(Integer id) throws Exception {
        List<Order> orders = orderMapper.selectByAddrId(id);

        return addrMapper.delete(id);
    }

    @Override
    public int update(Addr addr) {
        return addrMapper.update(addr);
    }

    @Override
    public Addr selectById(Integer id) {
        return addrMapper.selectById(id);
    }

    @Override
    public List<Addr> selectByUserId(Integer UserId) {
        return addrMapper.selectByUserId(UserId);
    }

    @Override
    public int updateStatus(Integer addrId) {
        Addr addr = new Addr();
        addr.setId(addrId);
        addr.setStatus(3);
        return addrMapper.update(addr);

    }
}
