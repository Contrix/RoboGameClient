package robogameclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import javafx.application.Platform;
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
    private String server;//http://hroch.spseol.cz:44822/
    private String id;
    private int postRequest = 0;
    private int map[][];
    private JSONObject obj;
    private boolean activeGame;
    private LogDialog logDialog;
    
    /*****GET*****/

    public void initialise(){//přesunout id a další metody do try
        activeGame = true;
        postRequest = 0;
        writeToLog("Creating new game");
        
        String jsonData = "";
        String inputLine;
        try{
            URLConnection connectionToServer = new URL(server).openConnection();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connectionToServer.getInputStream()))) {
                while ((inputLine = in.readLine()) != null){
                    jsonData += inputLine + "\n";
                }
                //sem
                //return true;
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
        //return false;        
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
                activeGame = false;
                System.err.println("Nepodařilo se přečíst data - " + ex);
                
            }
        } catch (Exception ex) {
            activeGame = false;
            System.err.println("Nepodařilo se navázat spojení se servrem (GET) - " + ex);
        }
    }
    
    private int[] bot;
    public int[][] getMap(){
        //map = new int[obj.getInt("map_height")][obj.getInt("map_width")];
        map = new int[(int)obj.getJSONArray("map_resolutions").get(1)][(int)obj.getJSONArray("map_resolutions").get(0)];
        
        JSONArray botMap = obj.getJSONArray("map");
        for (int i = 0; i < botMap.length(); i++){
            JSONArray a = botMap.getJSONArray(i);
            for (int j = 0; j < a.length(); j++){
                try {
                    map[i][j] = (int)a.getInt(j);
                }
                catch(Exception e){//bot
                    map[i][j] = 2;
                    try{
                        if (a.getJSONObject(j).getBoolean("your_bot")){
                            bot = new int[]{i,j, (int)a.getJSONObject(j).getInt("orientation")};
                        }
                    }
                    catch(Exception ex){
                        
                    }
                }
            }
        }
        return(map);
    }
    
    
    
    public int[] getBotInfo(){
        /*JSONObject myBot = obj.getJSONArray("bots").getJSONObject(0);;
        for (int i = 0; i < obj.getJSONArray("bots").length(); i++){
            if(obj.getJSONArray("bots").getJSONObject(i).getBoolean("your_bot")){
                myBot = obj.getJSONArray("bots").getJSONObject(i);
                break;
            }
        }
        int[] myBotInfo = {myBot.getInt("x"), myBot.getInt("y"), myBot.getInt("orientation")};
        return (myBotInfo);*/
        getMap();
        
        return bot;
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
                activeGame = false;
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
                writeToLog(String.format("%3s %30s  state: %s\n", postRequest, "sending: " + s, obj.getString("state")));                
                System.out.printf("%2s %20s  state: %s\n", postRequest, "sending: " + s, obj.getString("state"));
                if(obj.getString("state").equals("game_won")){
                    activeGame = false;
                }
            } catch (IOException ex) {
                activeGame = false;
                System.err.println("Nepodařilo se přečíst data - " + ex);
            }
        } catch(IOException ex){
            activeGame = false;
            System.err.println("Nepodařilo se navázat spojení se servrem (POST) - " + ex);
        }
    }
    
    public boolean isActiveGame(){
        return activeGame;
    }
    
    public void setLog(LogDialog logDialog){
        this.logDialog = logDialog;
    }
    
    private void writeToLog(String s){
        Platform.runLater(() -> {
            logDialog.addMsg(s);
        });
    }
    
    public void setServerName(String name){
        server = name;
        //initialise();
    }
}