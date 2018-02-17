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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static com.chatcompany.commonfiles.commModels.Constants.LOGIN_SERVICE;
import static com.chatcompany.commonfiles.commModels.Constants.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;

/**
 * FXML Controller class
 *
 * @author Eman
 */
public class SignUpController implements Initializable {

    @FXML
    private AnchorPane anchorBase;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXTextField lastNameField;
    @FXML
    private JFXTextField firstNameField;
    @FXML
    private JFXTextField userNameField;

    @FXML
    private JFXButton registerBtn;
    @FXML
    private JFXTextField countryField;

    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXPasswordField ConfirmPassword;

    @FXML
    private Label close;

    @FXML
    private Label minimize;

    @FXML
    private ImageView backToLogin;

    //labels validation..........
    @FXML
    private Label fnameLabel;
    @FXML
    private Label lnameLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label emaliLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label confirmLabel;
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static Pattern pattern;
    private Matcher matcher;

    boolean valid = true;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

        backToLoginScene();
        close();
        minimize();
        createAccount();
        labelListner();
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
                    /*MainApp.getMainStage().setWidth(537);
                    MainApp.getMainStage().setHeight(437);
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    MainApp.getMainStage().setX((primScreenBounds.getWidth() - MainApp.getMainStage().getWidth()) / 2);
                    MainApp.getMainStage().setY((primScreenBounds.getHeight() - MainApp.getMainStage().getHeight()) / 2);
                     */
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

    private void createAccount() {
        registerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int gender = 0;
                if (maleRadioButton.isSelected()) {
                    gender = MALE;
                }
                if (femaleRadioButton.isSelected()) {
                    gender = FEMALE;
                }
                if (isValidate()) {
                    User user = new User(userNameField.getText(), emailField.getText(), firstNameField.getText(), lastNameField.getText(), passwordField.getText(), gender, countryField.getText(), ONLINE, AVAILABLE);

                    System.out.println("data inserted succsefuly");

                    try {
                        LoginInterface loginInterface = (LoginInterface) MainApp.getServiceLoaderInterface().getServiceInstance(Constants.LOGIN_SERVICE);

                        user = loginInterface.SignUp(user, MainApp.getClientIntImp());
                    } catch (SQLException ex) {
                        Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RemoteException ex) {
                        Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (user != null) {
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
                            /*MainApp.getMainStage().setWidth(850);
                            MainApp.getMainStage().setHeight(500);
                            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                            MainApp.getMainStage().setX((primScreenBounds.getWidth() - MainApp.getMainStage().getWidth()) / 2);
                            MainApp.getMainStage().setY((primScreenBounds.getHeight() - MainApp.getMainStage().getHeight()) / 2);
                             */
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
            }
        }
        );
    }

    private boolean isValidate() {
        //frist name field
        if (firstNameField.getText().trim().isEmpty()) {
            fnameLabel.setText("empty!");
            valid = false;
        } else if (firstNameField.getText().length() < 3) {
            fnameLabel.setText("at least 3 letter");
            valid = false;
        }
        //last name field
        if (lastNameField.getText().trim().isEmpty()) {
            lnameLabel.setText("empty!");
            valid = false;
        } else if (lastNameField.getText().length() < 3) {
            lnameLabel.setText("at least 3 letter");
            valid = false;
        }
        //email field
        if (emailField.getText().trim().isEmpty()) {
            emaliLabel.setText("empty!");
            valid = false;
        } else if (!validateEmail(emailField.getText())) {
            emaliLabel.setText("invalid Email");
            valid = false;
        }
        //user name field
        if (userNameField.getText().trim().isEmpty()) {
            userNameLabel.setText("empty!");
            valid = false;
        } else if (userNameField.getText().length() < 3) {
            userNameLabel.setText("at least 3 letter");
            valid = false;
        }

        //password field
        if (passwordField.getText().trim().isEmpty()) {
            passwordLabel.setText("empty!");
            valid = false;
        } else if (passwordField.getText().length() < 3) {
            passwordLabel.setText("at least 3 letter");
            valid = false;
        }
        //password field
        if (ConfirmPassword.getText().trim().isEmpty()) {
            confirmLabel.setText("empty!");
            valid = false;
        } else if (ConfirmPassword.getText().equals(passwordField.getText())) {
            confirmLabel.setText("not confirm");
            valid = false;
        }

        //country field
        if (countryField.getText().trim().isEmpty()) {
            countryLabel.setText("empty!");
            valid = false;
        }

        return valid;

    }

    private boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void labelListner() {
        firstNameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                fnameLabel.setText("");

            }
        });

        lastNameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                lnameLabel.setText("");

            }
        });

        passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                passwordLabel.setText("");

            }
        });

        countryField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                countryLabel.setText("");

            }
        });
        userNameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                userNameLabel.setText("");

            }
        });
        emailField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                emaliLabel.setText("");
                confirmLabel.setText("");

            }
        });

    }

}
