package com.jobs.im.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

/**
 * @program: im
 * @ClassName: MinioConfig
 * @description:
 * @author: Author
 * @create: 2024-03-29 17:10
 * @Version 1.0
 **/
@Configuration
public class MinioConfig {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Bean
    public MinioClient minioClient() throws Exception {
        // 客户端操作类
        MinioClient minioClient = new MinioClient(endpoint, accessKey, secretKey);
        // 桶是否存在
        boolean bucketExists = minioClient.bucketExists(bucketName);
        // 不存在则创建
        if (!bucketExists) {
            minioClient.makeBucket(bucketName);
        }
        // 桶策略（公共）
        String bucketPolicy =
            "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::jobsworker-im\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:GetObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\"],\"Resource\":[\"arn:aws:s3:::jobsworker-im/*\"]}]}";
        // 桶策略设置
        minioClient.setBucketPolicy(bucketName, bucketPolicy);
        return minioClient;
    }
}
