package com.example.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.serviceedu.entity.vo.CoursePublishVo;
import com.example.serviceedu.entity.vo.CourseQuery;
import com.example.serviceedu.entity.vo.CourseVo;
import com.example.serviceedu.entity.vo.front.CourseFrontVo;
import com.example.serviceedu.entity.vo.front.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Bob
 * @since 2022-07-19
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseVo courseVo);

    CourseVo getCourseById(String id);

    String updateCourseInfo(CourseVo courseVo);

    CoursePublishVo getCoursePublishVoById(String id);

    boolean publishCourseById(String id);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    boolean removeCourseById(String id);

    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseFrontVo courseQuery);

    CourseWebVo getBasicCourseInfo(String id);

    void updatePageViewCount(String id);
}
