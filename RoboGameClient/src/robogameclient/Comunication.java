package robogameclient;

import Obj.Bot;
import Obj.Treasure;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalTime;
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
    private final LogDialog logDialog = new LogDialog();
    private String server;//http://hroch.spseol.cz:44822/
    private String id;
    private int postRequest = 0;
    private boolean activeGame;
    private Bot myBot; //xy yx, orientace, baterka
    private final boolean[] gameInfo = new boolean[3];//tahová hra, baterky, lasery
    
    ArrayList<Bot> bots = new ArrayList<>();
    ArrayList<Treasure> treasures = new ArrayList<>();
    
    private int map[][];
    private JSONObject object;
    
    /*****GET*****/
    
    /**
    * Ze serveru si načte základní data
    * @return  Připojení bylo úspěšné/neúspěšné
    */
    public boolean initialise(){//přesunout id a další metody do try
        postRequest = 0;
        bots.clear();
        treasures.clear();
        myBot = null;
        writeToLog("Vytvářím novou hru");
        
        StringBuilder response = new StringBuilder();
        String inputLine;
        try{
            URLConnection connectionToServer = new URL(server + ":44822/init").openConnection();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connectionToServer.getInputStream()))) {
                while ((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                id = new JSONObject(response.toString()).getString("bot_id");
                System.out.println("bot_id: " + id);
                activeGame = true;
                
                refreshData();
                refreshGameInfo();
                                
                return true;
            } catch (IOException ex) {
                System.err.println("Nepodařilo se přečíst data - " + ex);
                activeGame = false;
            }
        } catch (IOException | JSONException ex) {
            System.err.println("Nepodařilo se navázat spojení se servrem (GET) - " + ex);
        }
        writeToLog("Nepodařilo se vytvořit novou hru");
        activeGame = false;
        return false;        
    }
    
    /**
     * Obnoví informace o hře
    */
    private void refreshGameInfo(){
        gameInfo[0] = object.getJSONObject("game_info").getBoolean("rounded_game");
        gameInfo[1] = object.getJSONObject("game_info").getBoolean("battery_game");
        gameInfo[2] = object.getJSONObject("game_info").getBoolean("laser_game");
    }
    
    /**
     * Obnoví stávající data
    */
    public void refreshData(){
        StringBuilder response = new StringBuilder();
        String inputLine;
        try{
            URLConnection connectionGetMap = new URL(server + ":44822/game/" + id).openConnection();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connectionGetMap.getInputStream()))) {
                while ((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }    
                object = new JSONObject(response.toString());
            } catch (IOException ex) {
                activeGame = false;
                System.err.println("Nepodařilo se přečíst data - " + ex);            
            }
        } catch (IOException | JSONException ex) {
            activeGame = false;
            System.err.println("Nepodařilo se navázat spojení se servrem (GET) - " + ex);
        }
        if (activeGame)
            refreshMap();
    }
    
    /**
    * Obnoví informace o mapě
    */
    private void refreshMap(){
        map = new int[(int)object.getJSONObject("game_info")
                .getJSONObject("map_resolutions")
                .getInt("height")][(int)object.getJSONObject("game_info")
                .getJSONObject("map_resolutions").getInt("width")];
        bots.clear();
        treasures.clear();
        
        JSONArray botMap = object.getJSONArray("map");
        for (int i = 0; i < botMap.length(); i++){
            JSONArray a = botMap.getJSONArray(i);
            for (int j = 0; j < a.length(); j++){
                    //map[i][j] = (int)a.getInt(j);
                    JSONObject obj = a.getJSONObject(j);
                map[i][j] = obj.getInt("field");

                switch (obj.getInt("field")){
                    case 1:
                        treasures.add(new Treasure(j, i));
                        break;
                    case 2:
                        if (obj.has("your_bot")){
                            myBot = new Bot(j, i, (int)obj.getInt("orientation"), 10, obj.getString("name"));
                            bots.add(new Bot(j, i, (int)obj.getInt("orientation"), 10, obj.getString("name")));
                        }
                        else{
                            bots.add(new Bot(j, i, (int)obj.getInt("orientation"), 10, obj.getString("name")));
                        }
                        break;
                    case 4:
                        if (obj.has("your_bot")){
                            myBot = new Bot(j, i, (int)obj.getInt("orientation"), obj.getInt("battery_level"), obj.getString("name"));
                            bots.add(new Bot(j, i, (int)obj.getInt("orientation"), obj.getInt("battery_level"), obj.getString("name")));
                        }
                        else{
                            bots.add(new Bot(j, i, (int)obj.getInt("orientation"), obj.getInt("battery_level"), obj.getString("name")));
                        }
                        break;
                    default:
                        break;
                }
                
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
        return myBot;
    }
    
    /**
    * @return  Všechyn boty na mapě
    */
    public ArrayList<Bot> getBots(){
        return bots;
    }
    
    /**
    * @return  Poklady
    */
    public ArrayList<Treasure> getTreasure(){
        return treasures;
    }
    
    /*****POST*****/
    
    /**
     * Vytvoří požadavek pro server pro akci otočit se doleva
     * @return Povedlo se odeslat
     */
    public boolean actionTurnLeft(){
        return post("turn_left");        
    }
    
    /**
     * Vytvoří požadavek pro server pro akci otočit se doprava
     * @return Povedlo se odeslat
     */
    public boolean actionTurnRight(){
        return post("turn_right");
    }
    
    /**
     * Vytvoří požadavek pro server pro akci krok vpřed
     * @return Povedlo se odeslat
     */
    public boolean actionStep(){
        return post("step");
    }
    
    /**
     * Vytvoří požadavek pro server pro akci vzdát se tahu
     * @return Povedlo se odeslat
     */
    public boolean actionWait(){
        return post("wait");
    }
    
    /**
     * Vytvoří požadavek pro server pro akci střelba laserem
     * @return Povedlo se odeslat
     */
    public boolean actionLaserBeam(){
        return post("laser_beam");
    }
    
    /**
     * Odešle data na server
     * @param s Data k odeslání
     * @return Povedlo se odeslat
     */
    private boolean post(String s){//doladit
        LocalTime time = null;
        try{
            URLConnection connectionAction = new URL(server + ":44822/action").openConnection();
            connectionAction.setDoOutput(true);
            try (OutputStreamWriter out = new OutputStreamWriter(connectionAction.getOutputStream())) {
                out.write("bot_id=" + id + "&action=" + s);
            } catch (IOException ex) {
                activeGame = false;
                System.err.println("Nepodařilo se odeslat data - " + ex);
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connectionAction.getInputStream()))) {
                time = LocalTime.now();
                while ((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }                
                object = new JSONObject(response.toString());
                postRequest ++;
                writeToLog(String.format("%3s %30s  state: %s\n", postRequest, "sending: " + s, object.getString("state")));                
                if(object.getString("state").equals("game_won")){
                    activeGame = false;
                }
                //return true;
            } catch (IOException ex) {
                activeGame = false;
                System.err.println("Nepodařilo se přečíst data - " + ex);
                
            }
        } catch(IOException ex){
            activeGame = false;
            System.err.println("Nepodařilo se navázat spojení se servrem (POST) - " + ex);
        }
        LocalTime time2 = LocalTime.now();
        time2 = time2.minusHours(time.getHour());
        time2 = time2.minusMinutes(time.getMinute());
        time2 = time2.minusNanos(time.getNano());
        time2 = time2.minusSeconds(time.getSecond());
        System.out.println("Doba POSTu: " + time2);
        if (!activeGame)
            writeToLog("Problém s komunikací se serverem, hra byla zrušena");
        return activeGame;
        //return false;
    }
    
    /**
     * Vrátí, zda je hra aktivní
     * @return Aktivní hra
     */
    public boolean isActiveGame(){
        return activeGame;
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
     * Uzavře log
     */
    public void closeLog(){
        logDialog.closeDialog();
    }
    
    /**
     * Vrátí insatnci logu
     * @return LogDialog
     */
    public LogDialog getLog(){
        return logDialog;
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