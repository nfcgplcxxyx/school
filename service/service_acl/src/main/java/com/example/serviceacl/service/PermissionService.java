package com.example.serviceacl.service;

import com.alibaba.fastjson.JSONObject;
import com.example.serviceacl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author Bob
 * @since 2022-07-27
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> queryAllMenu();

    List<Permission> selectAllMenu(String roleId);

    void removeChildById(String id);

    void saveRolePermissionRealtionShip(String roleId, String[] permissionIds);

    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);

}
