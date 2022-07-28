package com.example.serviceorder.client;

import com.example.commonutils.vo.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface CourseClient {

    @GetMapping("/serviceedu/course/getDto/{courseId}")
    CourseDto getCourseInfoDto(@PathVariable String courseId);

}
