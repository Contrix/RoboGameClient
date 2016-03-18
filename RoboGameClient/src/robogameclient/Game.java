/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

import Obj.Bot;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/**
 *
 * @author Jirka
 */
public class Game {
    private final static Comunication com = new Comunication();
    private final Drawing drw = new Drawing();
    private final Wave wave = new Wave();     
    
    private final ServerNameDialog serverNameDialog = new ServerNameDialog();
    private final GraphicsContext gc;
    private final Stage primaryStage;   
    
    private int delay = 0;
    private boolean[] gameInfo = new boolean[3]; //tahová hra, batttery game, laserová hra
    
    private boolean autoNewGame = false;
    private boolean autoMove = false;
    
    private boolean focus = false;
    private int focusLevel = 0;
    
    /**
     * Řídící jednotka celé hry
     * @param gc GraphicsContext
     * @param primaryStage stage
     */
    public Game(GraphicsContext gc, Stage primaryStage){
        this.gc = gc;
        this.primaryStage = primaryStage;
        //newGame();
    }
    
    /**
     * Vrátí stage
     * @return stage
     */
    public Stage getStage(){
        return primaryStage;
    }
    
    /**
     * Pokusí se vytvořit novou hru
     */
    public final void newGame(){
        com.initialise();
        gameInfo = com.getGameInfo();
        drw.draw(gc, com.getMap(), delay, com.getMyBot(), com.getBots(), focus, focusLevel);
        /*
        Thread mojeVlakno = new Thread(() -> {                        
            ***                    
        }, "SecondThread");
        mojeVlakno.setDaemon(true);
        mojeVlakno.start();*/
    }
    
    /**
     * Zapne/vypne automatické chování bota
     */
    public void autoBot(){
        /*
        autoMove = !autoMove;
        if(autoMove){
            while(!com.isActiveGame()){
                nextStep();
                if (delay !=0)
                    delay();
                if(!autoMove){
                    break;
                }
            }
        }*/
    }
    
    /**
     * Jeden krok algoritmu
     */
    public void nextStep(){//předělat
        if(com.isActiveGame()){
            com.refreshData();
            /*switch (wave.getAction(com.getMap(), new MyPoint(com.getMyBot()[1], com.getMyBot()[0]), com.getTreasure(), com.getMyBot()[2])){
                case "step":
                    com.actionStep();
                    break;
                case "rotateLeft":
                    com.actionTurnLeft();
                    break;
                case "rotateRight":
                    com.actionTurnRight();
                    break;
                default:
                    break;
                }*/
            rePaint();
        }
        /*if(autoNewGame){
            delay(2000);
            newGame();
        }*/
    }
    
    public void rePaint(){
        /*Thread thread = new Thread(() -> {
            if (com.isActiveGame())
                com.refreshData();
            Platform.runLater(() -> {
                drw.draw(gc, com.getMap(), delay, com.getMyBot(), com.getBots());
            });
        }, "ThirdThread");
        thread.setDaemon(true);
        thread.start();*/
        if (com.isActiveGame())
            com.refreshData();
        drw.draw(gc, com.getMap(), delay, com.getMyBot(), com.getBots(), focus, focusLevel);
    }
    
    /**
     * Odešle požadavek pro akci "krok"
     */
    public void step(){
        if(com.isActiveGame()){
            if (com.actionStep()){
                
            }
        }
        rePaint();
    }
    
    /**
     * Odešle požadavek pro akci "otoč se doleva"
     */
    public void turnLeft(){
        if(com.isActiveGame()){
            if (com.actionTurnLeft()){
                
            }
        }
        rePaint();
    }
    
    /**
     * Odešle požadavek pro akci "otoč se doprava"
     */
    public void turnRight(){
        if(com.isActiveGame()){
            if (com.actionTurnRight()){
                
            }
        }
        rePaint();
    }
    
    /**
     * Odešle požadavek pro akci "čekej"
     */
    public void actionWait(){
        if(com.isActiveGame()){
            if (com.actionWait()){
                
            }
        }
        rePaint();
    }
    
    /**
     * Odešle požadavek pro akci "vytřel laserem"
     */
    public void actionLaserBeam(){
        if(com.isActiveGame()){
            if (com.actionLaserBeam()){
                
            }
        }
        rePaint();
    }
    
    /**
     * nastaví zanínání nových her po dohrání
     */
    public void setAutoNewGame(){
        autoNewGame = !autoNewGame;
    }
    
    /**** Delay ****/
    
    private void delay(){
        try{
            Thread.sleep(delay);
        }
        catch (Exception ex){
        }
    }
    
    private void delay(int d){
        try{
            Thread.sleep(d);
        }
        catch (Exception ex){
        }
    }
    
    /**
     * Nastaví zpoždění mezi automatickými tahy o 100ms větší
     */
    public void setDelayPlus(){
        delay += 100;
    }
    
    /**
     * Nastaví zpoždění mezi automatickými tahy o 100ms menší
     */
    public void setDelayMinus(){
        delay -= 100;
        if(delay < 0){
            delay = 0;
        }
    }
    
    /**
     * Vrátí aktuální hodnotu zpoždění mezi automatickými tahy
     * @return zpoždění
     */
    public int getDelay(){
        return delay;
    }
    
    /**** LogDialog ****/
    
    public LogDialog getLog(){
        return com.getLog();
    }
    
    public void closeLog(){
        com.closeLog();
    }
    
    /****  ****/
    
    
    public void showServerNameDialog(){
        serverNameDialog.showDialog(primaryStage);
        System.out.println(serverNameDialog.getValue());    
        com.setServerName(serverNameDialog.getValue());
    }
    
    public boolean[] getGameInfo(){
        return gameInfo;
    }
    
    /**
     * Vrátí, zda je hra aktivní
     * @return aktivní hra
     */
    public boolean isActiveGame(){
        return com.isActiveGame();
    }
    
    /**
     * Vrátí ArrayList všech botů
     * @return bots
     */
    public ArrayList<Bot> getBots(){
        return com.getBots();
    }
    
    /**
     * Vrátí nastavení vykreslování
     * @return pixel, velikost vykrslovaného čtverce v poli, posun X, posun Y
     */
    public int[] getDrawSettings(){
        return drw.getDrawSettings();
    }
    
    public void setFocus(){
        focus = !focus;
    }
    
    public void plusFocusLevel(){
        focusLevel ++;
    }
    
    public void minusFocusLevel(){
        focusLevel --;
        if (focusLevel < 0)
            focusLevel = 0;
    }

}

