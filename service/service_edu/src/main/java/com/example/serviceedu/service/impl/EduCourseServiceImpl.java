package com.example.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.servicebase.handler.SchoolException;
import com.example.serviceedu.entity.EduCourse;
import com.example.serviceedu.entity.EduCourseDescription;
import com.example.serviceedu.entity.vo.CoursePublishVo;
import com.example.serviceedu.entity.vo.CourseQuery;
import com.example.serviceedu.entity.vo.CourseVo;
import com.example.serviceedu.entity.vo.front.CourseFrontVo;
import com.example.serviceedu.entity.vo.front.CourseWebVo;
import com.example.serviceedu.mapper.EduCourseMapper;
import com.example.serviceedu.service.EduChapterService;
import com.example.serviceedu.service.EduCourseDescriptionService;
import com.example.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2022-07-19
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;

    @Override
    public String addCourseInfo(CourseVo courseVo) {
        EduCourse course = new EduCourse();
        course.setStatus(EduCourse.COURSE_DRAFT);
        BeanUtils.copyProperties(courseVo, course);
        boolean save = this.save(course);
        if (!save) {
            throw new SchoolException(20001, "课程信息保存失败");
        }

        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseVo.getDescription());
        courseDescription.setId(course.getId());
        boolean b = descriptionService.save(courseDescription);
        if (!b) {
            throw new SchoolException(20001, "课程信息保存失败");
        }

        return course.getId();
    }

    @Override
    public CourseVo getCourseById(String id) {
        EduCourse course = this.getById(id);
        if (course == null) {
            throw new SchoolException(20001, "课程不存在");
        }
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(course, courseVo);

        EduCourseDescription description = descriptionService.getById(id);
        if (course != null) {
            courseVo.setDescription(description.getDescription());
        }
        return courseVo;
    }

    @Override
    public String updateCourseInfo(CourseVo courseVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i == 0) {
            throw new SchoolException(20001, "修改课程信息失败");
        }

        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseVo.getId());
        description.setDescription(courseVo.getDescription());
        descriptionService.updateById(description);
        return eduCourse.getId();
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.getCoursePublishVoById(id);
    }

    @Override
    public boolean publishCourseById(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus(EduCourse.COURSE_NORMAL);
        Integer count = baseMapper.updateById(course);
        return null != count && count > 0;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        if (courseQuery == null) {
            baseMapper.selectPage(pageParam, wrapper);
            return;
        }
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.ge("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.ge("subject_id", subjectId);
        }
        baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public boolean removeCourseById(String id) {
        //根据课程ID删除所有视频
        videoService.removeByCourseId(id);
        //根据课程ID删除所有章节
        chapterService.removeByCourseId(id);
        //根据课程ID删除所有描述
        descriptionService.removeById(id);

        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseFrontVo courseQuery) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageParam, queryWrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBasicCourseInfo(String id) {
        this.updatePageViewCount(id);
        return baseMapper.getBasicCourseInfo(id);
    }

    @Override
    public void updatePageViewCount(String id) {
        EduCourse course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }
}
