package com.example.serviceedu.entity.vo;

import lombok.Data;

@Data
public class VideoVo {

    private String id;
    private String title;
    private String courseId;
    private String chapterId;
    private String videoSourceId;
    private Integer sort;
    private Boolean free;
    private String videoOriginalName;

}
