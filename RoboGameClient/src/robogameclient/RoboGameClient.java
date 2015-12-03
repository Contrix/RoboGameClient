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
import static javafx.scene.input.KeyCode.ESCAPE;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Jirka
 */
public class RoboGameClient extends Application {
    private LogDialog logDialog = new LogDialog();
    
    @Override
    public void start(Stage primaryStage){
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 800);
        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        final Game game = new Game(gc);
        game.setLog(logDialog);
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent event) -> {
            canvas.setWidth(scene.getWidth());
            canvas.setHeight(scene.getHeight());
            game.rePaint();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        
        
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e ) -> {
            switch (e.getCode()) {   
                case N:
                    game.nextStep();                    
                    break;
                    
                case S:
                    Thread mojeVlakno = new Thread(() -> {                        
                        game.startGame();                        
                    }, "SecondThread");
                    mojeVlakno.setDaemon(true);
                    mojeVlakno.start();
                    break;
                    
                case ESCAPE:
                    primaryStage.close();
                    break;
                    
                case UP:
                    game.step();
                    break;
                    
                case LEFT:
                    game.turnLeft();
                    break;
                    
                case RIGHT:
                    game.turnRight();
                    break;
                
                case F5://autohra
                    Thread mojeVlakno2 = new Thread(() -> {
                        game.startGame();
                    }, "SecondThread");
                    mojeVlakno2.setDaemon(true);
                    mojeVlakno2.start();
                    break;
                case F6://automatické zapínání dalších her
                    game.setAutoNewGame();
                    break;
                case F7://zpoždění +
                    game.setDelayPlus();
                    break;
                case F8:// zpoždění -
                    game.setDelayMinus();
                    break;
                case F9:// zobrazení logu
                    logDialog.showDialog(primaryStage.getOwner());
                    break;
                case F10:// nová hra
                    game.newGame();
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
