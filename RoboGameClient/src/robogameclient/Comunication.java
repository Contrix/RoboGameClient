package robogameclient;

import java.io.BufferedReader;
import java.io.IOException;
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
    //private final String server = "http://localhost:44822/";
    private String id;
    private int postRequest = 0;
    private int map[][];
    private JSONObject obj;
    private boolean endGame = false;
    
    /*****GET*****/

    public void initialise(){
        String jsonData = "";
        String inputLine;
        try{
            URLConnection connectionToServer = new URL(server).openConnection();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connectionToServer.getInputStream()))) {
                while ((inputLine = in.readLine()) != null){
                    jsonData += inputLine + "\n";
                }
            } catch (IOException ex) {
                System.err.println("Nepodařilo se přečíst data - " + ex);
            }
        } catch (Exception ex) {
            System.err.println("Nepodařilo se navázat spojení se servrem (GET) - " + ex);
        }
        
        id = new JSONObject(jsonData).getString("bot_id");
        System.out.println("bot_id: " + id);
        
        refreshData();
        getMap();
    }
    
    public void refreshData(){
        String jsonData = "";
        String inputLine = "";
        try{
            URLConnection connectionGetMap = new URL(server + "game/" + id).openConnection();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connectionGetMap.getInputStream()))) {
                while ((inputLine = in.readLine()) != null){
                    jsonData += inputLine + "\n";
                }
                obj = new JSONObject(jsonData);
            } catch (IOException ex) {
                endGame = true;
                System.err.println("Nepodařilo se přečíst data - " + ex);
                
            }
        } catch (Exception ex) {
            endGame = true;
            System.err.println("Nepodařilo se navázat spojení se servrem (GET) - " + ex);
        }
    }

    public int[][] getMap(){
        map = new int[obj.getInt("map_height")][obj.getInt("map_width")];
        
        JSONArray bot_map = obj.getJSONArray("map");
        for (int i = 0; i < bot_map.length(); i++){
            JSONArray a = bot_map.getJSONArray(i);
            for (int j = 0; j < a.length(); j++){
                map[i][j] = a.getInt(j);
            }
        }
        return(map);
    }
    
    public int[] getBotInfo(){
        JSONObject myBot = obj.getJSONArray("bots").getJSONObject(0);;
        for (int i = 0; i < obj.getJSONArray("bots").length(); i++){
            if(obj.getJSONArray("bots").getJSONObject(i).getBoolean("your_bot")){
                myBot = obj.getJSONArray("bots").getJSONObject(i);
                break;
            }
        }
        int[] myBotInfo = {myBot.getInt("x"), myBot.getInt("y"), myBot.getInt("orientation")};
        return (myBotInfo);
    }
    
    public MyPoint getTreasure(){
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                if (map[i][j] == 1){
                    return (new MyPoint(i, j));
                }
            }
        }
        return (new MyPoint(1000, 1000));
    }
    
    /*****POST*****/
    
    public void ActionTurnLeft(){
        post("turn_left");        
    }
    
    public void ActionTurnRight(){
        post("turn_right");
    }
    
    public void ActionStep(){
        post("step");
    }
    
    private void post(String s){
        try{
            URLConnection connectionAction = new URL(server + "action").openConnection();
            connectionAction.setDoOutput(true);
            try (OutputStreamWriter out = new OutputStreamWriter(connectionAction.getOutputStream())) {
                out.write("bot_id=" + id + "&action=" + s);
            } catch (IOException ex) {
                endGame = true;
                System.err.println("Nepodařilo se odeslat data - " + ex);
            }
            
            String jsonData = "";
            String inputLine;
            
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connectionAction.getInputStream()))) {
                while ((inputLine = in.readLine()) != null){
                    jsonData += inputLine + "\n";
                }
                obj = new JSONObject(jsonData);
                postRequest ++;
                System.out.printf("%2s %20s  state: %s\n", postRequest, "sending: " + s, obj.getString("state"));
                if(obj.getString("state").equals("game_won")){
                    endGame = true;
                }
            } catch (IOException ex) {
                endGame = true;
                System.err.println("Nepodařilo se přečíst data - " + ex);
            }
        } catch(IOException ex){
            endGame = true;
            System.err.println("Nepodařilo se navázat spojení se servrem (POST) - " + ex);
        }
    }
    
    public boolean getEndGame(){
        return endGame;
    }
}