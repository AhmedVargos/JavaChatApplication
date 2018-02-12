package com.chatcompany.chatclient.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.chatcompany.chatclient.views.MainApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        makeProfilePic();
        close();
        minimize();
        sign_out();
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
                Platform.exit();
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
                moveToSignIn();
            }
        });
    }

    private void moveToSignIn() {
        try {
            MainApp.setMainUser(null);

            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/SignIn.fxml"));
            Scene scene = new Scene(parent);
            
            //Open new scene and position it in the middle
            MainApp.getMainStage().setScene(scene);
            MainApp.getMainStage().setWidth(537);
            MainApp.getMainStage().setHeight(437);
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            MainApp.getMainStage().setX((primScreenBounds.getWidth() - MainApp.getMainStage().getWidth()) / 2);
            MainApp.getMainStage().setY((primScreenBounds.getHeight() - MainApp.getMainStage().getHeight()) / 2);

        } catch (IOException ex) {
            Logger.getLogger(ToolBarViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
