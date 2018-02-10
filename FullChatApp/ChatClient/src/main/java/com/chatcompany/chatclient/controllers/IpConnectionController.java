package com.chatcompany.chatclient.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class IpConnectionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label exit;
    @FXML
    private Label min;
    @FXML
    private TextField ipTextfield;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Platform.exit();
            }
        });
        min.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                ((Stage) min.getScene().getWindow()).setIconified(true);
            }
        });

    }

    public void connection() {
        String ip = ipTextfield.getText();
        String localHost="10.245.3.238";
        Pattern IP_PATTERN = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
   
        if(ip.equals(localHost)&&IP_PATTERN.matcher(ip).matches()){
            try {
                Parent parent=FXMLLoader.load(getClass().getResource("SignIn.fxml"));
                        } catch (IOException ex) {
                Logger.getLogger(IpConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
