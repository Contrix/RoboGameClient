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
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Jirka
 */
public class RoboGameClient extends Application {
    private final MenuBarComponent menuBarComponent = new MenuBarComponent();
    private final Tooltip tooltip = new Tooltip();
    
    @Override
    public void start(Stage primaryStage){
        /**
         * výstup do souboru        
        PrintStream out;
        try {
            out = new PrintStream(new FileOutputStream("output.txt"));
            System.setOut(out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RoboGameClient.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        VBox root = new VBox();
        
        Scene scene = new Scene(root, 800, 800);
        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight()-25);//25 - height of menuBar
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        final Game game = new Game(gc, primaryStage);
        
        game.showServerNameDialog();        
        game.newGame();
        game.rePaint();
        
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent event) -> {
            canvas.setWidth(scene.getWidth());
            canvas.setHeight(scene.getHeight()-25);//25 - height of menuBar
            game.rePaint();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        
        /**
         * Zobrazení popisku u bota
         */        
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, 
        new EventHandler<MouseEvent>() 
        {
            int x;
            int y; 
            
            @Override
            public void handle(MouseEvent e) 
            {
                x = (int)(e.getX() - game.getDrawSettings()[2]);
                y = (int)(e.getY() - game.getDrawSettings()[3]);
                tooltip.hide();
                game.getBots().forEach(bot -> {
                    
                    if (x >= bot.getPosition().getX() * game.getDrawSettings()[1] &&
                            x < (bot.getPosition().getX() + 1) * game.getDrawSettings()[1] &&
                            y >= bot.getPosition().getY() * game.getDrawSettings()[1] &&
                            y < (bot.getPosition().getY() + 1) * game.getDrawSettings()[1]){
                        tooltip.setAnchorX((double)(bot.getPosition().getX() * game.getDrawSettings()[1] + game.getDrawSettings()[2]) + scene.getWindow().getX());
                        tooltip.setAnchorY((double)(bot.getPosition().getY() * game.getDrawSettings()[1] + game.getDrawSettings()[3]) + scene.getWindow().getY());
                        tooltip.setText(bot.getName() + "\nBaterka: " + String.valueOf(bot.getBatteryLevel()));
                        tooltip.show(primaryStage);
                    }
                });
            }
        });
        
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e ) -> {
            switch (e.getCode()) {   
                case ESCAPE:
                    primaryStage.close();
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
