package com.example.serviceedu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.R;
import com.example.serviceedu.entity.EduCourse;
import com.example.serviceedu.entity.EduTeacher;
import com.example.serviceedu.service.EduCourseService;
import com.example.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin
@RestController
@RequestMapping("/serviceedu/teacherfront")
public class FrontTeacherController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    //分页查
    @GetMapping("/getTeacherFrontList/{page}/{limit}")
    public R pageList(@PathVariable Long page, @PathVariable Long limit) {
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        Map<String, Object> map = teacherService.pageListWeb(pageParam);
        return R.ok().data(map);
    }

    @GetMapping("getTeacherFrontInfo/{id}")
    public R getById(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> list = courseService.list(wrapper);
        return R.ok().data("teacher", teacher).data("courseList", list);
    }

}
