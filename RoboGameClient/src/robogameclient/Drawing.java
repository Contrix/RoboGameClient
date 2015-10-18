/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Jirka
 */
public class Drawing {
    private int pixel = 20;
    private final double width = 800;
    private final double height = 800;

    public void drawArray(GraphicsContext gc, int[][] array, int[] botInfo) throws Exception{
        checkPixel(array);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[0].length; j++){
                switch(array[i][j]){
                    case 0://volno
                        gc.setFill(Color.GREEN);
                        break;
                    case 1://poklad
                        gc.setFill(Color.YELLOW);
                        break;
                    case 2://bot
                        gc.setFill(Color.LIGHTGREY);
                        break;
                    default:
                        break;
                }
                gc.fillRect(j * pixel, i * pixel, pixel, pixel);
            }
        }
        gc.setFill(Color.BLACK);
        switch(botInfo[2]){
            case 0:
                gc.fillOval(botInfo[0] * pixel + pixel*3/8, botInfo[1] * pixel, pixel/4, pixel/4);
                break;
            case 1:
                gc.fillOval(botInfo[0] * pixel + pixel*3/4, botInfo[1] * pixel + pixel*3/8, pixel/4, pixel/4);
                break;
            case 2:
                gc.fillOval(botInfo[0] * pixel  + pixel*3/8, botInfo[1] * pixel + pixel*3/4, pixel/4, pixel/4);
                break;
            case 3:
                gc.fillOval(botInfo[0] * pixel, botInfo[1] * pixel + pixel*3/8, pixel/4, pixel/4);
                break;
            default:
                break;
        }
    }
    
    private void checkPixel(int[][] array){
        while (array[0].length * pixel + pixel > width){
            pixel--;
        }
        while (array.length * pixel + pixel > height){
            pixel--;
        }
        while (array[0].length * pixel  < width - pixel){
            if (array.length * pixel < height){
                if (pixel < 50){
                    pixel++;
                }
                else{
                    break;
                }
            }
            else{
                break;
            }
        }
    }
    
    public int getPixel(){
        return pixel;
    }
}
