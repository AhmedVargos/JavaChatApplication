/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.chatclient.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eman
 */
public class chatcomp implements Initializable {

    @FXML
    private AnchorPane anchorBase;
    @FXML
    private TextField textFieldMail;
    @FXML
    private Button button1;
    @FXML
    private PasswordField passwordFieldPassword;
  
    @FXML
    private Button signUpbtn;
  
    @FXML
    private Label minimize;
    @FXML
    private Label close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        signUp();
        close();
        minimize();
    }

    private void signUp() {
        signUpbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    System.out.println("clicked");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signUp.fxml"));
                    Parent root = loader.load();
                    SignUpController controller = loader.getController();
                    //controller.setText(nameTxtField.getText());

                    Scene scene = new Scene(root);

                    Stage stage = (Stage) anchorBase.getScene().getWindow();
                    stage.setWidth(700);
                    stage.setHeight(500);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    //close signup scene..........
    private void close() {
        close.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Platform.exit();
            }
        });
    }

    //minmize signup scene............
    private void minimize() {
        minimize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                ((Stage) minimize.getScene().getWindow()).setIconified(true);
            }
        });
    }
}
