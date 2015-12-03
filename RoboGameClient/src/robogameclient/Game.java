/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Jirka
 */
public class Game {
    private Comunication com = new Comunication();
    private Drawing drw = new Drawing();
    private final Wave wave = new Wave();
    private final GraphicsContext gc;
    private int[] gameInfo = {0,0,0,0};//delay, automaticky další hra, automove
    private boolean autoNewGame = false;
    private boolean autoMove = false;
    private LogDialog logDialog;
    
    public Game(GraphicsContext gc){
        this.gc = gc;
        newGame();
    }
    
    public final void newGame(){
        com.initialise();
        autoMove = false;
        drw.drawAll(gc, com.getMap(), com.getBotInfo(), gameInfo);
    }
    
    public void startGame(){
        autoMove = !autoMove;
        if (autoMove){
            gameInfo[1] = 1;
        }
        else{
            gameInfo[1] = 0;
        }
        
        if(autoMove){
            while(!com.getEndGame()){
                nextStep();
                delay();
                if(!autoMove){
                    break;
                }
            }
        }
    }
    
    public void nextStep(){
        if(!com.getEndGame()){
            com.refreshData();
            switch (wave.getAction(com.getMap(), new MyPoint(com.getBotInfo()[1], com.getBotInfo()[0]), com.getTreasure(), com.getBotInfo()[2])){
                case "step":
                    com.ActionStep();
                    break;
                case "rotateLeft":
                    com.ActionTurnLeft();
                    break;
                case "rotateRight":
                    com.ActionTurnRight();
                    break;
                default:
                    break;
                }
            rePaint();
        }
        if(com.getEndGame() && autoNewGame){
            delay(2000);
            com.initialise();
        }
    }
    
    public void rePaint(){
        try{
            Thread thread = new Thread(() -> {
                
                if(!com.getEndGame()){
                    com.refreshData();
                    Platform.runLater(() -> {
                        try{
                            drw.drawAll(gc, com.getMap(), com.getBotInfo(), gameInfo);
                        }catch(Exception ex){
                            System.err.println("Nepodařilo se vykreslit mapu");
                        }
                    });                        
                }
                
            }, "ThirdThread");
            thread.setDaemon(true);
            thread.start();
        }catch (Exception ex){
            System.err.println("Nepodařilo se překreslení");
        }
    }
    
    public void step(){
        if(!com.getEndGame()){
            com.refreshData();
            wave.getAction(com.getMap(), new MyPoint(com.getBotInfo()[1], com.getBotInfo()[0]), com.getTreasure(), com.getBotInfo()[2]);
            com.ActionStep();
        }
        rePaint();
    }
    
    public void turnLeft(){
        if(!com.getEndGame()){
            com.refreshData();
            wave.getAction(com.getMap(), new MyPoint(com.getBotInfo()[1], com.getBotInfo()[0]), com.getTreasure(), com.getBotInfo()[2]);
            com.ActionTurnLeft();
        }
        rePaint();
    }
    
    public void turnRight(){
        if(!com.getEndGame()){
            com.refreshData();
            wave.getAction(com.getMap(), new MyPoint(com.getBotInfo()[1], com.getBotInfo()[0]), com.getTreasure(), com.getBotInfo()[2]);
            com.ActionTurnRight();
        }
        rePaint();
    }
    
    private void delay(){
        try{
            Thread.sleep(gameInfo[0]);
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
        gameInfo[0] += 100;
    }
    
    public void setDelayMinus(){
        gameInfo[0] -= 100;
        if(gameInfo[0] < 0){
            gameInfo[0] = 0;
        }
    }
    
    public int getDelay(){
        return gameInfo[0];
    }
    
    public void setAutoNewGame(){
        autoNewGame = !autoNewGame;
        if (autoNewGame){
            gameInfo[2] = 1;
        }
        else{
            gameInfo[2] = 0;
        }
    }
    
    public void setLog(LogDialog logDialog){
        com.setLog(logDialog);
    }
}

