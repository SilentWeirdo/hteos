package com.hteos.biz.app.storage;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.BucketInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;

/**
 * 阿里云文件上传
 *
 * @author fengshuonan
 * @date 2017-12-14-上午10:53
 */
@Component
public class AliyunOssStorage {

    @Value("${aliyun.oss.endpoint:endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId:accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret:accessKeySecret}")
    private String accessKeySecret;

    /**
     * oss客户端
     */
    private OSSClient ossClient = null;

    /**
     * oss的bucket名称
     */
    @Value("${aliyun.oss.bucketName:bucketName}")
    private String bucketName;

    @PostConstruct
    public void init() {
        this.ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    public String upload(InputStream input, String key) {
        ossClient.putObject(bucketName, key, input);
        BucketInfo bucketInfo = ossClient.getBucketInfo(this.getBucketName());
        Bucket bucket = bucketInfo.getBucket();
        return "http://" + bucket.getName() + "." + bucket.getExtranetEndpoint() + "/" + key;
    }


    public void remove(String key) {
        ossClient.deleteObject(bucketName, key);
    }

    public InputStream getInputStream(String key) {
        return ossClient.getObject(bucketName, key).getObjectContent();
    }

    public String getBucketName() {
        return bucketName;
    }

    @PreDestroy
    public void destroy() {
        ossClient.shutdown();
    }
}
