package com.cmtou.microservices.facades;

import javazoom.jl.player.Player;
import java.io.*;

import org.springframework.stereotype.Service;


/**
 * Service facade to emit high frequency audio 
 * 
 * @author kxu
 *
 */
@Service
public class AudioDeterrentService {

    /**
     * Emit high frequency Alert
     */
    public void emitAlert()
    {
        try{
          
            FileInputStream fis = new FileInputStream("/Users/kxu/Downloads/whistle.mp3");
            Player playMP3 = new Player(fis);
            playMP3.play();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
}
