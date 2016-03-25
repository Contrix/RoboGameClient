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
    private final Comunication com = new Comunication();
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
    private int focusLevel = 1;
    
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
        autoMove = !autoMove;
        if(autoMove){
            Thread mojeVlakno = new Thread(() -> {        
                while(com.isActiveGame()){
                    nextStep();
                    if (delay !=0)
                        delay();
                    if(!autoMove){
                        break;
                    }
                }
            }, "SecondThread");
            mojeVlakno.setDaemon(true);
            mojeVlakno.start();
        }
    }
    
    /**
     * Jeden krok algoritmu
     */
    public void nextStep(){
        if(com.isActiveGame()){
            com.refreshData();
            if (com.getMyBot().getBatteryLevel() >= 5){
                switch (wave.getAction(com.getMap(), com.getMyBot().getPosition(), com.getTreasure().get(0).getPosition(), com.getMyBot().getOrientation())){
                    case "step":
                        com.actionStep();
                        break;
                    case "rotateLeft":
                        com.actionTurnLeft();
                        break;
                    case "rotateRight":
                        com.actionTurnRight();
                        break;
                    case "wait":
                        com.actionWait();
                        break;
                    case "laserBeam":
                        com.actionLaserBeam();
                        break;
                    default:
                        break;
                    }
            }
            else{
                com.actionWait();
            }
            rePaint();
        }
        /*if(autoNewGame){
            delay(2000);
            newGame();
        }*/
    }
    
    public void rePaint(){
        //Thread thread = new Thread(() -> {
            if (com.isActiveGame())
                com.refreshData();
            drw.draw(gc, com.getMap(), delay, com.getMyBot(), com.getBots(), focus, focusLevel);
            /*Platform.runLater(() -> {
                drw.draw(gc, com.getMap(), delay, com.getMyBot(), com.getBots());
            });*/
        /*}, "ThirdThread");
        thread.setDaemon(true);
        thread.start();*/
        
    }
    
    /**
     * Odešle požadavek pro akci "krok"
     */
    public void step(){
        //Thread threadStep = new Thread(() -> {
            if(com.isActiveGame()){
                if (com.actionStep()){

                }
            }
            rePaint();
        /*}, "StepThread");
        threadStep.setDaemon(true);
        threadStep.start();*/
    }
    
    /**
     * Odešle požadavek pro akci "otoč se doleva"
     */
    public void turnLeft(){
        //Thread threadLeft = new Thread(() -> {
            if(com.isActiveGame()){
                if (com.actionTurnLeft()){

                }
            }
            rePaint();
        /*}, "LeftThread");
        threadLeft.setDaemon(true);
        threadLeft.start();*/
    }
    
    /**
     * Odešle požadavek pro akci "otoč se doprava"
     */
    public void turnRight(){
        //Thread threadRigtht = new Thread(() -> {
            if(com.isActiveGame()){
                if (com.actionTurnRight()){

                }
            }
            rePaint();
        /*}, "RightThread");
        threadRigtht.setDaemon(true);
        threadRigtht.start();*/
    }
    
    /**
     * Odešle požadavek pro akci "čekej"
     */
    public void actionWait(){
        //Thread threadWait = new Thread(() -> {
            if(com.isActiveGame()){
                if (com.actionWait()){

                }
            }
            rePaint();
        /*}, "WaitThread");
        threadWait.setDaemon(true);
        threadWait.start();*/
    }
    
    /**
     * Odešle požadavek pro akci "vytřel laserem"
     */
    public void actionLaserBeam(){
        //Thread threadLaser = new Thread(() -> {
            if(com.isActiveGame()){
                if (com.actionLaserBeam()){

                }
            }
            rePaint();
        /*}, "LaserThread");
        threadLaser.setDaemon(true);
        threadLaser.start();*/
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
        if (focusLevel < 1)
            focusLevel = 1;
    }

}

