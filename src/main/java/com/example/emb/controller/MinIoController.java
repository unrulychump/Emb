package com.example.emb.controller;



import com.example.emb.until.JsonResult;
import io.minio.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @program: gmall-parent
 * @author: Mr.Zhuo
 * @create: 2022-04-03 21:41
 **/
@RestController
@Api(tags = "上传文件获得url")
@RequestMapping("MinIO")
public class MinIoController {

    //  获取文件上传对应的地址
    @Value("${minio.url}")
    public String endpointUrl;

    @Value("${minio.accessKey}")
    public String accessKey;

    @Value("${minio.secretKey}")
    public String secretKey;

    @Value("${minio.bucketName}")
    public String bucketName;

    @ApiOperation("上传文件")
    @PostMapping("/uploadFile")
    public JsonResult<String> uploadFile(MultipartFile file) {

        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpointUrl)
                    .credentials(accessKey, secretKey)
                    .build();

            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
            }

            // 文件名字
            String fileName = System.currentTimeMillis() + UUID.randomUUID().toString();
            // 上传文件 已经文件大小
            // file.getContentType() 文件类型
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                                    file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            // 返回文件访问路径
            String url = endpointUrl + "/" + bucketName + "/" + fileName;
            return new JsonResult<String >(200,url);

        } catch (Exception e) {
            return new JsonResult<String>(4009,"上传错误");
        }
    }

    @ApiOperation("删除文件,文件名称是最后一个/后的东西")
    @DeleteMapping("/deleteFile")
    public JsonResult<String> DeleteFile(String FileName){
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpointUrl)
                    .credentials(accessKey, secretKey)
                    .build();
            minioClient.removeObject( RemoveObjectArgs.builder().bucket(bucketName).object(FileName).build());
        } catch (Exception e) {
            return new JsonResult<String>(4002,e.getMessage());
        }
        return new JsonResult<String>(200,"success");
    }

}
