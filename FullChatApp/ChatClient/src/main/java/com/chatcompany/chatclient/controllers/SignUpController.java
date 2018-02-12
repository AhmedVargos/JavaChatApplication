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
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
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
    private TextField userNameField;

    @FXML
    private Button registerBtn;
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
        createAccount();
    }

    private void backToLoginScene() {
        backToLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    System.out.println("clicked");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignIn.fxml"));
                    Parent root = loader.load();
                    SignInController controller = loader.getController();
                    //controller.setText(nameTxtField.getText());

                    Scene scene = new Scene(root);

                    //Open new scene and position it in the middle
                    MainApp.getMainStage().setScene(scene);
                    MainApp.getMainStage().setWidth(537);
                    MainApp.getMainStage().setHeight(437);
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

    private void createAccount() {
        registerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User user = new User(userNameField.getText(),
                         emailField.getText(),
                         firstNameField.getText(),
                         lastNameField.getText(),
                         passwordField.getText(),
                         "0",
                         countryField.getText(),
                         "1");
                boolean isAccepted = false;
                try {
                    LoginInterface loginInterface = (LoginInterface) MainApp.getServiceLoaderInterface().getServiceInstance(Constants.LOGIN_SERVICE);
                    isAccepted = loginInterface.SignUp(user);
                } catch (SQLException ex) {
                    Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (isAccepted) {
                    try {
                        MainApp.setMainUser(user);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ParentChatView.fxml"));
                        Parent root = loader.load();
                        MainChatParentController controller = loader.getController();
                        //controller.setText(nameTxtField.getText());

                        Scene scene = new Scene(root);
                        scene.getStylesheets().add("/styles/Styles.css");
                        
                        //Open new scene and position it in the middle
                        MainApp.getMainStage().setScene(scene);
                        MainApp.getMainStage().setWidth(850);
                        MainApp.getMainStage().setHeight(500);
                        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                        MainApp.getMainStage().setX((primScreenBounds.getWidth() - MainApp.getMainStage().getWidth()) / 2);
                        MainApp.getMainStage().setY((primScreenBounds.getHeight() - MainApp.getMainStage().getHeight()) / 2);

                    } catch (IOException ex) {
                        Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("User name used");
                    alert.setContentText("Please change the user name enterd");
                    alert.show();
                }

            }
        });
    }

}
