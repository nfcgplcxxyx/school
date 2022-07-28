package com.example.serviceucenter.controller;


import com.example.commonutils.JwtUtils;
import com.example.commonutils.R;
import com.example.commonutils.vo.UserVo;
import com.example.servicebase.handler.SchoolException;
import com.example.serviceucenter.entity.UcenterMember;
import com.example.serviceucenter.entity.vo.LoginVo;
import com.example.serviceucenter.entity.vo.RegisterVo;
import com.example.serviceucenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2022-07-24
 */
@RestController
@RequestMapping("/serviceucenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    @GetMapping("auth/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("item", member);
    }

    @GetMapping("getInfoUc/{id}")
    public UserVo getInfoById(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(member, userVo);
        return userVo;
    }

    //统计一天的注册人数
    @GetMapping("countregister/{day}")
    public R registerCount(@PathVariable String day) {
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }

}

