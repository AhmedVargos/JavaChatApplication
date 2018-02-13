package com.chatcompany.chatclient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendItemViewController implements Initializable{
    @FXML
    private Circle userImage;
    @FXML
    private Text userName;
    @FXML
    private Circle userConnState;
    @FXML
    private ImageView cancelRequestImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setUserImage(String userImage) {
        Image img=new Image(userImage);
        ImagePattern imagePattern= new ImagePattern(img);
        this.userImage.setFill(imagePattern);
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    public void setUserConnState(Color color) {
        this.userConnState.setStroke(color);
    }
}
