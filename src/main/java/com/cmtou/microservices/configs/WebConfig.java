package com.cmtou.microservices.configs;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.cmtou.microservices.facades.ImageRecognitionService;


/**
 * Auto Configuration for Web Microservice
 * 
 * @author Kxu
 */
@Configuration
@EnableScheduling
public class WebConfig {

	protected Logger logger;
	
	@Autowired
    ImageRecognitionService imageRecognitionService;
   

    @Value("${image.capture}")
    private boolean capture;
    
	
	public WebConfig() {
		logger = Logger.getLogger(getClass().getName());
	}
	
	
	@Scheduled(fixedRate = 60000)
	public void updateMembers(){
	    try {
	        
	    	if(capture){
	    	    imageRecognitionService.captureImage();
	    	}
	    }catch (Exception e){
	    	logger.log(java.util.logging.Level.SEVERE, "Image processing failed due to: " + e.getMessage());
	    }
	}
	

}


