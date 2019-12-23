package com.zsp.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.zsp.service.AmazonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author: zhaoshiping
 * @Date: Created in 10:16 2019/12/12
 * @Description:
 * @Version:
 */
@Service
public class AmazonServiceImpl implements AmazonService {
    public Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.s3.fileName}")
    private String fileName;
    @Autowired
    private AmazonS3 amazonS3Client;
    /**
     * 上传文件
     *
     * @param content
     * @return
     */
    @Override
    public void upload(String content) {
        InputStream fileInputStream=new ByteArrayInputStream(content.getBytes());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("plain/text");
        objectMetadata.addUserMetadata("x-amz-meta-title", "someTitle");
        objectMetadata.setContentLength(content.getBytes().length);

        amazonS3Client.putObject(bucketName, fileName, fileInputStream, objectMetadata);
    }

    @Override
    public void get() {
        amazonS3Client.getObject(bucketName,fileName);
    }


}
