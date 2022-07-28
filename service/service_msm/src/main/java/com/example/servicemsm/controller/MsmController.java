package com.example.servicemsm.controller;

import com.example.commonutils.R;
import com.example.servicemsm.service.MsmService;
import com.example.servicemsm.utils.RandomUtil;
import com.example.servicemsm.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

//@CrossOrigin
@RestController
@RequestMapping("/servicemsm/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/send/{phone}")
    public R code(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        code = RandomUtil.getSixBitRandom();
        smsUtils.sendSmsCode(phone, code);
        redisTemplate.opsForValue().set(phone, code, 30, TimeUnit.MINUTES);
        return R.ok();
    }
}
