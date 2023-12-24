package com.example.emb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Modifier;
import java.util.Date;

@Service
public interface TeacherImpStuService {

    //新建班级
    void newClass(String Name, Date modifiedTime,Date endTime,int year);
    //根据excel导入学生info
    void ImportStuByExcel(MultipartFile file, int classId);

    //手动部分
    void InsertStu(String uid,int classId);

}
