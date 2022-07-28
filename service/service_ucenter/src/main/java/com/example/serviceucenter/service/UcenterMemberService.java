package com.example.serviceucenter.service;

import com.example.serviceucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.serviceucenter.entity.vo.LoginVo;
import com.example.serviceucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Bob
 * @since 2022-07-24
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    Integer countRegisterByDay(String day);
}
