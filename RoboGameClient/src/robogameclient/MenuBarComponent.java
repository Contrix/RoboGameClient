/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author Jirka
 */
public class MenuBarComponent {
    private final MenuBar menuBar = new MenuBar();
    
    public MenuBar getMenuBar(Game game){
        
        /*****menu Server*****/
        Menu menuServer = new Menu("Server");
        
        /**item servername**/
        MenuItem serverName = new MenuItem("Připojit k serveru");
        serverName.setOnAction((ActionEvent t) -> {
            game.showServerNameDialog(game.getStage());
        });        
        
        menuServer.getItems().addAll(serverName);
        
        /*****menu Settings*****/
        Menu menuSettings = new Menu("Nastavení");
        
        /**item autogame**/
        CheckMenuItem autoGame = new CheckMenuItem("Zapnout PyBota");
        autoGame.setAccelerator(KeyCombination.keyCombination("F5"));
        autoGame.selectedProperty().addListener(listener -> {
            System.out.println("autoGame - F5");
        });
        
        /**item autostart**/
        CheckMenuItem autostart = new CheckMenuItem("Po dokončení začít novou hru");
        autostart.setAccelerator(KeyCombination.keyCombination("F6"));
        autostart.selectedProperty().addListener(listener -> {
            System.out.println("autoStart - F6");
            game.setAutoNewGame();
        });
        
        /**item newGame**/
        MenuItem newGame = new MenuItem("Nová hra");
        newGame.setAccelerator(KeyCombination.keyCombination("F7"));
        newGame.setOnAction((Action) -> {
            System.out.println("newGame - F7");
            game.newGame();
        });
        
        /**item showLog**/
        MenuItem showLog = new MenuItem("Zobrazit log");
        showLog.setAccelerator(KeyCombination.keyCombination("F8"));
        showLog.setOnAction((Action) -> {
            System.out.println("showLog - F8");
            try{
                game.getLog().showDialog(game.getStage().getOwner());
            }catch(Exception ex){
                System.err.println("Nelze zobrazit více logů!");
            }
        });
        
        /**item delayPlus**/
        MenuItem delayPlus = new MenuItem("Zvětšit prodlevu mezi tahy");
        delayPlus.setAccelerator(KeyCombination.keyCombination("F9"));
        delayPlus.setOnAction((ActionEvent) -> {
            System.out.println("delay+ - F7");
            game.setDelayPlus();
        });
        
        /**item delayMinus**/
        MenuItem delayMinus = new MenuItem("Snížit prodlevu mezi tahy");
        delayMinus.setAccelerator(KeyCombination.keyCombination("F10"));
        delayMinus.setOnAction((ActionEvent) -> {
            System.out.println("delay- - F8");
            game.setDelayMinus();
        });

        menuSettings.getItems().addAll(autoGame, autostart, newGame, showLog, delayPlus, delayMinus);
        
        /*****menu control*****/
        Menu menuControl = new Menu("Ovládání");
        
        /**item nextStep**/
        MenuItem nextStep = new MenuItem("Jeden krok algoritmu");
        nextStep.setAccelerator(KeyCombination.keyCombination("N"));
        nextStep.setOnAction((ActionEvent) -> {
            System.out.println("nextStep - N");
            game.nextStep();  
        });
        
        /**item move**/
        MenuItem move = new MenuItem("Pohyb vpřed");
        move.setAccelerator(KeyCombination.keyCombination("W"));
        move.setOnAction((ActionEvent) -> {
            System.out.println("move - W");
            game.step();
        });
        
        /**item turnLeft**/
        MenuItem turnLeft = new MenuItem("Otočit doleva");
        turnLeft.setAccelerator(KeyCombination.keyCombination("A"));
        turnLeft.setOnAction((ActionEvent) -> {
            System.out.println("turnLeft - A");
            game.turnLeft();
        });
        
        /**item turnRight**/
        MenuItem turnRight = new MenuItem("Otočit doprava");
        turnRight.setAccelerator(KeyCombination.keyCombination("D"));
        turnRight.setOnAction((ActionEvent) -> {
            System.out.println("turnRight - D");
            game.turnRight();
        });        
        
        /**item laser**/
        MenuItem laser = new MenuItem("Střelba laserem");
        laser.setAccelerator(KeyCombination.keyCombination("SPACE"));
        laser.setOnAction((ActionEvent) -> {
            System.out.println("laser - SPACE");
        });
        
        /**item wait**/
        MenuItem wait = new MenuItem("Setrvat na místě");
        wait.setAccelerator(KeyCombination.keyCombination("S"));
        wait.setOnAction((ActionEvent) -> {
            System.out.println("wait - S");
        });
        
        menuControl.getItems().addAll(nextStep, move, turnLeft, turnRight, laser, wait);
        
        /*****menu help*****/
        Menu menuHelp = new Menu("Nápověda");
        
        /**item aboutApp**/
        MenuItem aboutApp = new MenuItem("O aplikaci");
        
        menuHelp.getItems().addAll(aboutApp);
 
        menuBar.getMenus().addAll(menuServer, menuControl, menuSettings, menuHelp);        
        return menuBar;
    }
}
