package com.example.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.servicebase.handler.SchoolException;
import com.example.serviceedu.client.VodClient;
import com.example.serviceedu.entity.EduVideo;
import com.example.serviceedu.entity.vo.VideoVo;
import com.example.serviceedu.mapper.EduVideoMapper;
import com.example.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2022-07-19
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public void saveVideo(VideoVo videoVo) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoVo, video);
        boolean save = this.save(video);
        if (!save) {
            throw new SchoolException(20001, "小节信息保存失败");
        }
    }

    @Override
    public VideoVo getInfoById(String id) {
        EduVideo byId = this.getById(id);
        if (byId == null) {
            throw new SchoolException(20001, "数据不存在");
        }
        VideoVo vo = new VideoVo();
        BeanUtils.copyProperties(byId, vo);
        return vo;
    }

    @Override
    public void updateInfoById(VideoVo videoVo) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoVo, video);
        boolean result = this.updateById(video);
        if (!result) {
            throw new SchoolException(20001, "课时信息保存失败");
        }
    }

    @Override
    public boolean removeVideoById(String id) {
        EduVideo video = baseMapper.selectById(id);
        String sourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(sourceId)) {
            vodClient.removeVideo(sourceId);
        }
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(wrapper);

        List<String> videoSourceIdList = new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {
            EduVideo video = videoList.get(i);
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoSourceIdList.add(videoSourceId);
            }
        }

        if (videoSourceIdList.size() > 0) {
            vodClient.removeVideoList(videoSourceIdList);
        }

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        Integer count = baseMapper.delete(queryWrapper);
        return null != count && count > 0;
    }
}
