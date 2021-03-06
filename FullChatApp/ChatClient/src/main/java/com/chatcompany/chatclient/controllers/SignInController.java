/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.chatclient.controllers;

import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.Constants;
import com.chatcompany.commonfiles.commModels.User;
import com.chatcompany.commonfiles.common.LoginInterface;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eman
 */
public class SignInController implements Initializable {

    /*@FXML
    private AnchorPane anchorBase;*/
    @FXML
    private JFXTextField textFieldMail;
    @FXML
    private JFXButton loginbtn;
    @FXML
    private JFXPasswordField passwordFieldPassword;

    @FXML
    private JFXButton signUp;

    @FXML
    private Label minimize;
    @FXML
    private Label close;
    @FXML
    private Label emaliLabel;
    @FXML
    private Label passLabel;

    private boolean valid = true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        signUp();
        close();
        minimize();
        login();
        textFeildListner();
    }

    private void signUp() {
        signUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    System.out.println("clicked");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signUp.fxml"));
                    Parent root = loader.load();
                    SignUpController controller = loader.getController();
                   
                    Scene scene = new Scene(root);

                    MainApp.getMainStage().setScene(scene);
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    MainApp.getMainStage().setX((primScreenBounds.getWidth() - MainApp.getMainStage().getWidth()) / 2);
                    MainApp.getMainStage().setY((primScreenBounds.getHeight() - MainApp.getMainStage().getHeight()) / 2);

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
                System.exit(0);
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

    private void login() {
        loginbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (isValidate()) {
                    try {

                        if (!textFieldMail.getText().isEmpty() || !passwordFieldPassword.getText().isEmpty()) {

                            LoginInterface loginInterface = (LoginInterface) MainApp.getServiceLoaderInterface().getServiceInstance(Constants.LOGIN_SERVICE);

                            User user = loginInterface.login(textFieldMail.getText(), passwordFieldPassword.getText(),MainApp.getClientIntImp());
                            if (user != null) {
                                MainApp.setMainUser(user);

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ParentChatView.fxml"));
                                Parent root = loader.load();
                                MainChatParentController controller = loader.getController();
                                //controller.setText(nameTxtField.getText());

                                Scene scene = new Scene(root);
                                scene.getStylesheets().addAll("/styles/Styles.css","/styles/list_view_cell_gap.css");
                                
                                MainApp.getMainStage().setScene(scene);
                                //MainApp.getMainStage().setWidth(850);
                                //MainApp.getMainStage().setHeight(500);
                                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                                MainApp.getMainStage().setX((primScreenBounds.getWidth() - MainApp.getMainStage().getWidth()) / 2);
                                MainApp.getMainStage().setY((primScreenBounds.getHeight() - MainApp.getMainStage().getHeight()) / 2);

                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Not found");
                                alert.setContentText("User name or password is not correct!");
                                alert.show();
                            }
                        } else {

                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Empty Fields");
                            alert.setContentText("User name and Password must be entered");
                            alert.show();
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    private boolean isValidate() {

        if (textFieldMail.getText().trim().isEmpty()) {
           emaliLabel.setText("Invalid");
            valid = false;

        }
        if (passwordFieldPassword.getText().trim().isEmpty()) {
            passLabel.setText("Invalid");
            valid = false;

        }

        return valid;
    }

    private void textFeildListner() {
        textFieldMail.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                emaliLabel.setText("");
            }
        });
        passwordFieldPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                  passLabel.setText("");
               
            }
        });

    }
}
