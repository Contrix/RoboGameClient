/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

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
    private double width = 800;
    private double height = 800;
    private int moveX = 0;
    private int moveY = 0;

    public void drawWindow(GraphicsContext gc, int[] gameInfo){
        checkPixel(gc);
        gc.setFill(Color.ANTIQUEWHITE);
        gc.fillRect(0, 0, width, height);
        
        drawInfo(gc, gameInfo);        
    }
    
    
    public void drawGame(GraphicsContext gc, int[][] array, int[] botInfo, int[] gameInfo){
        drawWindow(gc, botInfo);
        checkPixelllll(gc, array[0].length, array.length);
        
        drawArray(gc, array, botInfo);        
    }
    
    
    
    private void checkPixel(GraphicsContext gc){
        width = gc.getCanvas().getWidth();
        height = gc.getCanvas().getHeight();
        
        while (pixel * 15 >= width){
            pixel--;
        }
        while (pixel * 15 >= height){
            pixel--;
        }
        while (pixel * 15 < width && pixel * 15  < height){
            pixel++;
        }
    }
    
    private void checkPixelllll(GraphicsContext gc, int x, int y){
        width = gc.getCanvas().getWidth();
        height = gc.getCanvas().getHeight();
        
        while (pixel * x > width - pixel){
            pixel--;
            //System.out.println("-1");
        }
        while (pixel * (y + 2 + 7) > height){
            pixel--;
            //System.out.println("-2");
        }
        //while (pixel * x + pixel < width && pixel * (y + 2) + pixel < height){
        while (pixel * x < width - 5 * pixel && pixel * y  < height - 12 * pixel){
            pixel++;
            //System.out.println("+");
        }
        moveX = (int)(width - x * pixel)/2; 
        moveY = (int)(height - y * pixel)/2;
    }
    
    private void drawArray(GraphicsContext gc, int[][] array, int[] botInfo){
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[0].length; j++){
                switch(array[i][j]){
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
                gc.fillRect(j * pixel + moveX, i * pixel + moveY + pixel/2, pixel, pixel);
            }
        }
        gc.setFill(Color.BLACK);
        switch(botInfo[2]){
            case 0:
                gc.fillOval(botInfo[0] * pixel + pixel*3/8 + moveX, botInfo[1] * pixel + moveY + pixel/2, pixel/4, pixel/4);
                break;
            case 1:
                gc.fillOval(botInfo[0] * pixel + pixel*3/4 + moveX, botInfo[1] * pixel + pixel*3/8 + moveY + pixel/2, pixel/4, pixel/4);
                break;
            case 2:
                gc.fillOval(botInfo[0] * pixel  + pixel*3/8 + moveX, botInfo[1] * pixel + pixel*3/4 + moveY + pixel/2, pixel/4, pixel/4);
                break;
            case 3:
                gc.fillOval(botInfo[0] * pixel + moveX, botInfo[1] * pixel + pixel*3/8 + moveY + pixel/2, pixel/4, pixel/4);
                break;
            default:
                break;
        }
    }
    
    private void drawInfo(GraphicsContext gc, int[] gameInfo){
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, pixel));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("PyBots", width/2, pixel);
        
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText(String.format("© Jiří Hanák"), width - pixel/4, height - pixel/4);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText(String.format("v 0.6"), pixel/4, height - pixel/4);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(String.format(
                String.valueOf(gameInfo[0]) + " - " + 
                String.valueOf(gameInfo[1]) +
                String.valueOf(gameInfo[2])), width/2, height - pixel/4);
    }
}
