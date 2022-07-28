package com.example.serviceacl.controller;


import com.example.commonutils.R;
import com.example.serviceacl.entity.Permission;
import com.example.serviceacl.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2022-07-27
 */
@RestController
@RequestMapping("/serviceacl/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //查询所有菜单
    @GetMapping
    public R allPermission() {
        List<Permission> list = permissionService.queryAllMenu();
        return R.ok().data("children", list);
    }

    //递归删除菜单
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        permissionService.removeChildById(id);
        return R.ok();
    }

    //给角色分配权限
    @PostMapping("/doAssign")
    public R doAssign(String roleId, String[] permissionId) {
        permissionService.saveRolePermissionRealtionShip(roleId, permissionId);
        return R.ok();
    }

    //根据角色获取菜单
    @GetMapping("toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.ok().data("children", list);
    }


    //新增菜单
    @PostMapping("save")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    //修改菜单
    @PutMapping("update")
    public R updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }

}

