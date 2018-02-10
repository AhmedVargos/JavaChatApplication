package com.chatcompany.chatserver.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerMainViewController implements Initializable {

    @FXML
    public ImageView serverViewMin;
    @FXML
    public ImageView serverViewClose;
    @FXML
    public Button serverStartBtn;
    @FXML
    public Button serverStopBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialize handlers to the buttons
        close();
        minimize();

        serverStartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startServer();
            }
        });


        serverStopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopServer();
            }
        });
    }

    //stops the server
    private void stopServer() {

    }

    //starts the server
    private void startServer() {

    }

    //close window will close all connections
    private void close() {
        serverViewClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Platform.exit();
            }
        });
    }

    //minimize the screen
    private void minimize() {
        serverViewMin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                ((Stage)serverViewMin.getScene().getWindow()).setIconified(true);
            }
        });
    }
}
