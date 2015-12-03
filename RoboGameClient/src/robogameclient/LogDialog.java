/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author Jirka
 */
public class LogDialog {
    private ObservableList<String> log = FXCollections.observableArrayList();
    private ListView listView = new ListView();
    private Stage dialog;
    public Stage showDialog(Window parent) {
        dialog = new Stage();
        dialog.initOwner(parent);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Log");
        dialog.setWidth(350);
        dialog.setHeight(650);
        
        listView.setItems(log);

        Scene scene = new Scene(listView);        
        dialog.setScene(scene);
        dialog.show();
        return dialog;
    } 
    
    public void addMsg(String s){
        try{
            log.add(s);
        }catch(Exception ex){
           System.out.println("..." + ex);
        }
    }
    
    public void closeDialog(){
        dialog.close();
        System.out.println("2");
    }
}