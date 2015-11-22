/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Jirka
 */
public class Game {
    private final Comunication com = new Comunication();
    private final Drawing drw = new Drawing();
    private final Wave wave = new Wave();
    private final GraphicsContext gc;
    
    public Game(GraphicsContext gc){
        this.gc = gc;
        com.initialise();
        drw.drawAll(gc, com.getMap(), com.getBotInfo());
    }
    
    public void startGame(){
        while(!com.getWin()){
            nextStep();
        }
    }
    
    public void nextStep(){
        if(!com.getWin()){
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
    }
    
    public void rePaint(){
        Thread mojeVlakno = new Thread(() -> {
            if(!com.getWin()){
                com.refreshData();
                drw.drawAll(gc, com.getMap(), com.getBotInfo());
            }
        }, "ThirdThread");
        mojeVlakno.setDaemon(true);
        mojeVlakno.start();
    }
    
    public void step(){
        if(!com.getWin()){
            com.refreshData();
            wave.getAction(com.getMap(), new MyPoint(com.getBotInfo()[1], com.getBotInfo()[0]), com.getTreasure(), com.getBotInfo()[2]);
            com.ActionStep();
        }
        rePaint();
    }
    
    public void turnLeft(){
        if(!com.getWin()){
            com.refreshData();
            wave.getAction(com.getMap(), new MyPoint(com.getBotInfo()[1], com.getBotInfo()[0]), com.getTreasure(), com.getBotInfo()[2]);
            com.ActionTurnLeft();
        }
        rePaint();
    }
    
    public void turnRight(){
        if(!com.getWin()){
            com.refreshData();
            wave.getAction(com.getMap(), new MyPoint(com.getBotInfo()[1], com.getBotInfo()[0]), com.getTreasure(), com.getBotInfo()[2]);
            com.ActionTurnRight();
        }
        rePaint();
    }
}

            /*try{
                Thread.sleep(1000);
            }
            catch (Exception ex){
                
            }*/