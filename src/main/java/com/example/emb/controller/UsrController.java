package com.example.emb.controller;

import com.example.emb.until.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpSession;

@RestController
@Api(tags = "用户")
@RequestMapping("/Usr")
public class UsrController {



    @ApiOperation("用户登陆")
    @GetMapping("/logIn")
    private JsonResult logIn(String UserId,String UserPwd){
//        httpSession
        return new JsonResult<>(200);
    }

    @ApiOperation("学生修改密码")
    @PutMapping("/changePwd")
    private JsonResult changePwd(String oldPwd,String newPwd){

        return new JsonResult<>(200);
    }

//    访客
    @ApiOperation("访客注册")
    @PostMapping("/register")
    private JsonResult register(String tele,String pwd){

        return new JsonResult<>(200);
    }

    @ApiOperation("访客登陆")
    @GetMapping("/cusLogin")
    private JsonResult cusLogin(String tele,String pwd){
        return new JsonResult<>(200);
    }

//httpSession

}
