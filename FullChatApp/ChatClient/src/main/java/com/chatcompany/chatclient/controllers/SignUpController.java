/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.chatclient.controllers;

import com.chatcompany.chatclient.views.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eman
 */
public class SignUpController implements Initializable {

    @FXML
    private AnchorPane anchorBase;
    @FXML
    private TextField emailField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private Button button;
    @FXML
    private TextField countryField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
  
    @FXML
    private Label close;
    
    @FXML
    private Label minimize;

    @FXML
    private ImageView backToLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        backToLoginScene();
        close();
        minimize();
    }

    private void backToLoginScene() {
        backToLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    System.out.println("clicked");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignIn.fxml"));
                    Parent root = loader.load();
                    chatcomp controller = loader.getController();
                    //controller.setText(nameTxtField.getText());

                    Scene scene = new Scene(root);

                    
                    MainApp.getMainStage().setScene(scene);
                    MainApp.getMainStage().setWidth(537);
                    MainApp.getMainStage().setHeight(437);
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
