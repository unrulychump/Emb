package com.example.emb.service;

import com.example.emb.entity.Mettable;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipmentService {

    //基本的操作

    //展示所有器材
    List<Mettable> showAllEquip();

    //展示具体的器材
    Mettable ShowTheEquip(int id);
    //(展示有剩余的器
    List<Mettable> showLeftEquip();
    //根据名称查询器材
    List<Mettable> getByName(String name);


    //学生关于器材的操作
    //怎么确定使用什么小组的身份去借用器材
    void postEquApply(int id,int num,int GroupId);

    ResponseEntity<InputStreamResource> DownloadZip(int id);

    //教师部分
    //上传
    void PostEquipment();
    //修改
    void UpdateEquipment();
    //删除
    void DeleteEquipment();
    //归还
    void ReturnEquipment();


}
