package com.example.serviceedu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.JwtUtils;
import com.example.commonutils.R;
import com.example.serviceedu.client.OrderClient;
import com.example.serviceedu.entity.EduCourse;
import com.example.serviceedu.entity.vo.ChapterVo;
import com.example.serviceedu.entity.vo.front.CourseFrontVo;
import com.example.serviceedu.entity.vo.front.CourseWebVo;
import com.example.serviceedu.service.EduChapterService;
import com.example.serviceedu.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

//@CrossOrigin
@RestController
@RequestMapping("/serviceedu/coursefront")
public class FrontCourseController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrderClient orderClient;

    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public R pageList(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) CourseFrontVo courseQuery) {
        Page<EduCourse> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam, courseQuery);
        return R.ok().data(map);
    }

    @GetMapping("/getFrontCourseInfo/{id}")
    public R getFrontCourseInfo(@PathVariable String id, HttpServletRequest request) {
        CourseWebVo courseWebVo = courseService.getBasicCourseInfo(id);
        List<ChapterVo> voList = chapterService.nestedList(id);
        boolean buyCourse = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request), id);
        return R.ok().data("course", courseWebVo).data("chapterVoList", voList).data("isbuy", buyCourse);
    }

}
