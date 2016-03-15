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
    private final Comunication com = new Comunication();
    private final Drawing drw = new Drawing();
    private final Wave wave = new Wave();
    private final GraphicsContext gc;
    private int[] myGameInfo = {0};//delay, 
    private boolean[] settingsOfGame = {true, true, true}; //tahová hra, batttery game, laserová hra
    private boolean autoNewGame = false;
    private boolean autoMove = false;
    private boolean activGame = false;    
    private final Stage primaryStage;    
    private final LogDialog logDialog = new LogDialog();
    private final ServerNameDialog serverNameDialog = new ServerNameDialog();
    
    public Game(GraphicsContext gc, Stage primaryStage){
        this.gc = gc;
        this.primaryStage = primaryStage;
        com.setLog(logDialog);
        drw.drawWindow(gc, myGameInfo);
        //newGame();
    }
    
    public Stage getStage(){
        return primaryStage;
    }
    
    public final void newGame(){
        com.initialise();
        autoMove = false;
        drw.drawGame(gc, com.getMap(), com.getMyBot(), myGameInfo);
    }
    
    public void startGame(){//předělat..
        autoMove = !autoMove;
        if(autoMove){
            while(!com.isActiveGame()){
                nextStep();
                delay();
                if(!autoMove){
                    break;
                }
            }
        }
    }
    
    public void nextStep(){
        if(!com.isActiveGame()){
            com.refreshData();
            switch (wave.getAction(com.getMap(), new MyPoint(com.getMyBot()[1], com.getMyBot()[0]), com.getTreasure(), com.getMyBot()[2])){
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
                }
            rePaint();
        }
        if(com.isActiveGame() && autoNewGame){
            delay(2000);
            com.initialise();
        }
    }
    
    public void rePaint(){
        //if (activGame){
            try{
                Thread thread = new Thread(() -> {

                    if(!com.isActiveGame()){
                        com.refreshData();
                        Platform.runLater(() -> {
                            try{
                                drw.drawGame(gc, com.getMap(), com.getMyBot(), myGameInfo);
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
        /*}
        else
            drw.drawWindow(gc, gameInfo);*/
    }
    
    public void step(){
        if(!com.isActiveGame()){
            com.refreshData();
            wave.getAction(com.getMap(), new MyPoint(com.getMyBot()[1], com.getMyBot()[0]), com.getTreasure(), com.getMyBot()[2]);//?
            com.actionStep();
        }
        rePaint();
    }
    
    public void turnLeft(){
        if(!com.isActiveGame()){
            com.refreshData();
            wave.getAction(com.getMap(), new MyPoint(com.getMyBot()[1], com.getMyBot()[0]), com.getTreasure(), com.getMyBot()[2]);//?
            com.actionTurnLeft();
        }
        rePaint();
    }
    
    public void turnRight(){
        if(!com.isActiveGame()){
            com.refreshData();
            wave.getAction(com.getMap(), new MyPoint(com.getMyBot()[1], com.getMyBot()[0]), com.getTreasure(), com.getMyBot()[2]);//?
            com.actionTurnRight();
        }
        rePaint();
    }

    public void setAutoNewGame(){
        autoNewGame = !autoNewGame;
    }
    
    /**** Delay ****/
    
    private void delay(){
        try{
            Thread.sleep(myGameInfo[0]);
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
        myGameInfo[0] += 100;
    }
    
    public void setDelayMinus(){
        myGameInfo[0] -= 100;
        if(myGameInfo[0] < 0){
            myGameInfo[0] = 0;
        }
    }
    
    public int getDelay(){
        return myGameInfo[0];
    }
    
    /**** LogDialog ****/
    
    public LogDialog getLog(){
        return logDialog;
    }
    
    public void closeLog(){
        logDialog.closeDialog();
    }
    
    /****  ****/
    
    public boolean isActiveGame (){
        return activGame;
    }
    
    public void showServerNameDialog(){
        serverNameDialog.showDialog(primaryStage);
        System.out.println(serverNameDialog.getValue());    
        com.setServerName(serverNameDialog.getValue());
    }
    
    public boolean[] getSettingsOfGame(){
        return settingsOfGame;
    }
}

