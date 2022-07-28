package com.example.serviceedu.controller;


import com.example.commonutils.R;
import com.example.serviceedu.entity.EduChapter;
import com.example.serviceedu.entity.vo.ChapterVo;
import com.example.serviceedu.service.EduChapterService;
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
@RequestMapping("/serviceedu/chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //增
    @PostMapping
    public R save(@RequestBody EduChapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    //删
    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id) {
        boolean result = chapterService.removeChapterById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("删除失败");
        }
    }

    //改
    @PutMapping("{id}")
    public R updateById(@PathVariable String id, @RequestBody EduChapter chapter) {
        chapter.setId(id);
        chapterService.updateById(chapter);
        return R.ok();
    }

    //查
    @GetMapping("{id}")
    public R getById(@PathVariable String id) {
        EduChapter chapter = chapterService.getById(id);
        return R.ok().data("item", chapter);
    }

    @GetMapping("/nestedList/{courseId}")
    public R nestedListById(@PathVariable String courseId) {
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return R.ok().data("items", chapterVoList);
    }
}

