package com.example.serviceedu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.R;
import com.example.servicebase.handler.SchoolException;
import com.example.serviceedu.entity.EduTeacher;
import com.example.serviceedu.entity.vo.TeacherQuery;
import com.example.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2022-07-15
 */
@RestController
@RequestMapping("/serviceedu/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //增
    @PostMapping("/addTeacher")
    public R save(@RequestBody EduTeacher teacher) {
        boolean save = teacherService.save(teacher);
        return save ? R.ok() : R.error();
    }

    //删
    @DeleteMapping("/{id}")
    public R deleteTeacher(@PathVariable String id) {
        boolean b = teacherService.removeById(id);
        return b ? R.ok() : R.error();
    }

    //改
    @PutMapping("{id}")
    public R updateById(@PathVariable String id, @RequestBody EduTeacher teacher) {
        //由于是Put请求，需要手动设置ID值
        teacher.setId(id);
        boolean b = teacherService.updateById(teacher);
        return b ? R.ok() : R.error();
    }

    //查所有
    @GetMapping("")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    //根据ID查
    @GetMapping("{id}")
    public R getById(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    //分页查
//    @GetMapping("/{page}/{limit}")
//    public R pageList(@PathVariable Long page, @PathVariable Long limit) {
//
//        Page<EduTeacher> pageParam = new Page<>(page, limit);
//        teacherService.page(pageParam, null);
//        List<EduTeacher> records = pageParam.getRecords();
//        long total = pageParam.getTotal();
//        return R.ok().data("total", total).data("rows", records);
//
//    }

    //条件分页查
    @PostMapping("/{page}/{limit}")
    public R conditionalPageList(@PathVariable Long page, @PathVariable Long limit,
                                 @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> pageParam = new Page<>(page, limit);
        teacherService.pageQuery(pageParam, teacherQuery);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);

    }

}

