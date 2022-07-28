package com.example.serviceedu.controller;


import com.example.commonutils.R;
import com.example.serviceedu.entity.vo.SubjectNestedVo;
import com.example.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Bob
 * @since 2022-07-17
 */
//@CrossOrigin
@RestController
@RequestMapping("/serviceedu/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //增
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.addSubject(file, subjectService);
        return R.ok();
    }

    //查
    @GetMapping("")
    public R nestedList() {
        List<SubjectNestedVo> subjectNestedVoList = subjectService.nestedList();
        return R.ok().data("items", subjectNestedVoList);
    }
}

