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
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.F5;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Jirka
 */
public class RoboGameClient extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 800);
        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        final Game game = new Game(gc);
        
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent event) -> {
            try {
                canvas.setWidth(scene.getWidth());
                canvas.setHeight(scene.getHeight());
                game.rePaint();
            } catch (Exception ex) {
                Logger.getLogger(RoboGameClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        
        
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e ) -> {
            switch (e.getCode()) {   
                case N:
                try {
                    game.startGame();
                } catch (Exception ex) {
                    System.out.println("Chyba, hra nemůže pokračovat.");
                    Logger.getLogger(RoboGameClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                    break;
                    
                case ESCAPE:
                    primaryStage.close();
                    break;
                    
                case UP:
            {
                try {
                    game.step();
                } catch (Exception ex) {
                    Logger.getLogger(RoboGameClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    break;
                case LEFT:
            {
                try {
                    game.turnLeft();
                } catch (Exception ex) {
                    Logger.getLogger(RoboGameClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    break;
                case RIGHT:
            {
                try {
                    game.turnRight();
                } catch (Exception ex) {
                    Logger.getLogger(RoboGameClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    break;
                
                    
                default:
                    break;
            }
        });

        root.setAlignment(Pos.CENTER_LEFT);
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
}
