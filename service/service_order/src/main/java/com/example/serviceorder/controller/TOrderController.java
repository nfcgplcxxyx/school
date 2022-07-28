package com.example.serviceorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.commonutils.JwtUtils;
import com.example.commonutils.R;
import com.example.serviceorder.entity.TOrder;
import com.example.serviceorder.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2022-07-26
 */
@RestController
@RequestMapping("/serviceorder/order")
//@CrossOrigin
public class TOrderController {
    @Autowired
    private TOrderService orderService;

    //根据课程id和用户id创建订单，并返回订单id
    @PostMapping("createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        String orderId = orderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    //根据订单号获取订单详情
    @GetMapping("getOrder/{orderId}")
    public R get(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        TOrder order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    //根据用户id和课程id查询订单信息
    @GetMapping("isBuyCourse/{memberid}/{id}")
    public boolean isBuyCourse(@PathVariable String memberid, @PathVariable String id) {
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<TOrder>()
                .eq("member_id", memberid)
                .eq("course_id", id)
                .eq("status", 1));
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}

