package com.situ.stmall.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.stmall.common.bean.*;
import com.situ.stmall.common.mapper.*;
import com.situ.stmall.common.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private AddrMapper addrMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(Order order, Integer[] cartsId) throws Exception {
        //添加订单 - 事务
        //1.生成订单ID uuid
        String orderId = UUID.randomUUID().toString().replace("-", "");
        order.setId(orderId);
        //2.验证地址是否属于当前用户
        Addr addr = addrMapper.selectById(order.getAddrId());
        if(addr.getUserId()!=order.getUserId()){
            throw  new Exception("订单定制属于当前用户");
        }

        //3.保存Order到数据库中
        if(orderMapper.insert(order)!=1){
            throw  new Exception("订单添加失败,请确认后重新提交");
        }
        //4.根据cartIds查询购物车信息
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        List<Cart> carts = cartMapper.selectByIds(cartsId);
        for (Cart cart:carts){
            //5.验证购物车是否属于当前用户
            if(cart.getUserId()!=order.getUserId()){
                throw new Exception("购物车不属于当前用户");
            }
            //6.验证库存
            Goods goods = cart.getGoods();
            if(goods.getCount()<cart.getCount()){
                throw  new Exception("库存不足，请确认后重新下单");
            }
            //7.验证商品状态
            if(goods.getStatus()==1){
                throw new Exception("该商品已下架，请确认后重新下单");
            }
            //8.修改商品库存
            goods.setCount(goods.getCount()-cart.getCount());
            if(goodsMapper.update(goods)!=1){
                throw new Exception("修改库存失败，请确认后重新下单");
            }

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setGoodsId(cart.getGoodsId());
            orderDetail.setPrice(goods.getPrice());
            orderDetail.setCount(cart.getCount());
            orderDetails.add(orderDetail);
        }
        //9.新增订单详情
        if(orderDetailMapper.insert(orderDetails)!=orderDetails.size()){
            throw new Exception("添加订单详情失败，请确认后重新下单");
        }



        //10.删除购物车
        carts.stream().forEach(cart -> cartMapper.delete(cart.getId()));

        return 0;
    }



    @Override
    public List<Order> selectAll() {
        return orderMapper.selectAll();
    }

    @Override
    public PageInfo<Order> selectAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectAll();
        PageInfo<Order> goodsPageInfo = new PageInfo<>(orderList);
        return goodsPageInfo;
    }

    @Override
    public Order selectById(String id) throws Exception {
        Order order = orderMapper.selectById(id);
        if(order==null){
            throw new Exception("该订单不存在");
        }
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(Order order , User user) throws Exception {
        Order order1 = orderMapper.selectById(order.getId());
        if(order1==null){
            throw new Exception("该订单不存在");
        }
        //如果退款
        if(order.getStatus()==6){
            BigDecimal sum = new BigDecimal("0");
            for (OrderDetail orderDetail:order1.getOrderDetails()){
                BigDecimal sumPrice = orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getCount()));
                sum=sum.add(sumPrice);
                Goods goods = orderDetail.getGoods();
                goods.setCount(goods.getCount()+orderDetail.getCount());
                if (goodsMapper.update(goods)!=1) {
                    throw  new Exception("库存修改失败");
                }
            }

            user.setMoney(user.getMoney().add(sum));
            if(userMapper.update(user)!=1){
                throw new Exception("退款失败！！");
            }

        }

        //如果取消订单
        if(order.getStatus()==7){
            if(order1.getStatus()!=0){
                System.out.println(1111);
                BigDecimal sum = new BigDecimal("0");
                for (OrderDetail orderDetail:order1.getOrderDetails()){
                    BigDecimal sumPrice = orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getCount()));
                    sum=sum.add(sumPrice);
                    Goods goods = orderDetail.getGoods();
                    goods.setCount(goods.getCount()+orderDetail.getCount());
                    if (goodsMapper.update(goods)!=1) {
                        throw  new Exception("库存修改失败");
                    }
                }

                user.setMoney(user.getMoney().add(sum));
                if(userMapper.update(user)!=1){
                    throw new Exception("退款失败！！");
                }

            }

        }

        return orderMapper.update(order);
    }

    @Override
    public int updStatus(String id) throws Exception {
        Order order = orderMapper.selectById(id);
        if(order==null){
            throw new Exception("该订单不存在");
        }
        return orderMapper.updStatus(id);
    }

    @Override
    public List<OrderDetail> findByOid(String oid) {
        //PageHelper.startPage(pageNum,pageSize);
        List<OrderDetail> orderDetails = orderDetailMapper.selectByOid(oid);
        //PageInfo<OrderDetail> goodsPageInfo = new PageInfo<>(orderDetails);
        return orderDetails;

    }

    @Override
    public OrderDetail findById(Integer id) throws Exception {
        OrderDetail orderDetail = orderDetailMapper.selectById(id);
        if (orderDetail==null){
            throw  new Exception("该订单不存在");
        }
        return orderDetail;
    }

    @Override
    public List<Integer> getOrderDrawInfo() {
        List<Order> orders = orderMapper.selectAll();
        ArrayList<Integer> list = new ArrayList<>();

        long notPaid = orders.stream().filter(item -> item.getStatus() == 0).count();//未支付
        long paid = orders.stream().filter(item -> item.getStatus() == 1).count();//已支付
        long shipped = orders.stream().filter(item -> item.getStatus() == 2).count();//已发货
        long received = orders.stream().filter(item -> item.getStatus() == 3).count();//已收货

        long refund = orders.stream().filter(item -> item.getStatus() == 4).count();//退款
        long returnable = orders.stream().filter(item -> item.getStatus() == 5).count();//退货中
        long refundedReceived = orders.stream().filter(item -> item.getStatus() == 6).count();//已退款
        long cancel = orders.stream().filter(item -> item.getStatus() == 7).count();//已取消
        long allCount = orders.stream().count();
        list.add((int) allCount);//
        list.add((int) notPaid);//
        list.add((int) paid);//
        list.add((int) shipped);//
        list.add((int) received);//

        list.add((int) refund);//
        list.add((int) returnable);//
        list.add((int) refundedReceived);//
        list.add((int) cancel);//


        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pay(String orderId, Integer userId, String paypwd) throws Exception {

        //参数的验证
        //订单是否存在
        //根据订单id查询订单
        Order order = orderMapper.selectById(orderId);
        if(order==null){
            throw new Exception("订单不存在，无法支付");
        }
        //订单是否支付
        if(order.getStatus()!=0){
            throw  new Exception("订单已经支付，请勿重复支付");
        }
        //订单是否属于当前用户
        if(order.getUserId()!=userId){
            throw new Exception("非法的获取订单");
        }
        //订单待支付的总额 = 商品的价格*商品的数量
        BigDecimal sum = new BigDecimal("0");
        for (OrderDetail orderDetail:order.getOrderDetails()){
            BigDecimal sumPrice = orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getCount()));
            sum=sum.add(sumPrice);
        }
        System.out.println(sum);
        //验证余额是否充足
        User user = userMapper.selectById(userId);
        if(user.getMoney().compareTo(sum)<0){
            throw  new Exception("余额不足，无法支付");
        }
        //验证支付密码
        if(user.getPayPassword()==null){
            throw  new Exception("没有支付密码，无法支付");
        }
        //验证支付密码是否正确

        String md5Paypwd = MD5Util.getMD5(paypwd + user.getSalt());

        if(!user.getPayPassword().equals(md5Paypwd)){
            throw  new Exception("支付密码错误!!");
        }

        //支付

        System.out.println(sum);
        user.setMoney(user.getMoney().subtract(sum));
        user.setId(userId);
        System.out.println(user.getMoney());
        System.out.println("2222222222222");

        if(userMapper.update(user)!=1){
            throw new Exception("支付失败！！");
        }

        //修改订单状态
        order.setStatus(1);
        if(orderMapper.update(order)!=1){
            throw new Exception("订单状态修改失败，请重新支付");
        }



    }

    @Override
    public List<Order> selectByUserId(Integer userId) {
        return orderMapper.selectByUserId(userId);
    }

    @Override
    public List<Order> selectByUserIdAndStatus(Integer userId, Integer[] status) {
        return orderMapper.selectByUserIdAndStatus(userId,status);
    }

    @Override
    public int cancel(String orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(7);
        orderMapper.update(order);
        return 0;
    }

    @Override
    public List<Order> selectByAddrId(Integer addrId) {
        return orderMapper.selectByAddrId(addrId);
    }


}
