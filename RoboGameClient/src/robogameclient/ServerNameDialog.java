package robogameclient;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jirka
 */
public class ServerNameDialog {
    private Stage dialog;
    private String serverName;
    
    public Stage showDialog(Window parent) {
        dialog = new Stage();
        dialog.initOwner(parent);
        dialog.initStyle(StageStyle.DECORATED);
        dialog.setTitle("Server name");
        dialog.setWidth(350);
        dialog.setHeight(150);
        dialog.setResizable(false);
        
        VBox root = new VBox();
        HBox hBox = new HBox();
        Button button = new Button();
        Label label = new Label("Zadejte adresu serveru");
        TextField textField = new TextField();
        
        root.getChildren().addAll(hBox, button);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        
        hBox.getChildren().addAll(label, textField);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        
        button.setText("Odeslat");
        button.setOnAction((event) -> {
            if (textField.getText().length() < 3){
                textField.setStyle("-fx-background-color: salmon");
            }
            else {
                System.out.println("Button Action");
                serverName = textField.getText();
                dialog.close();
            }
        });

        Scene scene = new Scene(root);        
        dialog.setScene(scene);
        dialog.showAndWait();
        return dialog;
    } 
    
    public String getValue(){
        return serverName;
    }    
}