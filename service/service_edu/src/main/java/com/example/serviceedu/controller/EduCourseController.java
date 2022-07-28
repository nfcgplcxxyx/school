package com.example.serviceedu.controller;


import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.R;
import com.example.commonutils.vo.CourseDto;
import com.example.serviceedu.entity.EduCourse;
import com.example.serviceedu.entity.vo.CoursePublishVo;
import com.example.serviceedu.entity.vo.CourseQuery;
import com.example.serviceedu.entity.vo.CourseVo;
import com.example.serviceedu.service.EduCourseService;
import com.example.serviceedu.service.EduTeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/serviceedu/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    //增
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseVo courseVo) {
        String courseId = courseService.addCourseInfo(courseVo);
        if (!StringUtils.isEmpty(courseId)) {
            return R.ok().data("courseId", courseId);
        } else {
            return R.error().message("保存失败");
        }
    }

    //删
    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id) {
        boolean result = courseService.removeCourseById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("删除失败");
        }
    }

    //改
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseVo courseVo) {
        String courseId = courseService.updateCourseInfo(courseVo);
        return R.ok().data("courseId", courseId);
    }

    //查
    @GetMapping("/courseInfo/{id}")
    public R getById(@PathVariable String id) {
        CourseVo courseVo = courseService.getCourseById(id);
        return R.ok().data("item", courseVo);
    }

    //分页查
    @GetMapping("{page}/{limit}")
    public R pageQuery(@PathVariable Long page, @PathVariable Long limit, CourseQuery courseQuery) {
        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    //发布
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVoById(@PathVariable String id) {
        CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
        return R.ok().data("item", courseInfoForm);
    }

    @PutMapping("publish-course/{id}")
    public R publishCourseById(@PathVariable String id) {
        courseService.publishCourseById(id);
        return R.ok();
    }

    //订单查
    @GetMapping("/getDto/{courseId}")
    public CourseDto getCourseInfoDto(@PathVariable String courseId) {
        CourseVo courseVo = courseService.getCourseById(courseId);
        CourseDto courseDto = new CourseDto();
        BeanUtils.copyProperties(courseVo, courseDto);
        String teacherId = courseVo.getTeacherId();
        String teacherName = teacherService.getById(teacherId).getName();
        courseDto.setTeacherName(teacherName);
        return courseDto;
    }
}

