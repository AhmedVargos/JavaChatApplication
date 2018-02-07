package com.chatcompany.chatclient.controllers;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactTabsLayout implements Initializable{
    @FXML
    public Tab chatGroupTab;
    @FXML
    public Tab requestsTab;
    @FXML
    public Tab groupTab;
    public JFXTabPane contactsTabPane;
    @FXML
    private Tab friendTab;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //contactsTabPane.getStyleClass().add("custome-header-pane");

        friendTab.setText(null);
        friendTab.setGraphic(buildImage("/images/ic_person_white_24dp_2x.png"));

        requestsTab.setText(null);
        requestsTab.setGraphic(buildImage("/images/ic_send_white_24dp_2x.png"));

        groupTab.setText(null);
        groupTab.setGraphic(buildImage("/images/ic_group_white_24dp_2x.png"));

        chatGroupTab.setText(null);
        chatGroupTab.setGraphic(buildImage("/images/ic_group_add_white_24dp_2x.png"));
    }

    // Helper method to create image from image patch
    private static ImageView buildImage(String imgPatch) {
        Image i = new Image(imgPatch,30,30,true,true);
        ImageView imageView = new ImageView();
        //You can set width and height
        //imageView.setFitHeight(16);
        //imageView.setFitWidth(16);
        imageView.setImage(i);
        return imageView;
    }
}
