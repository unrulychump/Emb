package com.example.emb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.emb.entity.Custele;
import com.example.emb.entity.Usrtable;
import com.example.emb.mapper.CusteleMapper;
import com.example.emb.mapper.UsrtableMapper;
import com.example.emb.service.UsrService;
import com.example.emb.service.exception.PwdErrorException;
import com.example.emb.service.exception.UsrNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Service
public class UsrServiceImpl implements UsrService {
    @Autowired
    UsrtableMapper usrtableMapper;
    @Autowired
    CusteleMapper custeleMapper;

    @Override
    public Usrtable logIn(String id, String password) {
        QueryWrapper<Usrtable> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("id",Long.parseLong(id));
        Usrtable usr=usrtableMapper.selectOne(queryWrapper);
        if(usr==null){
            //没有查询到
            throw new UsrNotFoundException(id+"该用户不存在");
        }
        if(usr.getMd5()!=null){
            if(usr.getPassword().equals(getMD5Password(password,usr.getMd5()))){
                return usr;
            }
            else
                throw new PwdErrorException("密码错误");
        }
        else {
            if (usr.getPassword().equals(password))
                return usr;
            else
                throw new PwdErrorException("密码错误");
        }
    }

    @Override
    public long cusLogIn(String tele) {
        QueryWrapper<Custele> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("tele",Long.parseLong(tele));
        Custele cus=custeleMapper.selectOne(queryWrapper);
        if(cus!=null)
            return Long.parseLong(tele);
        else
            throw new UsrNotFoundException("访客"+tele+"不存在，请先注册");
    }

    @Override
    public void changePwd(String uid, String OldPwd, String NewPwd) {
        QueryWrapper<Usrtable> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("id",Long.parseLong(uid));
        Usrtable usr=usrtableMapper.selectOne(queryWrapper);
        if(usr.getMd5()!=null){
            if(usr.getPassword().equals(getMD5Password(OldPwd,usr.getMd5()))){
                String salt = UUID.randomUUID().toString().toUpperCase();
                usr.setMd5(salt);
                usr.setPassword(getMD5Password(NewPwd,salt));
                usrtableMapper.update(usr,queryWrapper);
            }
            else
                throw new PwdErrorException("密码错误");
        }
        else {
            if (usr.getPassword().equals(OldPwd)){
                String salt = UUID.randomUUID().toString().toUpperCase();
                usr.setMd5(salt);
                usr.setPassword(getMD5Password(NewPwd,salt));
                usrtableMapper.update(usr,queryWrapper);
            }
            else
                throw new PwdErrorException("密码错误");
        }
    }

    private String getMD5Password(String password, String salt) {

        for (int i = 0; i < 3; i++) {
            //MD5加密算法调用，三次加密
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
