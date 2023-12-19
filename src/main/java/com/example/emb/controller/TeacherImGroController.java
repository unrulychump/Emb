package com.example.emb.controller;


import com.example.emb.until.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.Group;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "教师导入小组info")
@RequestMapping("/zjgsEmb/TeacherImGro")
public class TeacherImGroController {

    @ApiOperation("批量导入小组info")
    @PostMapping("ImportGro")
    private JsonResult ImportGro(@RequestParam("file") MultipartFile file){

        if (!file.getContentType().equals("application/vnd.ms-excel") && !file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return new JsonResult(10001,"上传非excel文件");
        }
        //如果操作失败了，应该要有这里有自动回滚和手动回滚的方法

        return new JsonResult<>(200);
    }
    //如果操作失败了，应该要有这里有自动回滚和手动回滚的方法

    @ApiOperation("手动导入小组info")
    @PostMapping("InsertGro")
    private JsonResult InsertGro(Group gro){

        return new JsonResult(200);
    }
    @ApiOperation("修改小组信息")
    @PutMapping("/ChangeGro")
    private JsonResult ChangeGro(Group group){

        return new JsonResult(200);
    }

    @ApiOperation("手动删除小组info")
    @PostMapping("DeleStu")
    private JsonResult DeleteGro(String GroupId){

        return new JsonResult(200);
    }

    @ApiOperation("查询小组info")
    @PostMapping("SearchGro")
    private JsonResult SearchStu(Group gro){
//    复用接口
        return new JsonResult(200);
    }

}
