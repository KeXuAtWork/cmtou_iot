package com.cmtou.microservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmtou.microservices.facades.AudioDeterrentService;
import com.cmtou.microservices.facades.ImageRecognitionService;

/**
 * Controller to simulate an audio speaker device that recieves a signal from Raspi Hub 
 * 
 * @author kxu
 *
 */
@Controller
public class AudioDeterrentController {

    @Autowired
    AudioDeterrentService audioDeterrentService;

    @Autowired
    ImageRecognitionService imageRecognitionService;
    
    
    @RequestMapping(value = "/emitAlarm" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public  @ResponseBody boolean getRecords(Model model) {
		audioDeterrentService.emitAlert();
		return true;
	}
    
    @RequestMapping(value = "/testImageRecognition" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  @ResponseBody boolean testImageRecognition(Model model) {
        imageRecognitionService.captureImage();
        return true;
    }

}
