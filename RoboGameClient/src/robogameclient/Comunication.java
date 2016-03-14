package robogameclient;

import Obj.Bot;
import Obj.Treasure;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
    private boolean activeGame;
    private Bot myBot; //xy yx, orientace, baterka
    
    ArrayList<Bot> bots = new ArrayList<>();
    ArrayList<Treasure> treasures = new ArrayList<>();
    
    
    private int map[][];
    private JSONObject obj;
    
    private LogDialog logDialog;
    
    /*****GET*****/

    public boolean initialise(){//přesunout id a další metody do try
        postRequest = 0;
        writeToLog("Vytvářím novou hru");
        
        String jsonData = "";
        String inputLine;
        try{
            URLConnection connectionToServer = new URL(server).openConnection();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connectionToServer.getInputStream()))) {
                while ((inputLine = in.readLine()) != null){
                    jsonData += inputLine + "\n";
                }
                id = new JSONObject(jsonData).getString("bot_id");
                System.out.println("bot_id: " + id);
                activeGame = true;
                refreshData();
                getMap();
                
                return true;
            } catch (IOException ex) {
                System.err.println("Nepodařilo se přečíst data - " + ex);
            }
        } catch (Exception ex) {
            System.err.println("Nepodařilo se navázat spojení se servrem (GET) - " + ex);
        }
        activeGame = false;
        return false;        
    }
    
    public void refreshData(){
        String jsonData = "";
        String inputLine;
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
    
    public int[][] getMap(){
        //map = new int[obj.getInt("map_height")][obj.getInt("map_width")];
        map = new int[(int)obj.getJSONArray("map_resolutions").get(1)][(int)obj.getJSONArray("map_resolutions").get(0)];
        
        JSONArray botMap = obj.getJSONArray("map");
        for (int i = 0; i < botMap.length(); i++){
            JSONArray a = botMap.getJSONArray(i);
            for (int j = 0; j < a.length(); j++){
                //všechny položky budou objekty..try pak smazat
                try {
                    map[i][j] = (int)a.getInt(j);
                }
                catch(Exception e){//bot
                    map[i][j] = 2;
                    if (a.getJSONObject(j).has("your_bot")){
                        myBot = new Bot(i,j, (int)a.getJSONObject(j).getInt("orientation"),0);
                    }
                    else{
                        bots.add(new Bot(i,j, (int)a.getJSONObject(j).getInt("orientation"),0));
                    }
                }
                
                //==je poklad
                //treasures.add(new Treasure(x, y));
                
            }
        }
        return(map);
    }    
    
    
    public Bot getMyBot(){
        getMap();//asi smazat        
        return myBot;
    }
    
    public ArrayList getBots(){
        getMap();//asi smazat        
        return bots;
    }
    
    public ArrayList getTreasure(){
        return treasures;
    }
    
    /*****POST*****/
    
    public void actionTurnLeft(){
        post("turn_left");        
    }
    
    public void actionTurnRight(){
        post("turn_right");
    }
    
    public void actionStep(){
        post("step");
    }
    
    public void actionWait(){//jména
        post("wait");
    }
    
    public void actionLaser(){//jména
        post("laser");
    }
    
    private void post(String s){//doladit
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
                //System.out.printf("%2s %20s  state: %s\n", postRequest, "sending: " + s, obj.getString("state"));
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