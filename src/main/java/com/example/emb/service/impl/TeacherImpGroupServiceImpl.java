package com.example.emb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.emb.entity.*;
import com.example.emb.mapper.ClasstableMapper;
import com.example.emb.mapper.GroupdtableMapper;
import com.example.emb.mapper.GrouptableMapper;
import com.example.emb.service.TeacherImpGroupService;
import com.example.emb.service.TeacherImpStuService;
import com.example.emb.service.exception.ErrorGroupNameException;
import com.example.emb.service.exception.GroupExistException;
import com.example.emb.service.exception.GroupNumRepeatException;
import com.example.emb.service.exception.MemberExistException;
import io.swagger.models.auth.In;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TeacherImpGroupServiceImpl  implements TeacherImpGroupService {
    @Autowired
    GrouptableMapper grouptableMapper;
    @Autowired
    GroupdtableMapper groupdtableMapper;
    @Autowired
    ClasstableMapper classtableMapper;

    /**
     * 先查看班级是不是已经存在了，然后再在插入的过程中查看：
     *         1.有没有个人重复插入的情况
     *         2.小组的名称是不是正确的，小组有没有名命重复的情况
     *         在插入前做判断，避免需要回滚的情况出现
     * @param file
     * @param classId
     */
    @Override
    public void ImportGroupByExcel(MultipartFile file, int classId) {
        //解析excel，同时判断和查重
        try(Workbook workbook = WorkbookFactory.create(file.getInputStream())){
            Sheet sheet = workbook.getSheetAt(0);

            List<Grouptable> groupTableList=new ArrayList<>();
            List<Groupdtable> groupDTableList=new ArrayList<>();

            Date date=new Date();
            java.sql.Date sqlD=new java.sql.Date(date.getTime());
            //第一行是标题行
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                //出现空行的问题
                if(row==null)
                    continue;
                //实际上需要的是uid ，stuName，className，组装list
                String studentId = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                String college=row.getCell(2).getStringCellValue();
                String className=row.getCell(3).getStringCellValue();
                String LearningClass=row.getCell(4).getStringCellValue();
                String year=row.getCell(5).getStringCellValue();
                String GroupName=row.getCell(6).getStringCellValue();

                //对GroupName的正则表达式进行判断
                if(!isValidGroupName(GroupName)){
                    throw new ErrorGroupNameException("错误的组名"+GroupName);
                }

                //开始组装两个list
                Grouptable grouptable=new Grouptable();
                Groupdtable groupdtable=new Groupdtable();

                grouptable.setClassName(LearningClass)
                        .setClassId(classId)
                        .setGroupNum(1)
                        .setModifiedtime(sqlD)
                        .setYear(Integer.parseInt(year))
                        .setGroupName(GroupName);

                groupdtable.setGroupName(GroupName)
                        .setClassName(LearningClass)
                        .setName(name)
                        .setUid(Long.parseLong(studentId))
                        .setClassId(classId);
                //是不是存在了
                QueryWrapper<Grouptable> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("GroupName",LearningClass)
                        .eq("classId",classId);
                Grouptable grouptable1=grouptableMapper.selectOne(queryWrapper);
                if(grouptable1!=null){
                    //更新num
                    grouptable1.setGroupNum(grouptable1.getGroupNum()+1);
                    grouptableMapper.update(grouptable1,queryWrapper);
                    //插入到groupDTable
                    if(groupdtableMapper.selectById(Long.parseLong(studentId))!=null){
                        throw new GroupNumRepeatException("学生只允许加入一个小组");
                    }
                    groupdtable.setGroupId(grouptable1.getId());
                    groupdtableMapper.insert(groupdtable);
                }
                else{
                    //说明原来不存在的
                    grouptableMapper.insert(grouptable);
                    if(groupdtableMapper.selectById(Long.parseLong(studentId))!=null){
                        throw new GroupNumRepeatException("学生只允许加入一个小组");
                    }
                    groupdtable.setGroupId(grouptable.getId());
                    groupdtableMapper.insert(groupdtable);
                }

//                groupTableList.add()

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //对数据做校验
    }

    //新建一个小组
    @Override
    public int InitGroup(String groupName, int year,int classId, List<Usrtable> usr) {
        QueryWrapper<Grouptable> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("classId",classId)
                .eq("GroupName",groupName);
        String className=classtableMapper.selectById(classId).getClassName();
        if(grouptableMapper.selectOne(queryWrapper)!=null){
            throw new GroupExistException("请勿重复创建小组");
        }
        Date date=new Date();
        java.sql.Date sqlD=new java.sql.Date(date.getTime());


        Grouptable grouptable=new Grouptable();
        grouptable.setGroupNum(usr.size())
                .setModifiedtime(sqlD)
                .setClassId(classId)
                .setClassName(className)
                .setYear(year);
        grouptableMapper.insert(grouptable);
        if(usr.size()!=0){
            //插入详情表
            Groupdtable groupdtable=new Groupdtable();
            groupdtable.setGroupId(grouptable.getId())
                    .setClassId(classId)
                    .setClassName(className);
            for(Usrtable user:usr){
                groupdtable.setUid(user.getId())
                        .setName(user.getName());
                groupdtableMapper.insert(groupdtable);
            }
        }
        return grouptable.getId();
    }

    //修改小组info 组名就不给修改了
    @Override
    public void updateGroup(int GroupId, List<Usrtable> InsertUsr, List<Usrtable> DeleteUsr) {
        Date date=new Date();
        java.sql.Date sqlD=new java.sql.Date(date.getTime());
        Grouptable grouptable=grouptableMapper.selectById(GroupId);
        if(InsertUsr.size()!=0){
            for(Usrtable usr: InsertUsr){
                if(groupdtableMapper.selectById(usr.getId())!=null){
                    throw new MemberExistException("学生"+usr.getName()+"已在小组中");
                }
                else {
                    Groupdtable groupdtable=new Groupdtable();
                    groupdtable.setName(usr.getName())
                            .setGroupId(GroupId)
                            .setUid(usr.getId())
                            .setGroupName(grouptable.getGroupName())
                            .setClassName(grouptable.getClassName());
                    groupdtableMapper.insert(groupdtable);
                }
            }
            if(DeleteUsr.size()!=0){
                for (Usrtable usr:DeleteUsr){
                    if(groupdtableMapper.selectById(usr.getId())==null){
                        throw new MemberExistException("学生"+usr.getName()+"不存在在小组中");
                    }
                    else {
                        QueryWrapper<Groupdtable> queryWrapper=new QueryWrapper<>();
                        queryWrapper.eq("Uid",usr.getId());
                        groupdtableMapper.delete(queryWrapper);
                        QueryWrapper<Grouptable>queryWrapper1=new QueryWrapper<>();
                        queryWrapper1.eq("id",GroupId);
                        grouptableMapper.update(grouptable.setGroupNum(grouptable.getGroupNum()-1),queryWrapper1);
                    }
                }
            }
            grouptable=grouptableMapper.selectById(GroupId);
            if(grouptable.getGroupNum()==0)
                grouptableMapper.deleteById(GroupId);
        }
    }







    ///////////////////////////////
    public static boolean isValidGroupName(String groupName) {
        // 定义匹配的正则表达式
        String regex = "第[一二三四五六七八九十]+组";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(groupName);

        // 检查是否匹配
        return matcher.matches();
    }
}
