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
    private final AboutAppDialog aboutAppDialog = new AboutAppDialog();
    private final UpdateDialog updateDialog = new UpdateDialog();
    
    public MenuBar getMenuBar(Game game){
        
        /*****menu game*****/
        Menu menuGame = new Menu("Hra");
        
        /**item newGame**/
        MenuItem newGame = new MenuItem("Nová hra");
        newGame.setAccelerator(KeyCombination.keyCombination("F2"));
        newGame.setOnAction((Action) -> {
            System.out.println("newGame - F7");            
            game.newGame();
            refreshSettings(game);
        });
        menuGame.getItems().add(newGame);
        
        /*****menu map*****/
        
        Menu menuMap = new Menu("Mapa");
        
        /** item focuse **/
        MenuItem focus = new MenuItem("Zaostřit na bota");
        focus.setAccelerator(KeyCombination.keyCombination("F5"));
        focus.setOnAction((action)->{
            game.setFocus();
        });
        focus.setDisable(true);
        
        /** item focusPlus **/
        MenuItem focusPlus = new MenuItem("Přiblížit");
        focusPlus.setAccelerator(KeyCombination.keyCombination("ADD"));
        focusPlus.setOnAction((action)->{
            game.plusFocusLevel();
        });
        focusPlus.setDisable(true);
        
        /** item focuseMinus **/
        MenuItem focusMinus = new MenuItem("Oddlálit");
        focusMinus.setAccelerator(KeyCombination.keyCombination("SUBTRACT"));
        focusMinus.setOnAction((action)->{
            game.minusFocusLevel();
        });
        focusMinus.setDisable(true);
        
        menuMap.getItems().addAll(focus, focusPlus, focusMinus);
        
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
            game.actionLaserBeam();
            System.out.println("laser - SPACE");
        });
        
        /**item wait**/
        MenuItem wait = new MenuItem("Setrvat na místě");
        wait.setAccelerator(KeyCombination.keyCombination("S"));
        wait.setOnAction((ActionEvent) -> {
            System.out.println("wait - S");
            game.actionWait();
        });        
        menuControl.getItems().addAll(nextStep, move, turnLeft, turnRight, wait, laser);
        
        /*****menu Server*****/
        Menu menuServer = new Menu("Server");
        
        /**item servername**/
        MenuItem serverName = new MenuItem("Připojit k serveru");
        serverName.setAccelerator(KeyCombination.keyCombination("F1"));
        serverName.setOnAction((ActionEvent t) -> {
            game.showServerNameDialog();           
            game.newGame();
            refreshSettings(game);
        });
        menuServer.getItems().addAll(serverName);
        
        /*****menu help*****/
        Menu menuHelp = new Menu("Nápověda");
        
        /**item aboutApp**/
        MenuItem aboutApp = new MenuItem("O aplikaci");
        aboutApp.setOnAction((Action) -> {
            aboutAppDialog.showDialog(game.getStage());
        });
        
        /**item update**/
        MenuItem update = new MenuItem("Zkontrolovat aktualizace");
        update.setOnAction((Action) -> {
            //updateDialog.showDialog(game.getStage());
        });        
        update.setDisable(true);
        menuHelp.getItems().addAll(aboutApp, update);
                
        /*****menu Settings*****/
        Menu menuSettings = new Menu("Nastavení");
        
        /**item delayPlus**/
        MenuItem delayPlus = new MenuItem("Zvětšit prodlevu mezi tahy");
        delayPlus.setAccelerator(KeyCombination.keyCombination("F9"));
        delayPlus.setDisable(true);
        delayPlus.setOnAction((ActionEvent) -> {
            System.out.println("delay+ - F7");
            game.setDelayPlus();
        });
        
        /**item delayMinus**/
        MenuItem delayMinus = new MenuItem("Snížit prodlevu mezi tahy");
        delayMinus.setAccelerator(KeyCombination.keyCombination("F10"));
        delayMinus.setDisable(true);
        delayMinus.setOnAction((ActionEvent) -> {
            System.out.println("delay- - F8");
            game.setDelayMinus();
        });
        
        /**item autogame**/
        CheckMenuItem autoGame = new CheckMenuItem("Zapnout PyBota");
        autoGame.setAccelerator(KeyCombination.keyCombination("F6"));
        autoGame.selectedProperty().addListener(listener -> {
            System.out.println("autoGame - F6");
            game.autoBot();
            refreshSettings(game);
        });
        
        /**item autostart**/
        CheckMenuItem autostart = new CheckMenuItem("Po dokončení začít novou hru");
        autostart.setAccelerator(KeyCombination.keyCombination("F7"));
        autostart.selectedProperty().addListener(listener -> {
            System.out.println("autoStart - F7");
            game.setAutoNewGame();
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

        menuSettings.getItems().addAll(autoGame, autostart, showLog, delayPlus, delayMinus);        
        
        menuBar.getMenus().addAll(menuServer, menuGame, menuMap, menuControl, menuSettings, menuHelp);
        refreshSettings(game);
        return menuBar;
    }
    
    private void refreshSettings(Game game){
        //Pokud je hra aktivní, zapne ovládání
        if (game.isActiveGame()){
            menuBar.getMenus().get(3).getItems().forEach((item) -> {
                item.setDisable(false);
            });
        }
        else{
            menuBar.getMenus().get(3).getItems().forEach((item) -> {
                item.setDisable(true);
            });
        }
        
        //Pokud je batteryGame, zapne akci čekání
        if (!game.getGameInfo()[1])
            menuBar.getMenus().get(3).getItems().get(4).setDisable(true);
        
        //Pokud je laserGame, zapne akci laserBeam
        if (!game.getGameInfo()[2])
            menuBar.getMenus().get(3).getItems().get(5).setDisable(true);
        
        //Pokud je tahová hra, zapne delay        
        if (game.getGameInfo()[0]){
            menuBar.getMenus().get(4).getItems().get(3).setDisable(false);
            menuBar.getMenus().get(4).getItems().get(4).setDisable(false);
        }
        else{
            menuBar.getMenus().get(4).getItems().get(3).setDisable(true);
            menuBar.getMenus().get(4).getItems().get(4).setDisable(true);
        }
        
        if (game.isAutoBot()){
            menuBar.getMenus().get(3).getItems().forEach((item) -> {
                item.setDisable(true);
            });
        }
    }
}
