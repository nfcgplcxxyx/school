package com.example.serviceacl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.serviceacl.entity.User;
import com.example.serviceacl.mapper.UserMapper;
import com.example.serviceacl.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2022-07-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }
}
