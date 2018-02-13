package com.chatcompany.chatclient.controllers;

import com.chatcompany.commonfiles.commModels.User;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RequestListItemController implements Initializable{
    @FXML
    private ImageView acceptRequestImage;
    @FXML
    private ImageView cancelRequestImage;
    @FXML
    private Circle userImage;
    @FXML
    private Text userName;

    private User mUser;

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

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
}
