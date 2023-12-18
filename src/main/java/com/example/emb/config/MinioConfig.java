package com.example.emb.config;



import io.minio.MinioClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Auther: wangjiao
 * @Date: 2021-07-12 11:28
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    /**
     * 服务地址
     */
    private String url;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
     * 预览到期时间（小时）
     */
    private Integer previewExpiry;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Integer getPreviewExpiry() {
        return previewExpiry;
    }

    public void setPreviewExpiry(Integer previewExpiry) {
        this.previewExpiry = previewExpiry;
    }

    @Bean
    public MinioClient getMinIOClient()
    {
        return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    }
}

