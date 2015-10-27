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
    //private final String server = "https://pybots.localtunnel.me/";
    private long id;
    private int map[][];
    private JSONObject obj;
    private boolean win = false;

    public void initialise() throws Exception{
        String jsonData = "";
        String inputLine;
        URLConnection connectionToServer = new URL(server).openConnection();
        connectionToServer.setUseCaches(false);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connectionToServer.getInputStream()))) {
            while ((inputLine = in.readLine()) != null){
                jsonData += inputLine + "\n";
            }
        }
        id = new JSONObject(jsonData).getLong("bot_id");
        System.out.println("bot_id: " + id);
        
        refreshData();
        getMap();
    }
    
    public void refreshData() throws Exception{
        String jsonData = "";
        String inputLine = "";
        URLConnection connectionGetMap = new URL(server + "game/" + String.valueOf(id)).openConnection();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connectionGetMap.getInputStream()))) {
            while ((inputLine = in.readLine()) != null){
                //System.out.println(inputLine);
                jsonData += inputLine + "\n";
            }
        }
        obj = new JSONObject(jsonData);
    }

    public int[][] getMap() throws Exception{
        map = new int[obj.getInt("map_height")][obj.getInt("map_width")];
        
        JSONArray bot_map = obj.getJSONArray("map");
        for (int i = 0; i < bot_map.length(); i++){
            JSONArray a = bot_map.getJSONArray(i);
            for (int j = 0; j < a.length(); j++){
                map[i][j] = a.getInt(j);
                //System.out.printf("%s, ", map[i][j]);
            }
            //System.out.println();
        }
        //System.out.println();
        return(map);
    }
    
    public int[] getBotInfo() throws Exception{
        //JSONObject myBot = obj.getJSONArray("bots").getJSONObject(0);
        
        
        JSONObject myBot = obj.getJSONArray("bots").getJSONObject(0);;
        for (int i = 0; i < obj.getJSONArray("bots").length(); i++){
            if(obj.getJSONArray("bots").getJSONObject(i).getBoolean("your_bot")){
                myBot = obj.getJSONArray("bots").getJSONObject(i);
                break;
            }
            else{
                myBot = null;
            }
        }
        int[] myBotInfo = {myBot.getInt("x"), myBot.getInt("y"), myBot.getInt("orientation")};
        return (myBotInfo);
    }
    
    public MyPoint getTreasure() throws Exception{
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                if (map[i][j] == 1){
                    return (new MyPoint(i, j));
                }
            }
        }
        return (new MyPoint(1000, 1000));
    }
    
    
    
    
    
    
    
    
    public void ActionTurnLeft() throws Exception{
        post("turn_left");        
    }
    
    public void ActionTurnRight() throws Exception{
        post("turn_right");
    }
    
    public void ActionStep() throws Exception{
        post("step");
    }
    
    private void post(String s) throws Exception{
        //System.out.println("sending: " + s);
        String jsonData = "";
        URLConnection connectionAction = new URL(server + "action").openConnection();
        connectionAction.setDoOutput(true);
        try (OutputStreamWriter out = new OutputStreamWriter(connectionAction.getOutputStream())) {
            out.write("bot_id=" + id + "&action=" + s);
        }
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connectionAction.getInputStream()));
        String decodedString;
        while ((decodedString = in.readLine()) != null) {
            //System.out.printf(decodedString);
            jsonData += decodedString + "\n";
        }
        in.close();
        obj = new JSONObject(jsonData);
        //System.out.println("state: " + obj.getString("state"));
        
        System.out.printf("%20s  state: %s\n", "sending: " + s, obj.getString("state"));
        if(obj.getString("state") == "game_won"){
            win = true;
        }
    }
    
    public boolean getWin(){
        return win;
    }
}
