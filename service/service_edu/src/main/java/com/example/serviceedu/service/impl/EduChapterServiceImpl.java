package com.example.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.servicebase.handler.SchoolException;
import com.example.serviceedu.entity.EduChapter;
import com.example.serviceedu.entity.EduVideo;
import com.example.serviceedu.entity.vo.ChapterVo;
import com.example.serviceedu.entity.vo.SubSectionVo;
import com.example.serviceedu.mapper.EduChapterMapper;
import com.example.serviceedu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2022-07-19
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> nestedList(String courseId) {
        ArrayList<ChapterVo> chapterVoArrayList = new ArrayList<>();

        QueryWrapper<EduChapter> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id", courseId);
        queryWrapper1.orderByAsc("sort", "id");
        List<EduChapter> chapters = baseMapper.selectList(queryWrapper1);

        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", courseId);
        queryWrapper2.orderByAsc("sort", "id");
        List<EduVideo> videos = videoService.list(queryWrapper2);

        int count1 = chapters.size();
        for (int i = 0; i < count1; i++) {
            EduChapter chapter = chapters.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoArrayList.add(chapterVo);
            ArrayList<SubSectionVo> videoVoArrayList = new ArrayList<>();

            int count2 = videos.size();
            for (int j = 0; j < count2; j++) {
                EduVideo video = videos.get(j);
                if (chapter.getId().equals(video.getChapterId())) {
                    SubSectionVo videoVo = new SubSectionVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoArrayList.add(videoVo);
                }
            }

            chapterVo.setChildren(videoVoArrayList);
        }
        return chapterVoArrayList;
    }

    @Override
    public boolean removeChapterById(String id) {
        if (videoService.getCountByChapterId(id)) {
            throw new SchoolException(20001, "该章节下存在小节，请先删除小结");
        }
        Integer result = baseMapper.deleteById(id);
        return result != null && result > 0;

    }

    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        Integer count = baseMapper.delete(queryWrapper);
        return null != count && count > 0;
    }
}
