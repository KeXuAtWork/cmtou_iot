package com.cmtou.microservices.daos;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

/**
 * 
 * 
 * @author kxu
 *
 */
@Component
public class StorageDao {

    
    private String bucket;
    
    private AmazonS3 s3client;
    
    /**
     * Constructor
     * 
     * @param accessKey
     * @param secretKey
     */
    public StorageDao(@Value("${aws.accesskey}")  String accessKey, @Value("${aws.secretkey}")  String secretKey,  @Value("${aws.bucket}")  String bucket)
    {
       s3client  = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
       this.bucket = bucket;
    }
    
    
    /**
     * Adds a file to S3
     * 
     * @param fileName
     * @param file
     */
    public PutObjectResult addFile(String fileName, File file)
    {
        return s3client.putObject(new PutObjectRequest(bucket, fileName, file));     
    }
    
  
    
    
}
