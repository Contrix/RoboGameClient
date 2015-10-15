package robogameclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jirka
 */
public class Comunication {
    private final String server = "http://hroch.spseol.cz:44822/";
    private long id;
    private int map[][];

    
    public void getBotID() throws Exception{
        String jsonData = "";
        String inputLine;
        URLConnection connectionToServer = new URL(server).openConnection();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connectionToServer.getInputStream()))) {
            while ((inputLine = in.readLine()) != null){
                jsonData += inputLine + "\n";
            }
        }
        
        JSONObject obj = new JSONObject(jsonData);
        id = obj.getLong("bot_id");
    }
    
    public int[][] getMap() throws Exception{
        String jsonData = "";
        String inputLine = "";
        URLConnection connectionGetMap = new URL(server + "game/" + String.valueOf(id)).openConnection();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connectionGetMap.getInputStream()))) {
            while ((inputLine = in.readLine()) != null){
                //System.out.println(inputLine);
                jsonData += inputLine + "\n";
            }
        }
        
        JSONObject obj = new JSONObject(jsonData);
        map = new int[obj.getInt("map_height")][obj.getInt("map_width")];
        
        JSONArray bot_map = obj.getJSONArray("map");
        for (int i = 0; i < bot_map.length(); i++){
            JSONArray a = bot_map.getJSONArray(i);
            for (int j = 0; j < a.length(); j++){
                map[i][j] = a.getInt(j);
                System.out.printf("%s, ", map[i][j]);
            }
            System.out.println();
        }
        
        
        return(map);
    }
    
    public void ActionturnLeft() throws Exception{
        post("turn_left");        
    }
    
    public void ActionturnRight() throws Exception{
        post("turn_right");
    }
    
    public void ActionStep() throws Exception{
        post("step");
        
    }
    
    private void post(String s) throws Exception{
        URLConnection connectionAction = new URL(server + "action").openConnection();
        connectionAction.setDoOutput(true);
        try (OutputStreamWriter out = new OutputStreamWriter(connectionAction.getOutputStream())) {
            out.write("bot_id=" + id + "&action=" + s);
        }
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connectionAction.getInputStream()));
        String decodedString;
        while ((decodedString = in.readLine()) != null) {
            //System.out.printf(decodedString);
        }
        in.close();
    }
}
