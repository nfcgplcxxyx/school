package com.example.serviceedu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CourseVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String teacherId;
    private String subjectId;
    private String title;
    private BigDecimal price;
    private Integer lessonNum;
    private String cover;
    private String description;
    private String subjectParentId;
}
