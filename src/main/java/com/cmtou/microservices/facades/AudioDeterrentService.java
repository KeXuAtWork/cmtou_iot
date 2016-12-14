package com.cmtou.microservices.facades;

import javazoom.jl.player.Player;
import java.io.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * Service facade to emit high frequency audio 
 * 
 * @author kxu
 *
 */
@Service
public class AudioDeterrentService {

    @Value("${audio.filepath}")
    private String audio;
    
    
    /**
     * Emit high frequency Alert
     */
    public void emitAlert()
    {
        try{
          
            FileInputStream fis = new FileInputStream(audio);
            Player playMP3 = new Player(fis);
            playMP3.play();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
}
