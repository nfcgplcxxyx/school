package com.example.serviceacl.service;

import com.example.serviceacl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Bob
 * @since 2022-07-28
 */
public interface UserService extends IService<User> {

    User selectByUsername(String username);
}
