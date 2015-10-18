/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.F5;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
        
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
            switch (e.getCode()) {   
                case F5:
                try {
                    game.startGame();
                } catch (Exception ex) {
                    System.out.println("Chyba, hra nemůže začít.");
                }
                    break;
                    
                case ESCAPE:
                    primaryStage.close();
                    break;
                default:
                    break;
            }
        });

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
}
