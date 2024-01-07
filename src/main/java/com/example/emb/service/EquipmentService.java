package com.example.emb.service;

import com.example.emb.entity.Mettable;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface EquipmentService {

    //要加入基本的分页操作
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
    // 上传
    //上传基本的文件描述 ,photo,zip;
    void PostEquipment(Mettable mettable, MultipartFile photo,MultipartFile zip);
    //修改
    void UpdateEquipment(Mettable mettable ,MultipartFile photo,MultipartFile zip);
    //删除
    void DeleteEquipment(int id);

    //归还
    void ReturnEquipment(int  BrowId);


}
