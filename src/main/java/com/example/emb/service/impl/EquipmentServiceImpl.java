package com.example.emb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.emb.entity.Browtable;
import com.example.emb.entity.Mettable;
import com.example.emb.mapper.BrowtableMapper;
import com.example.emb.mapper.MettableMapper;
import com.example.emb.service.EquipmentService;
import com.example.emb.service.common.MinioService;
import com.example.emb.service.exception.*;
import com.example.emb.until.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EquipmentServiceImpl extends ServiceImpl<MettableMapper,Mettable> implements EquipmentService  {
    @Autowired
    MettableMapper mettableMapper;
    @Autowired
    MinioService minioService;
    @Autowired
    BrowtableMapper browtableMapper;

    @Override
    public List<Mettable> showAllEquip() {
        //直接继承方法的形式实现效果
        return list();
    }
    @Override
    public Mettable ShowTheEquip(int id) {
        Mettable mettable=mettableMapper.selectById(id);
        return mettable;
    }

    @Override
    public List<Mettable> showLeftEquip() {
        QueryWrapper<Mettable> queryWrapper=new QueryWrapper<>();
        queryWrapper.ge("num",1);
        return null;
    }

    @Override
    public List<Mettable> getByName(String name) {
        QueryWrapper<Mettable> queryWrapper=new QueryWrapper<>();
        String fuzzyName="%";
        if(name.length()!=0){
            //去除空格
            name.replaceAll("\\s", "");
            for(char a:name.toCharArray()){
                fuzzyName+=a+"%";
            }
            queryWrapper.eq("name",fuzzyName);
            List<Mettable>list=mettableMapper.selectList(queryWrapper);
        }
        else{
            throw new NoNameReceivedException("收到空字符串");
        }

        return null;
    }

    @Override
    public void postEquApply(int id, int num, int GroupId) {
        Mettable mettable=mettableMapper.selectById(id);
        QueryWrapper<Mettable> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        if(mettable.getNum()>=num && num>0){
            mettable.setNum(mettable.getNum()-num);
            mettableMapper.update(mettable,queryWrapper);
        }
        else {
            throw new LeftNotEnoughException("存量不足");
        }
    }


    //下载器材资料的时候要求使用Minio配合
    @Override
    public ResponseEntity<InputStreamResource> DownloadZip(int id) {
        Mettable mettable=mettableMapper.selectById(id);
        if(mettable.getInfoUrl()==null){
            throw new NoInfoException("当前器材没有对应的资源");
        }
        else {
            //返回fileName
            String fileName="";
            String[] pathSegments = mettable.getInfoUrl().split("/");
            fileName = pathSegments[pathSegments.length - 1];
            return minioService.downloadFile(fileName);
        }
    }

    //上传器材资料
    @Override
    public void PostEquipment(Mettable mettable, MultipartFile photo, MultipartFile zip) {
        //判断文件类型
        isValidZip(zip);
        isValidImage(photo);

        //成功的话就上传
        String photoInfo=minioService.uploadFile(photo);
        String zipInfo=minioService.uploadFile(zip);
        if(!photoInfo.equals("wrong") || !zipInfo.equals("wrong"))
            throw new upLoadFailedException("上传错误");
        Date date=new Date();
        java.sql.Date sqlDate=new java.sql.Date(date.getTime());
        mettable.setInfoUrl(zipInfo)
                .setPhotoUrl(photoInfo)
                .setModifiedTime(sqlDate);
        mettableMapper.insert(mettable);

    }

    //更新器材资料
    @Override
    public void UpdateEquipment(Mettable mettable,MultipartFile photo,MultipartFile zip) {
        //判断文件类型
        isValidZip(zip);
        isValidImage(photo);

        //成功的话就上传
        String photoInfo=minioService.uploadFile(photo);
        String zipInfo=minioService.uploadFile(zip);
        if(!photoInfo.equals("wrong") || !zipInfo.equals("wrong"))
            throw new upLoadFailedException("上传错误");
        Date date=new Date();
        java.sql.Date sqlDate=new java.sql.Date(date.getTime());
        mettable.setInfoUrl(zipInfo)
                .setPhotoUrl(photoInfo)
                .setModifiedTime(sqlDate);
        mettableMapper.updateById(mettable);
    }

    //删除器材资料
    //在前端两次确认，同时需要Minio的配合
    @Override
    public void DeleteEquipment(int id) {
        //先得到fileName
        Mettable mettable=mettableMapper.selectById(id);
        if(mettable!=null){
            String[] pathSegmentsPhoto = mettable.getPhotoUrl().split("/");
            String[] pathSegmentsZip = mettable.getInfoUrl().split("/");

            //删除
            minioService.DeleteFile(pathSegmentsPhoto[pathSegmentsPhoto.length-1]);
            minioService.DeleteFile(pathSegmentsZip[pathSegmentsZip.length-1]);
        }
        else
            throw new NoNameReceivedException("没有得到器材info");
    }

    @Override
    public void ReturnEquipment(int  BrowId) {
        Browtable browtable=browtableMapper.selectById(BrowId);
        if(browtable==null){
            throw new WrongBrowMsgException("借出记录有误");
        }
        else {
            //先更新借用表的info
            Date date=new Date();
            java.sql.Date sqlDate= new java.sql.Date(date.getTime());
            browtable.setIsReturned(sqlDate);
            browtableMapper.updateById(browtable);
            //更新器材表的数据
            int EquId=browtable.getMId();
            Mettable mettable=mettableMapper.selectById(EquId);
            mettable.setNum(+mettable.getUsed()-browtable.getBroNum());
            mettableMapper.updateById(mettable);
            return;
        }
    }

    //////////////////////////////////////////

    private void isValidImage(MultipartFile file) {
        // 检查文件类型是否为图片
        if (!file.getContentType().startsWith("image/")) {
            throw new FileTypeErrorException("图片类型错误");
        }

        // 检查文件大小是否不超过2M
        if(file.getSize() >= 2 * 1024 * 1024)
            throw new FileTooBigException("图片大小超过2M"); // 2M expressed in bytes
    }

    private void isValidZip(MultipartFile file) {
        // 检查文件类型是否为压缩包
        if (!file.getContentType().equals("application/zip")) {
            throw new FileTypeErrorException("压缩包类型错误上传zip类型的压缩包");
        }

        // 检查文件大小是否不超过40M
        if(file.getSize() >= 40 * 1024 * 1024)
            throw new FileTooBigException("压缩包大小超过40M"); // 40M expressed in bytes
    }
}
