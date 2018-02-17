package com.chatcompany.chatclient.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.Constants;
import com.chatcompany.commonfiles.common.ServerMainInterface;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXNodesList;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.sun.corba.se.spi.activation.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eman
 */
public class ToolBarViewController implements Initializable {

    @FXML
    Circle profileCircle;
    @FXML
    private ImageView imageView;
    @FXML
    private Label exit;
    @FXML
    private Label min;
    @FXML
    private JFXComboBox statusComboBox;
    @FXML
    private Label userName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        userName.setText(MainApp.getMainUser().getUsername());
        makeProfilePic();
        close();
        minimize();
        sign_out();
        makeStateButtons();
    }

    //profile pic......................
    private void makeProfilePic() {
        profileCircle.setStroke(Color.SEAGREEN);
        Image im = new Image("/images/user.png", false);
        profileCircle.setFill(new ImagePattern(im));
        profileCircle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        profileCircle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                FileChooser ProfileImg = new FileChooser();
                //Set filters
                FileChooser.ExtensionFilter filterJpgCap = new FileChooser.ExtensionFilter("JPG imgFiles (*.JPG)", "*.JPG");
                FileChooser.ExtensionFilter filterJpgSml = new FileChooser.ExtensionFilter("jpg imgFiles (*.jpg)", "*.jpg");
                FileChooser.ExtensionFilter filterPngCap = new FileChooser.ExtensionFilter("PNG imgFiles (*.PNG)", "*.PNG");
                FileChooser.ExtensionFilter filterPngSml = new FileChooser.ExtensionFilter("png imgFiles (*.png)", "*.png");
                ProfileImg.getExtensionFilters().addAll(filterJpgSml, filterJpgCap, filterPngSml, filterPngCap);
                //Show open imgFile dialog
                File imgFile = ProfileImg.showOpenDialog(null);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(imgFile);
                    Image imgProfile = new Image(fis);
                    ImagePattern imgPattern = new ImagePattern(imgProfile);
                    profileCircle.setFill(imgPattern);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
        });

    }
    //close application not sign out...............

    private void close() {
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                changeConnectionToOffile();
                System.exit(0);
            }
        });
    }

    private void minimize() {
        min.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                ((Stage) min.getScene().getWindow()).setIconified(true);
            }
        });
    }

    private void sign_out() {
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                changeConnectionToOffile();
                moveToSignIn();
            }
        });
    }

    private void changeConnectionToOffile() {
        ServerMainInterface serverMainInterface = null;
        try {
            serverMainInterface = (ServerMainInterface) MainApp.getServiceLoaderInterface().getServiceInstance(Constants.SERVER_MAIN_SERVICE);
        } catch (RemoteException ex) {
            Logger.getLogger(ToolBarViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        MainApp.getMainUser().setConnStatus(Constants.OFFLINE);
        try {
            serverMainInterface.updateInfo(MainApp.getMainUser());
        } catch (SQLException ex) {
            Logger.getLogger(ToolBarViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ToolBarViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void moveToSignIn() {
        try {

            MainApp.getMainUser().setConnStatus(Constants.OFFLINE);
            ServerMainInterface serverMainInterface = (ServerMainInterface) MainApp.getServiceLoaderInterface().getServiceInstance(Constants.SERVER_MAIN_SERVICE);
            serverMainInterface.updateInfo(MainApp.getMainUser());

            MainApp.setMainUser(null);

            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/SignIn.fxml"));
            Scene scene = new Scene(parent);

            //Open new scene and position it in the middle
            MainApp.getMainStage().setScene(scene);
            /*MainApp.getMainStage().setWidth(537);
            MainApp.getMainStage().setHeight(437);*/
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            MainApp.getMainStage().setX((primScreenBounds.getWidth() - MainApp.getMainStage().getWidth()) / 2);
            MainApp.getMainStage().setY((primScreenBounds.getHeight() - MainApp.getMainStage().getHeight()) / 2);

        } catch (IOException ex) {
            Logger.getLogger(ToolBarViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ToolBarViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void makeStateButtons() {
        statusComboBox.getItems().add("Available");
        statusComboBox.getItems().add("Busy");
        statusComboBox.getItems().add("Away");

        switch (MainApp.getMainUser().getAppearanceStatus()) {
            case Constants.AVAILABLE:
                statusComboBox.getSelectionModel().select(0);
                break;
            case Constants.BUSY:
                statusComboBox.getSelectionModel().select(1);
                break;
            case Constants.NOT_AVAILABLE:
                statusComboBox.getSelectionModel().select(2);
                break;
            default:
                statusComboBox.getSelectionModel().select(0);
        }
        statusComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                try {
                    int state = 0;
                    switch (t1) {
                        case "Available":
                            state = Constants.AVAILABLE;
                            break;
                        case "Busy":
                            state = Constants.BUSY;
                            break;
                        case "Away":
                            state = Constants.NOT_AVAILABLE;
                            break;
                        default:
                            state = Constants.AVAILABLE;

                    }
                    MainApp.getMainUser().setAppearanceStatus(state);
                    ServerMainInterface serverMainInterface = (ServerMainInterface) MainApp.getServiceLoaderInterface().getServiceInstance(Constants.SERVER_MAIN_SERVICE);
                    serverMainInterface.updateInfo(MainApp.getMainUser());
                } catch (RemoteException ex) {
                    Logger.getLogger(ToolBarViewController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ToolBarViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

    }

}
