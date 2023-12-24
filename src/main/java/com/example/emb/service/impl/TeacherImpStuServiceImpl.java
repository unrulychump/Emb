package com.example.emb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.emb.entity.Classdtable;
import com.example.emb.entity.Classtable;
import com.example.emb.entity.Usrtable;
import com.example.emb.mapper.ClassdtableMapper;
import com.example.emb.mapper.ClasstableMapper;
import com.example.emb.mapper.UsrtableMapper;
import com.example.emb.service.TeacherImpStuService;
import com.example.emb.service.exception.UsrNotFoundException;
import com.example.emb.service.exception.repeatClassException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Date;

@Service
public class TeacherImpStuServiceImpl implements TeacherImpStuService {
    @Autowired
    ClasstableMapper classtableMapper;
    @Autowired
    UsrtableMapper usrtableMapper;
    @Autowired
    ClassdtableMapper classdtableMapper;


    @Override
    public void newClass(String Name, Date modifiedTime, Date endTime, int year) {
        QueryWrapper<Classtable> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("year",year)
                .eq("className",Name);
        Classtable classtable=classtableMapper.selectOne(queryWrapper);
        Classtable class1=new Classtable();
        java.sql.Date sqlDateM = new java.sql.Date(modifiedTime.getTime());
        java.sql.Date sqlDateE = new java.sql.Date(endTime.getTime());
        class1.setClassName(Name)
                .setModifiedTime(sqlDateM)
                .setEndTime(sqlDateE)
                .setYear(year);
        if(classtable!=null){
            throw new repeatClassException("重复建立相同班级");
        }
        else
            classtableMapper.insert(class1);
    }

    @Override
    public void ImportStuByExcel(MultipartFile file, int classId) {
        //解析excel
        try(Workbook workbook = WorkbookFactory.create(file.getInputStream())){
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                //出现空行的问题
                if(row==null)
                    continue;
                String studentId = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                String college=row.getCell(2).getStringCellValue();
                String className=row.getCell(3).getStringCellValue();
                String LearningClass=row.getCell(4).getStringCellValue();
                String year=row.getCell(5).getStringCellValue();
                //先看看学生info中有没有录入，如果没有，先导入到学生表里面
                Usrtable usr=usrtableMapper.selectById(Long.parseLong(studentId));
                Date date=new Date();
                java.sql.Date sqlD=new java.sql.Date(date.getTime());
                if(usr==null){
                    Usrtable newUsr=new Usrtable();
                    newUsr.setPassword(studentId)
                            .setId(Long.parseLong(studentId))
                            .setName(name)
                            .setCollege(college)
                            .setModifiedtime(sqlD)
                            .setClassName(className);
                    usrtableMapper.insert(newUsr);
                }

                //在班级表中插入学生
                Classdtable stu=new Classdtable();
                stu.setStuId(Long.parseLong(studentId))
                        .setClassId(classId)
                        .setStuName(studentId)
                        .setClassName(className);

                classdtableMapper.insert(stu);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void InsertStu(String uid,int classId) {
        Usrtable usr=usrtableMapper.selectById(Long.parseLong(uid));
        if(usr==null){
            throw new UsrNotFoundException("该学生不存在");
        }
        Classdtable classStu=new Classdtable();
        classStu.setClassId(classId)
                .setStuId(usr.getId())
                .setStuName(usr.getName());
    }
}
