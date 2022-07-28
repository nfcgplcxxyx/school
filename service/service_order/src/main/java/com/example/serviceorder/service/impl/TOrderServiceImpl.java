package com.example.serviceorder.service.impl;

import com.example.commonutils.vo.CourseDto;
import com.example.commonutils.vo.UserVo;
import com.example.serviceorder.client.CourseClient;
import com.example.serviceorder.client.UcenterClient;
import com.example.serviceorder.entity.TOrder;
import com.example.serviceorder.mapper.TOrderMapper;
import com.example.serviceorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.serviceorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2022-07-26
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {
    @Autowired
    private CourseClient courseClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String saveOrder(String courseId, String memberIdByJwtToken) {
        CourseDto courseDto = courseClient.getCourseInfoDto(courseId);
        UserVo ucenterMember = ucenterClient.getInfoById(memberIdByJwtToken);
        TOrder order = new TOrder();

        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName(courseDto.getTeacherName());
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0);
        order.setPayType(1);

        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
