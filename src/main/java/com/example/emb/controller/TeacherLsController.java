package com.example.emb.controller;

import com.example.emb.until.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

@RestController
@Api(tags = "教师查询路径和班级")
@RequestMapping("/zjgsEmb/Teacher")
public class TeacherLsController {

    @ApiOperation("获得所有年份")
    @GetMapping("/getAllYears")
    private JsonResult<List<Integer>> getAllYears(){
        List<Integer> list=new ArrayList<>();

        return new JsonResult<List<Integer>>(200,list);
    }

    @ApiOperation("获得该年份下的所有班级")
    @GetMapping("/getClassByYear")
    private JsonResult<List<String>> getClassByYear(int year){
        List<String> list=new ArrayList<>();

        return new JsonResult<List<String>>(200,list);
    }

    @ApiOperation("获得班级下的所有学生信息")
    @GetMapping("/getStuByClass")
    private JsonResult<List<User>> getStuByClass(int year, String className){
        List<User> list=new ArrayList<>();

        return new JsonResult<List<User>>(200,list);
    }


}
