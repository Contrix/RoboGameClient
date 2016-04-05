/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robogameclient;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author Jirka
 */
public class AboutAppDialog {
    private Stage dialog;
    
    public Stage showDialog(Window parent) {
        dialog = new Stage();
        dialog.initOwner(parent);
        dialog.initStyle(StageStyle.DECORATED);
        dialog.setTitle("O aplikaci");
        dialog.setWidth(500);
        dialog.setHeight(500);
        dialog.setResizable(false);
        
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        
        Label autorLabel = new Label("Autor - Jiří Hanák");
        Label versionLabel = new Label("Verze - 0.7.6");
        Label licence = new Label("Licence - CC BY 3.0 CZ");
        
        root.getChildren().addAll(autorLabel, versionLabel, licence);
        Scene scene = new Scene(root);        
        dialog.setScene(scene);
        dialog.showAndWait();
        return dialog;
    }
}
