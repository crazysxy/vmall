package com.situ.stmall.front.controller;

import com.situ.stmall.common.bean.Addr;
import com.situ.stmall.common.bean.RespBean;
import com.situ.stmall.common.bean.User;
import com.situ.stmall.common.groups.UpdateGroup;
import com.situ.stmall.common.service.AddrService;
import com.situ.stmall.common.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.groups.Default;
import java.util.List;

@Controller
@RequestMapping("/user/addr")
public class AddrController {
    @Autowired
    private AddrService addrService;
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String addr(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        List<Addr> addrs = addrService.selectByUserId(user.getId());
        model.addAttribute("addresses", addrs);
        return "user/addr";
    }

    @GetMapping("/toInsertAddr")
    public String toInsertAddr(){
        return "user/toInsertAddr";

    }
    @GetMapping("/toUpdateAddr")
    public String toUpdateAddr(){
        return "user/toUpdateAddr";
    }

    @ResponseBody
    @GetMapping("/findAddrInfo")
    public  RespBean findAddrInfo( Integer id,HttpSession session){
        Addr addr = addrService.selectById(id);
        session.setAttribute("address", addr);
        return  RespBean.ok(addr);
    }



    //添加新地址
    @ResponseBody
    @PostMapping("/insert")
    public RespBean insert(HttpSession session,@Validated Addr addr){
        User user = (User) session.getAttribute("user");
        List<Addr> addrs = addrService.selectByUserId(user.getId());
        if(addrs.size()+1>10){
            return RespBean.error("地址已满，请删除地址后再添加");
        }
        addr.setUserId(user.getId());
        if(addr.getStatus()==null){
            addr.setStatus(0);
        }
        if (addr.getStatus()==1){
            addrService.insert(addr, user.getId());
        }else {
            addrService.insert(addr);
        }

        return RespBean.ok();

    }
    @ResponseBody
    @PostMapping("/update")
    public RespBean update(HttpSession session, @Validated({UpdateGroup.class, Default.class}) Addr addr){
        User user = (User) session.getAttribute("user");
        addr.setUserId(user.getId());
        if(addr.getStatus()==null){
            addr.setStatus(0);
        }
        Addr address = (Addr) session.getAttribute("address");
        addr.setId(address.getId());

        addrService.update(addr);
        return RespBean.ok();

    }

    //删除地址
    @GetMapping("/delete/{id}")
    public String delete( @PathVariable("id") Integer id ,HttpSession session ){

        if (orderService.selectByAddrId(id).size()!=0){
            try {
                addrService.updateStatus(id);
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("addrMsg", e.getMessage());
            }

        }else {
            try {
                addrService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("addrMsg", e.getMessage());
            }

        }



        return "redirect:/user/addr";

    }

}
