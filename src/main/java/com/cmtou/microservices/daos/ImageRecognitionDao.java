package com.cmtou.microservices.daos;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Generic DAO for executing Image Recognition Requests against AWS Rekognition API
 * 
 * 
 * @author kxu
 *
 */
@Component
public class ImageRecognitionDao {

    private AmazonRekognitionClient rekognitionClient;
  
    public ImageRecognitionDao(@Value("${aws.accesskey}")  String accessKey, @Value("${aws.secretkey}")  String secretKey)
    {
        
        rekognitionClient = new AmazonRekognitionClient(new BasicAWSCredentials(accessKey, secretKey));
        rekognitionClient.setSignerRegionOverride("us-east-1");
        
    }
    
  
    /**
     * Classifies a provided image into structured set of labels.
     * 
     * @param request
     * @return
     */
    public DetectLabelsResult classifyImage(DetectLabelsRequest request)
    {
        DetectLabelsResult result = null;
        
        try {
            result = rekognitionClient.detectLabels(request);
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("Result = " + objectMapper.writeValueAsString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}
