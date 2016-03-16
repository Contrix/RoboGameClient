/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

import javafx.application.Platform;
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
        drw.draw(gc, com.getMap(), delay, com.getMyBot(), com.getBots());
    }
    
    /**
     * Zapne/vypne automatické chování bota
     */
    public void autoBot(){
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
        }
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
        drw.draw(gc, com.getMap(), delay, com.getMyBot(), com.getBots());
    }
    
    public void step(){
        if(com.isActiveGame()){
            com.actionStep();
        }
        rePaint();
    }
    
    public void turnLeft(){
        if(com.isActiveGame()){
            com.actionTurnLeft();
        }
        rePaint();
    }
    
    public void turnRight(){
        if(com.isActiveGame()){
            com.actionTurnRight();
        }
        rePaint();
    }
    
    public void actionWait(){
        if(com.isActiveGame()){
            com.actionWait();
        }
        rePaint();
    }

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
    
    public void setDelayPlus(){
        delay += 100;
    }
    
    public void setDelayMinus(){
        delay -= 100;
        if(delay < 0){
            delay = 0;
        }
    }
    
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
    
    public boolean isActiveGame(){
        return com.isActiveGame();
    }

}

