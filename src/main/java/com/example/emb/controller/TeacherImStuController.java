package com.example.emb.controller;

import com.example.emb.entity.Classdtable;
import com.example.emb.until.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "教师导入学生info")
@RequestMapping("/zjgsEmb/TeacherImStu")
public class TeacherImStuController {

    @ApiOperation("批量导入学生info")
    @PostMapping("ImportStu")
    private JsonResult ImportStu(@RequestParam("file") MultipartFile file){

        if (!file.getContentType().equals("application/vnd.ms-excel") && !file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {

            return new JsonResult(10001,"上传非excel文件");
        }
        //如果操作失败了，应该要有这里有自动回滚和手动回滚的方法

        return new JsonResult<>(200);
    }
    //如果操作失败了，应该要有这里有自动回滚和手动回滚的方法

    @ApiOperation("手动导入学生info")
    @PostMapping("InsertStu")
    private JsonResult InsertStu(String uid,int classId){

        return new JsonResult(200);
    }

    @ApiOperation("手动删除学生info")
    @PostMapping("DeleteStu")
    private JsonResult DeleteStu(int classStuId){

        return new JsonResult(200);
    }

    @ApiOperation("查询学生info")
    @PostMapping("SearchStu")
    private JsonResult SearchStu(Classdtable stu, int classId){
//    复用接口
        return new JsonResult(200);
    }

}
