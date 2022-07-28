package com.example.serviceacl.service;

import com.example.serviceacl.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bob
 * @since 2022-07-28
 */
public interface RoleService extends IService<Role> {

    Map<String, Object> findRoleByUserId(String userId);

    void saveUserRoleRealtionShip(String userId, String[] roleIds);

    List<Role> selectRoleByUserId(String id);
}
