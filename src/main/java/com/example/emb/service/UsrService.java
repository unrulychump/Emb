package com.example.emb.service;

import com.example.emb.entity.Usrtable;
import com.example.emb.mapper.UsrtableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface UsrService {

    //登陆要返回uid的
    Usrtable logIn(String id,String password);

    //注册 这里还没有想好怎么写，验证码怎么搞上去

    //访客登陆
    long cusLogIn(String tele);

    //学生改变密码
    void changePwd(String uid,String OldPwd,String NewPwd);
}
