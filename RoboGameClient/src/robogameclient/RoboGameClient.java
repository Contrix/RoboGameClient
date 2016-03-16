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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Jirka
 */
public class RoboGameClient extends Application {
    private final MenuBarComponent menuBarComponent = new MenuBarComponent();
    
    @Override
    public void start(Stage primaryStage){
        VBox root = new VBox();
        
        Scene scene = new Scene(root, 800, 800);
        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight()-25);//25 - height of menuBar
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        final Game game = new Game(gc, primaryStage);
        
        game.showServerNameDialog();
        game.rePaint();
        game.newGame();
        
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent event) -> {
            canvas.setWidth(scene.getWidth());
            canvas.setHeight(scene.getHeight()-25);//25 - height of menuBar
            //game.rePaint();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e ) -> {
            switch (e.getCode()) {   
                /*case S:
                    Thread mojeVlakno = new Thread(() -> {                        
                        game.startGame();                        
                    }, "SecondThread");
                    mojeVlakno.setDaemon(true);
                    mojeVlakno.start();
                    break;*/
                    
                case ESCAPE:
                    primaryStage.close();
                    break;

                case F5://autohra
                    Thread mojeVlakno2 = new Thread(() -> {
                        game.autoBot();
                    }, "SecondThread");
                    mojeVlakno2.setDaemon(true);
                    mojeVlakno2.start();
                    break;
                    
                default:
                    break;
            }
        });
        
        primaryStage.setOnCloseRequest(EventHandler ->{
            game.closeLog();
        });

        root.setAlignment(Pos.CENTER_LEFT);
        root.getChildren().addAll(menuBarComponent.getMenuBar(game), canvas);
        
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
