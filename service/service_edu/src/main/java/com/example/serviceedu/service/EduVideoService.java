package com.example.serviceedu.service;

import com.example.serviceedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.serviceedu.entity.vo.VideoVo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Bob
 * @since 2022-07-19
 */
public interface EduVideoService extends IService<EduVideo> {
    boolean getCountByChapterId(String chapterId);

    void saveVideo(VideoVo videoVo);

    VideoVo getInfoById(String id);

    void updateInfoById(VideoVo videoVo);

    boolean removeVideoById(String id);

    boolean removeByCourseId(String courseId);
}
