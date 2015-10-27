/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

/**
 *
 * @author Jirka
 */
public class Game {
    private final Comunication com = new Comunication();
    private final Drawing drw = new Drawing();
    private final Wave wave = new Wave();
    private final GraphicsContext gc;
    
    public Game(GraphicsContext gc) throws Exception{
        this.gc = gc;
        com.initialise();
        drw.drawAll(gc, com.getMap(), com.getBotInfo());
    }
    
    public void startGame() throws Exception{
        /*while (com.getTreasure().getX() != com.getBotInfo()[0] && com.getTreasure().getY() != com.getBotInfo()[1]){
            com.refreshData();
            switch (wave.getAction(com.getMap(), new MyPoint(com.getBotInfo()[0], com.getBotInfo()[1]), com.getTreasure(), com.getBotInfo()[2])){
                case "step":
                    com.ActionStep();
                    break;
                case "rotateLeft":
                    com.ActionturnLeft();
                    break;
                case "rotateRight":
                    com.ActionturnRight();
                    break;
                default:
                    break;
            }*/
        if(!com.getWin()){
            for (int i = 0; i < 1; i++){
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
                //Thread.sleep(1000);
                //System.out.println("ssss");   
            }
        }
       com.refreshData();
       drw.drawAll(gc, com.getMap(), com.getBotInfo());
    }
    
    public void rePaint()throws Exception{
        drw.drawAll(gc, com.getMap(), com.getBotInfo());
    }
    
    public void step() throws Exception{
        com.refreshData();
        wave.getAction(com.getMap(), new MyPoint(com.getBotInfo()[1], com.getBotInfo()[0]), com.getTreasure(), com.getBotInfo()[2]);
        if(!com.getWin()){
            com.ActionStep();
        }
        com.refreshData();
        drw.drawAll(gc, com.getMap(), com.getBotInfo());
    }
    
    public void turnLeft() throws Exception{
        com.refreshData();
        wave.getAction(com.getMap(), new MyPoint(com.getBotInfo()[1], com.getBotInfo()[0]), com.getTreasure(), com.getBotInfo()[2]);
        if(!com.getWin()){
            com.ActionTurnLeft();
        }
        com.refreshData();
        drw.drawAll(gc, com.getMap(), com.getBotInfo());
    }
    
    public void turnRight() throws Exception{
        com.refreshData();
        wave.getAction(com.getMap(), new MyPoint(com.getBotInfo()[1], com.getBotInfo()[0]), com.getTreasure(), com.getBotInfo()[2]);
        if(!com.getWin()){
            com.ActionTurnRight();
        }
        com.refreshData();
        drw.drawAll(gc, com.getMap(), com.getBotInfo());
    }
}