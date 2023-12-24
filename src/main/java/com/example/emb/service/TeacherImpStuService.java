package com.example.emb.service;

import com.example.emb.entity.Classdtable;
import com.example.emb.entity.Usrtable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;

@Service
public interface TeacherImpStuService {

    //新建班级
    void newClass(String Name, Date modifiedTime,Date endTime,int year);
    //根据excel导入学生info
    void ImportStuByExcel(MultipartFile file, int classId);

    //手动部分

    // 手动写到教学班级
    void InsertStu(String uid,int classId);
    //手动删除
    void DeleteStu(int classStuId);
    //查询学生info 返回列表
    List<Usrtable> searchStu(Classdtable Stu, int classId);
}
