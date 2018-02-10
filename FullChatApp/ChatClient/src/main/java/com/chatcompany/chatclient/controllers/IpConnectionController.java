package com.chatcompany.chatclient.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
                
                ((Stage)min.getScene().getWindow()).setIconified(true);
            }
        });
    }    
    
}
