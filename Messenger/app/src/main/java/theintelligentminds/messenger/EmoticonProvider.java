package theintelligentminds.messenger;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.ws.rs.client.Entity;

/**
 * Created by Rene on 20.05.2015.
 */
public class EmoticonProvider {

    private HashMap<String, Integer> emomap;
    private static EmoticonProvider instance = null;
    private EmoticonProvider()
    {
        emomap = new HashMap<>();
        emomap.put(":-)",R.drawable.bubble_s_a);

    }

    public static EmoticonProvider getInstance(){
        if(instance == null)instance = new EmoticonProvider();
        return instance;
    }



    public int getEmoticon(String ecostring){
        Integer ecoid = emomap.get(ecostring);

        if(ecoid == null) return -1;
        return ecoid;
    }

    public String htmlFormatEmoticons(String message){
        for(Map.Entry<String, Integer> entry : emomap.entrySet()){
            System.out.println("Gonna replace all "+entry.getKey()+" in "+message);
            message = message.replaceAll(Pattern.quote(entry.getKey()), "<img src=\""+entry.getKey()+"\"/>");
        }
        System.out.println("Returning: " + message);
        return message;

    }

}
