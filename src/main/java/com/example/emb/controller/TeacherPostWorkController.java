package com.example.emb.controller;

import com.example.emb.entity.Worktable;
import com.example.emb.until.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

@RestController
@Api(tags = "教师发布作业")
@RequestMapping("/zjgsEmb/TeacherPostWork")
public class TeacherPostWorkController {

    @ApiOperation("设置作业模板")
    @PostMapping("/setModel")
    private JsonResult setModel(Json model,String id){

        return new JsonResult(200);
    }

    @ApiOperation("改变作业模板")
    @PutMapping("/setModel")
    private JsonResult changeModel(Json model,String id){

        return new JsonResult(200);
    }
    @ApiOperation("删除作业模板")
    @DeleteMapping("/DeleteModel")
    private JsonResult deleteModel(Json model,String id){

        return new JsonResult(200);
    }
    @ApiOperation("发布作业")
    @PostMapping("/postWork")
    private JsonResult PostWork(String workid){

        return new JsonResult(200);
    }
    @ApiOperation("修改作业")
    @PutMapping("/changeWork")
    private JsonResult ChangeWork(String workid){

        return new JsonResult(200);
    }
    @ApiOperation("删除作业")
    @DeleteMapping("/DeleteWork")
    private JsonResult DeleteWork(String workid){

        return new JsonResult(200);
    }
}
