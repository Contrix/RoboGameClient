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
    
    public void drawArray(GraphicsContext gc, double width, double height, int[][] array){
        checkPixel(width, height, array);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[0].length; j++){
                if (array[i][j] == 0){
                    gc.setFill(Color.GREEN);
                }
                else if (array[i][j] == 1){
                    gc.setFill(Color.LIGHTGREY);
                }
                gc.fillRect(j * pixel, i * pixel, pixel, pixel);
            }
        }
        
    }
    
    private void checkPixel(double width, double height, int[][] array){
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
