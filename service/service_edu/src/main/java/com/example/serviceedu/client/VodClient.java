package com.example.serviceedu.client;

import com.example.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    @DeleteMapping("/servicevod/video/{videoId}")
    R removeVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/servicevod/video/delete-batch")
    R removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);

}
