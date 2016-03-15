/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

import Obj.Bot;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Jirka
 */
public class Drawing {
    private int pixel = 20;
    private int square;
    private double width = 800;
    private double height = 800;
    private int moveX = 0;
    private int moveY = 0;

    /**
     * Zadá požadavky pro vykrslení jednotlivých součástí
     * @param gc GraphicsContext
     * @param map podklad mapy
     * @param delay zpoždění mezi tahy
     * @param myBot moj bot
     * @param bots arrayList všech botů
     */
    public void draw(GraphicsContext gc, int[][] map, int delay, Bot myBot, ArrayList<Bot> bots){
        checkPixel(gc, map);
        gc.setFill(Color.ANTIQUEWHITE);
        gc.fillRect(0, 0, width, height);
        
        try{
            drawInfo(gc, delay);  
            drawMap(gc, map, myBot, bots);
        }catch(Exception e){
            System.out.println("Nepodařilo se vykreslit mapu.");
        }
    }
    
    /**
     * Upraví hodnotu pixel na optimální velikost
     * @param gc GraphicsContext
     */
    private void checkPixel(GraphicsContext gc, int[][] map){//doladit..opakuje se zbytečně
        width = gc.getCanvas().getWidth();
        height = gc.getCanvas().getHeight();
        
        while (pixel * 50 >= width){
            pixel--;
        }
        while (pixel * 50 >= height){
            pixel--;
        }
        while (pixel * 50 < width && pixel * 50  < height){
            pixel++;
        }
        if (map!= null){
            square = (int)(height - 5 * pixel)/map.length;
            if (square * map[0].length > width - 2 * pixel){
                square = (int)(width - 2 * pixel)/map[0].length;
            }
            moveX = (int)(width - map[0].length * square)/2;
            moveY = (int)(height - map.length * square)/2;
        }
        
    }
    
    /**
     * Vykreslí všechny informace, popisky
     * @param gc GraphicsContext
     * @param delay Zpoždění mezi tahy
     */
    private void drawInfo(GraphicsContext gc, int delay){
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 2 * pixel));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("PyBots", width/2, 2 * pixel);
        
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText(String.format("© Jiří Hanák"), width - pixel/4, height - pixel/4);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText(String.format("v 0.7.1"), pixel/4, height - pixel/4);
        gc.setTextAlign(TextAlignment.CENTER);
        if (delay != 0){
            gc.fillText("Zpoždění: " + String.format(String.valueOf(delay)), width/2, height - pixel/4);
        }
    }

    /**
     * Vykreslí podklady mapy
     * @param gc GraphicsContext
     * @param map poklad mapy
     * @param myBot můj bot
     * @param bots ArrayList všech botů
     */
    private void drawMap(GraphicsContext gc, int[][] map, Bot myBot, ArrayList<Bot> bots){
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                switch(map[i][j]){//upraví se
                    case 0://volno
                        gc.setFill(Color.LIGHTGREY);
                        break;
                    case 1://poklad
                        gc.setFill(Color.YELLOW);
                        break;
                    case 2://bot
                        gc.setFill(Color.ORANGE);
                        break;
                    case 3://blok
                        gc.setFill(Color.GREY);
                        break;
                    default:
                        break;
                }
                gc.fillRect(j * square + moveX, i * square + moveY + square/2, square, square);
            }
        }
        drawBots(gc, myBot, bots);
    }
    
    /**
     * Vykreslí všechny boty
     * @param gc GraphicsContext
     * @param myBot můj bot
     * @param bots všichni boti
     */
    private void drawBots(GraphicsContext gc, Bot myBot, ArrayList<Bot> bots){
        /*gc.setFill(Color.ORANGE);
        bots.forEach(bot -> {
            gc.fillRect(bot.getPosition().getX() * square + moveX, bot.getPosition().getY() * square + moveY + square/2, square, square);
        });*/
        
        //můj robot
        gc.setFill(Color.BLACK);
        switch(myBot.getOrientation()){//nefunguje
            case 0:
                gc.fillOval(myBot.getPosition().getX() * square + square*3/8 + moveX, 
                        myBot.getPosition().getY() * square + moveY + square/2, square/4, square/4);
                break;
            case 1:
                gc.fillOval(myBot.getPosition().getX() * square + square*3/4 + moveX, 
                        myBot.getPosition().getY() * square + square*3/8 + moveY + square/2, square/4, square/4);
                break;
            case 2:
                gc.fillOval(myBot.getPosition().getX() * square  + square*3/8 + moveX, 
                        myBot.getPosition().getY() * square + square*3/4 + moveY + square/2, square/4, square/4);
                break;
            case 3:
                gc.fillOval(myBot.getPosition().getX() * square + moveX, 
                        myBot.getPosition().getY() * square + square*3/8 + moveY + square/2, square/4, square/4);
                break;
            default:
                break;
        }
    }
}
