package com.example.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.serviceedu.entity.EduSubject;
import com.example.serviceedu.entity.SubjectData;
import com.example.serviceedu.entity.vo.SubjectNestedVo;
import com.example.serviceedu.entity.vo.SubjectVo;
import com.example.serviceedu.listener.SubjectExcelListener;
import com.example.serviceedu.mapper.EduSubjectMapper;
import com.example.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2022-07-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<SubjectNestedVo> nestedList() {
        ArrayList<SubjectNestedVo> list = new ArrayList<>();
        //一级分类列表
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id", 0);
        wrapper1.orderByAsc("sort", "id");
        List<EduSubject> eduSubjects = baseMapper.selectList(wrapper1);

        //二级分类列表
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", 0);
        wrapper2.orderByAsc("sort", "id");
        List<EduSubject> subSubjects = baseMapper.selectList(wrapper2);

        for (int i = 0; i < eduSubjects.size(); i++) {
            EduSubject subject = eduSubjects.get(i);
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
            BeanUtils.copyProperties(subject, subjectNestedVo);
            list.add(subjectNestedVo);

            //把对应的二级分类查出来
            ArrayList<SubjectVo> subList = new ArrayList<>();
            for (int j = 0; j < subSubjects.size(); j++) {
                EduSubject subSubject = subSubjects.get(j);
                if (subject.getId().equals(subSubject.getParentId())) {
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(subSubject, subjectVo);
                    subList.add(subjectVo);
                }
            }
            subjectNestedVo.setChildren(subList);
        }
        return list;
    }
}
