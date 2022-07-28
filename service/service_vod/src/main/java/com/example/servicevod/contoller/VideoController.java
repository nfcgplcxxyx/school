package com.example.servicevod.contoller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.example.commonutils.R;
import com.example.servicevod.service.VideoService;
import com.example.servicevod.utils.AliyunVodSDKUtils;
import com.example.servicevod.utils.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/servicevod/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("upload")
    public R uploadVideo(@RequestParam("file") MultipartFile file) throws Exception {
        String videoId = videoService.uploadVideo(file);
        return R.ok().message("视频上传成功").data("videoId", videoId);
    }

    @DeleteMapping("/{videoId}")
    public R removeVideo(@PathVariable String videoId) {
        videoService.removeVideo(videoId);
        return R.ok().message("删除视频成功");
    }

    @DeleteMapping("/delete-batch")
    public R removeVideoList(@RequestParam("videoIdList") List<String> videoIdList) {
        videoService.removeVideoList(videoIdList);
        return R.ok().message("视频删除成功");
    }

    @GetMapping("getPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {
        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        //初始化
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        //得到播放凭证
        String playAuth = response.getPlayAuth();
        //返回结果
        return R.ok().message("获取凭证成功").data("playAuth", playAuth);
    }
}

