package com.example.serviceedu.service;

import com.example.serviceedu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.serviceedu.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Bob
 * @since 2022-07-19
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> nestedList(String courseId);

    boolean removeChapterById(String id);

    boolean removeByCourseId(String courseId);
}
