package com.cmtou.microservices.facades;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import com.cmtou.microservices.daos.ImageRecognitionDao;
import com.cmtou.microservices.daos.StorageDao;


/**
 * Service to execute Raspi Still and feed image to S3 and then to Rekognition API
 * 
 * @author kxu
 *
 */
@Service
public class ImageRecognitionService {
    private static final Logger logger = LoggerFactory.getLogger(ImageRecognitionService.class);
    
    
    
    @Value("${image.dir}")
    private String destDir;
    
    private final String height = "720";
    private final String width = "960";
 
    @Value("${remote.endpoint}")
    private String audioDeviceEndpoint;
    
    @Autowired
    ImageRecognitionDao imageRecognitionDao;
    
    @Autowired
    StorageDao storageDao;
    
    @Autowired
    AudioDeterrentService audioDeterrentService;
    
    @Value("${aws.bucket}")
    private String bucket;
    
    
    
    public void captureImage()
    {
      try{  
        
      //Raspistill image capture

          String startInstruction = "/usr/bin/raspistill -t 0 -h "+height+ " -w "+width+ " -o "+destDir;
          String imageName = "image.jpg";
          String fileName = startInstruction + imageName;
          
          logger.info("Capturing Image Now: "+startInstruction + imageName);
          executeCommand(fileName);  
          //TODO needs to add in a wait time
          
          
          //String imageName = "squirrel.jpg";   
            
          logger.info("searching for image in: "+destDir+imageName);
          File createdImage = new File(destDir+imageName);
          
          if(createdImage.exists())
          {
              logger.info("Image exists");
              //Feed image to S3
              String s3FileName = System.currentTimeMillis()+"-"+imageName;
              storageDao.addFile(s3FileName, createdImage);
              logger.info("Added image to S3");
              
              //Classify using Recognition API
              DetectLabelsRequest request = new DetectLabelsRequest()
                      .withImage(new Image()
                              .withS3Object(new S3Object()
                                      .withName(s3FileName)
                                      .withBucket(bucket)))
                      .withMaxLabels(10)
                      .withMinConfidence(77F);
              logger.info("Calling Recoginition API");
              DetectLabelsResult result = imageRecognitionDao.classifyImage(request);
              
              boolean mammal = false;
              boolean squirrel = false;
              
              for(Label label : result.getLabels()){
                  if(label.getName().equalsIgnoreCase("mammal"))
                      mammal = true;
                  
                  if(label.getName().equalsIgnoreCase("squirrel"))
                      squirrel = true;
              }
              
              
              if(mammal && squirrel){
                  logger.info("Identified Squirrel");
                  RestTemplate restTemplate = new RestTemplate();
                  ResponseEntity<String> response = restTemplate.exchange(audioDeviceEndpoint+"/emitAlarm", HttpMethod.GET, null, String.class);
                  
                  //Call device to emit alarm
                  //audioDeterrentService.emitAlert();
              }
          }
          else {
              logger.info("Image was not captured");
          }
      
      }catch(Exception e){
          
          e.printStackTrace();
          
      }
        
    }
    
    
    private void executeCommand(String cmd) {
        Runtime r = Runtime.getRuntime();
        try {
            r.exec(cmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
