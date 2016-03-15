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
import org.json.JSONException;
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
    private boolean[] gameInfo = new boolean[3];//tahová hra, baterky, lasery
    
    ArrayList<Bot> bots = new ArrayList<>();
    ArrayList<Treasure> treasures = new ArrayList<>();
    
    
    private int map[][];
    private JSONObject obj;
    
    private LogDialog logDialog;
    
    /*****GET*****/
    
    /**
    * Ze serveru si načte základní data
    * @return  Připojení bylo úspěšné/neúspěšné
    */
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
                
                refreshGameInfo();
                refreshData();
                
                return true;
            } catch (IOException ex) {
                System.err.println("Nepodařilo se přečíst data - " + ex);
            }
        } catch (IOException | JSONException ex) {
            System.err.println("Nepodařilo se navázat spojení se servrem (GET) - " + ex);
        }
        activeGame = false;
        return false;        
    }
    
    /**
     * Obnoví informace o hře
    */
    private void refreshGameInfo(){
        gameInfo[0] = obj.getJSONObject("game_info").getBoolean("rounded_game");
        gameInfo[1] = obj.getJSONObject("game_info").getBoolean("battery_game");
        gameInfo[2] = obj.getJSONObject("game_info").getBoolean("laser_game");
    }
    
    /**
     * Obnoví stávající data
    */
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
        } catch (IOException | JSONException ex) {
            activeGame = false;
            System.err.println("Nepodařilo se navázat spojení se servrem (GET) - " + ex);
        }
        
        refreshMap();
    }
    
    /**
    * Obnoví informace o mapě
    */
    private void refreshMap(){
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
                        bots.add(new Bot(i,j, (int)a.getJSONObject(j).getInt("orientation"),0));
                    }
                    else{
                        bots.add(new Bot(i,j, (int)a.getJSONObject(j).getInt("orientation"),0));
                    }
                }
                
                //==je poklad
                //treasures.add(new Treasure(x, y));
                
            }
        }
    }
    
    /**
    * @return  Nastavení hry
    */
    public boolean[] getGameInfo(){
        return gameInfo;
    }  
    
    /**
    * @return  Mapa hry
    */
    public int[][] getMap(){
        return map;
    }    
    
    /**
    * @return  Bot stávajícího klienta
    */
    public Bot getMyBot(){
        refreshMap();//asi smazat        
        return myBot;
    }
    
    /**
    * @return  Všechyn boty na mapě
    */
    public ArrayList getBots(){
        refreshMap();//asi smazat        
        return bots;
    }
    
    /**
    * @return  Poklady
    */
    public ArrayList getTreasure(){
        return treasures;
    }
    
    /*****POST*****/
    
    /**
     * Vytvoří požadavek pro server pro akci otočit se doleva
     */
    public void actionTurnLeft(){
        post("turn_left");        
    }
    
    /**
     * Vytvoří požadavek pro server pro akci otočit se doprava
     */
    public void actionTurnRight(){
        post("turn_right");
    }
    
    /**
     * Vytvoří požadavek pro server pro akci krok vpřed
     */
    public void actionStep(){
        post("step");
    }
    
    /**
     * Vytvoří požadavek pro server pro akci vzdát se tahu
     */
    public void actionWait(){
        post("wait");
    }
    
    /**
     * Vytvoří požadavek pro server pro akci střelba laserem
     */
    public void actionLaserBeam(){
        post("laser_beam");
    }
    
    /**
     * Odešle data na server
     * @param s Data k odeslání
     */
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
    
    /**
     * Vrátí, zda je hra aktivní
     * @return Aktivní hra
     */
    public boolean isActiveGame(){
        return activeGame;
    }
    
    /**
     * Nastaví log
     * @param logDialog 
     */
    public void setLog(LogDialog logDialog){
        this.logDialog = logDialog;
    }
    
    /**
     * Zapíše do uživatelského logu
     * @param text Textový zápis
    */
    private void writeToLog(String text){
        Platform.runLater(() -> {
            logDialog.addMsg(text);
        });
    }
    
    /**
     * Nastaví asresu server
     * @param name URI serveru
    */
    public void setServerName(String name){
        server = name;
        //initialise();
    }
}