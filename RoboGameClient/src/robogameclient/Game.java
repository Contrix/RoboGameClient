/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

/**
 *
 * @author Jirka
 */
public class Game {
    private Comunication com = new Comunication();
    private final Wave wave = new Wave();
    private final Drawing drw = new Drawing();
    
    public void startGame() throws Exception{
        com.initialise();
        
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
            }
        } */   
       for (int i = 0; i <2; i++){
            //com.refreshData();
            switch (wave.getAction(com.getMap(), new MyPoint(com.getBotInfo()[1], com.getBotInfo()[0]), com.getTreasure(), com.getBotInfo()[2])){
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
            }
        }
    }
    
}
