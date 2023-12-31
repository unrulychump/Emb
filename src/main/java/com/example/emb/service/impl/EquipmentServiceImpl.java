package com.example.emb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.emb.entity.Mettable;
import com.example.emb.mapper.MettableMapper;
import com.example.emb.service.EquipmentService;
import com.example.emb.service.common.MinioService;
import com.example.emb.service.exception.LeftNotEnoughException;
import com.example.emb.service.exception.NoInfoException;
import com.example.emb.service.exception.NoNameReceivedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentServiceImpl extends ServiceImpl<MettableMapper,Mettable> implements EquipmentService  {
    @Autowired
    MettableMapper mettableMapper;
    @Autowired
    MinioService minioService;

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


    @Override
    public ResponseEntity<InputStreamResource> DownloadZip(int id) {
        Mettable mettable=mettableMapper.selectById(id);
        if(mettable.getInfoUrl()==null){
            throw new NoInfoException("当前器材没有对应的资源");
        }
        else {
            //要不要-1
            String[] element=mettable.getName().split("/");
            return minioService.downloadFile(element[element.length-1]);
        }
    }
}
