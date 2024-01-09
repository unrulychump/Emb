package com.example.emb.controller;

import com.example.emb.entity.Usrtable;
import com.example.emb.service.UsrService;
import com.example.emb.service.common.AliService;
import com.example.emb.service.exception.PwdErrorException;
import com.example.emb.service.exception.SmsErrorException;
import com.example.emb.service.exception.UsrNotFoundException;
import com.example.emb.until.JsonResult;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

//学会使用try catch
@RestController
@Api(tags = "用户")
@RequestMapping("/Usr")
public class UsrController {

    @Autowired
    UsrService usrService;
    @Autowired
    private Producer kaptchaProducer;
    @Autowired
    AliService aliService; //收发短信



    @ApiOperation("生成验证码")
    @GetMapping("/getKaptcha")
    public JsonResult<String> generateCaptcha(HttpSession session, HttpServletResponse response) {
        try {
            String text = kaptchaProducer.createText();
            BufferedImage image = kaptchaProducer.createImage(text);

            // 设置响应头
            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");

            // 将验证码图片写入到输出流
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);
            out.close();

            // 将验证码文本存入session，你的业务逻辑中可能需要用到验证码的文本
            session.setAttribute("kaptcha", text);

            return new JsonResult<String>(200, "验证码生成成功");
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResult<String>(405, "验证码生成失败");
        }
    }

    //发送短信应该在验证码核验无误之后实行的
    @ApiOperation("发送短信")
    @PostMapping("/SmsMsg")
    private JsonResult<String > SmSMsgSend(String tele,HttpSession session){
        int randomNum = generateRandomNumber(10000, 99999);
        session.setAttribute("code",String.valueOf(randomNum));
        try {
            aliService.sendSms(tele,String.valueOf(randomNum));
        }catch (SmsErrorException e){
            return new JsonResult<String>(501,e.getMessage());
        }
        return new JsonResult<String >(200,"短信发送成功");
    }

    @ApiOperation("用户登录")
    @GetMapping("/logIn")
    private JsonResult<String> logIn(String UserId, String UserPwd, HttpSession session,String kapt) {
        try {
            // 调用 UserService 的登录方法
            if(!kapt.equals(session.getAttribute("kaptcha")))
                return new JsonResult(500,"验证码错误");
            Usrtable usr = usrService.logIn(UserId, UserPwd);

            // 登录成功，将 UserId 写入 HttpSession
            session.setAttribute("Uid", UserId);
            session.setAttribute("name", usr.getName());
            session.setAttribute("groupId", usr.getGroupIdNow());

            // 设置 HttpSession 存活时间为2小时（以秒为单位）
            int sessionTimeoutInSeconds = 2 * 60 * 60; // 2 hours
            session.setMaxInactiveInterval(sessionTimeoutInSeconds);

            return new JsonResult<String>(200,"登陆成功");
        } catch (UsrNotFoundException | PwdErrorException e) {
            // 处理登录异常，可以返回特定的错误码和消息
            return new JsonResult<>(401, e.getMessage());
        } catch (Exception e) {
            // 捕获其他未预期的异常，可以进行通用的错误处理
            e.printStackTrace();
            return new JsonResult<>(500, "Internal Server Error");
        }
    }

    @ApiOperation("学生修改密码")
    @PutMapping("/changePwd")
    private JsonResult changePwd(String oldPwd,String newPwd,HttpSession session){
        try {
            usrService.changePwd(session.getAttribute("Uid").toString(),oldPwd,newPwd);
        }catch (PwdErrorException e){
            return new JsonResult<>(402,"密码错误");
        }catch (Exception e) {
            // 捕获其他未预期的异常，可以进行通用的错误处理
            e.printStackTrace();
            return new JsonResult<>(500, "Internal Server Error");
        }
        return new JsonResult<>(200);
    }

//    访客 短信验证码怎么搞
    @ApiOperation("访客注册")
    @PostMapping("/register")
    private JsonResult<String> register(String tele,String pwd,HttpSession session,String kapt){
        //验证码
        if(!session.getAttribute("kaptcha").equals(kapt))
            return new JsonResult<String >(500,"验证码错误");
        return new JsonResult<>(200);
    }
//差一个短信
    @ApiOperation("访客登陆")
    @GetMapping("/cusLogin")
    private JsonResult<String> cusLogin(String tele,HttpSession session,String kapt){
        if(!session.getAttribute("kaptcha").equals(kapt))
            return new JsonResult<String >(500,"验证码错误");
        try {
            usrService.cusLogIn(tele);
        }catch (UsrNotFoundException e){
            return new JsonResult<String>(402,e.getMessage());
        }
        return new JsonResult<>(200);
    }

//httpSession

    //////////
    private static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }


}
