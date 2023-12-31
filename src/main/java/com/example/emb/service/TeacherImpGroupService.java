package com.example.emb.service;

import com.example.emb.entity.Usrtable;
import com.example.emb.service.impl.TeacherImpGroupServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface TeacherImpGroupService {
    //批量导入学生分组 通过excel
    void ImportGroupByExcel(MultipartFile file, int classId);

    //手动部分

    //新建  要关注小组编号是不是已经有了 同时返回id
    //同时对groupName正则匹配,加入的人是不是有重复的
    int InitGroup(String groupName,int year, int classId ,List<Usrtable> usr);
    //修改
    void updateGroup(int GroupId,List<Usrtable> InsertUsr,List<Usrtable> DeleteUsr);


}
