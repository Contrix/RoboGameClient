/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Jirka
 */
public class RoboGameClient extends Application {
    
    private final Drawing drw = new Drawing();
    private final Wave wave = new Wave();
    private int[][] array;
    private MyPoint start;
    private MyPoint end;
    
    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 540, 620);
        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        setArray();
        drw.drawArray(gc, scene.getWidth(), scene.getHeight(), array);
        
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(100), (ActionEvent event) -> {
            drw.drawArray(gc, scene.getWidth(), scene.getHeight(), array);
            
            canvas.setWidth(array[0].length * drw.getPixel());
            canvas.setHeight(array.length * drw.getPixel());
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(canvas);
        primaryStage.setTitle("RoboGame");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void setArray(){
        array = new int[][]{{0,0,0,1,0,1,0,0},
                            {0,0,0,1,0,1,0,0},
                            {0,1,0,0,0,1,0,0},
                            {0,0,1,0,0,1,0,0},
                            {0,0,0,1,0,1,1,0}};
        
        /*System.out.println(array.length);
        System.out.println(array[0].length);*/
        start = new MyPoint(0, 0);
        end = new MyPoint(4, 4); //y, x
        
        setMove();
    }
    
    private void setMove(){
        switch (wave.getCourse(array, start, end, 1)){
            case -1:
                break;
            case 0://jdi rovně
                break;
            case 1:
                break;
            case 2:
                break;
            case 3://otoč doprava
                break;
            case 4://otoč doleva
                break;
            default:
                break;
        }
    }
}
