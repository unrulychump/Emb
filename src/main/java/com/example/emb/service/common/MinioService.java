package com.example.emb.service.common;

import com.example.emb.until.JsonResult;
import io.minio.*;
import io.minio.errors.MinioException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class MinioService {
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
    public String uploadFile(MultipartFile file) {

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
            return url;

        } catch (Exception e) {
            return "wrong";
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

    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName) {
        try {
            // 初始化 Minio 客户端
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpointUrl)
                    .credentials(accessKey, secretKey)
                    .build();
            // 获取文件的 InputStream
            InputStream inputStream = minioClient.getObject(
                    io.minio.GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build());

            // 设置下载响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(new InputStreamResource(inputStream));

        } catch (InvalidKeyException | NoSuchAlgorithmException | IOException | MinioException e) {
            e.printStackTrace();
            // 处理异常，返回相应的错误信息
            return ResponseEntity
                    .status(500)
                    .body(null);
        }
    }

}
