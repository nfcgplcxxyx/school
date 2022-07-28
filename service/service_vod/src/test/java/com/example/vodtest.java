package com.example;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.example.servicevod.utils.AliyunVodSDKUtils;
import org.junit.Test;

import java.util.List;

public class vodtest {
    @Test
    public void test1() throws Exception {
        String fileName = "C:\\Users\\NFFive\\Documents\\计科\\Java\\依赖\\谷粒学院项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";
        UploadVideoRequest request = new UploadVideoRequest("LTAI5tFxT2Y1osgr9zoDfcNN", "iAizSOmp3EvYD2oArjhpa1bkfoFkmc", "test", fileName);

        request.setPartSize(2 * 1024 * 1024L);

        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    public static void getAuth() throws Exception {
        //根据视频ID获取加密视频的播放凭证
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI5tFxT2Y1osgr9zoDfcNN", "iAizSOmp3EvYD2oArjhpa1bkfoFkmc");

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("b15bd7f7fb284b4584979a54813a8863");

        response = client.getAcsResponse(request);
        System.out.println(response.getPlayAuth());

    }


    //根据视频ID获取播放地址
    public static void getUrl() throws Exception {
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI5tFxT2Y1osgr9zoDfcNN", "iAizSOmp3EvYD2oArjhpa1bkfoFkmc");

        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        request.setVideoId("b15bd7f7fb284b4584979a54813a8863");

        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
