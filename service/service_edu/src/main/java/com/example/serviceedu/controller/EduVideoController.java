package com.example.serviceedu.controller;


import com.example.commonutils.R;
import com.example.serviceedu.entity.vo.VideoVo;
import com.example.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/serviceedu/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    //增
    @PostMapping("/addVideo")
    public R save(@RequestBody VideoVo videoVo) {
        videoService.saveVideo(videoVo);
        return R.ok();
    }

    //删
    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id) {
        boolean result = videoService.removeVideoById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("删除失败");
        }
    }

    //改
    @PutMapping("/{id}")
    public R updateById(@RequestBody VideoVo videoVo, @PathVariable String id) {
        videoService.updateInfoById(videoVo);
        return R.ok();
    }

    //查
    @GetMapping("/{id}")
    public R getVideoInfo(@PathVariable String id) {
        VideoVo vo = videoService.getInfoById(id);
        return R.ok().data("item", vo);
    }

}

