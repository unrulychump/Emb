package com.example.emb.controller;

import com.example.emb.service.StudentService;
import com.example.emb.service.common.MinioService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "学生的基本操作")
@RequestMapping("/zjgsEmb/Stu")
public class StuController {
    @Autowired
    MinioService minioService;
    @Autowired
    StudentService studentService;





}
