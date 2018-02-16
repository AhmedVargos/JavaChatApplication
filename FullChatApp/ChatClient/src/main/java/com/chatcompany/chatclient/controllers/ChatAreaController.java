/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.chatclient.controllers;

import com.chatcompany.commonfiles.commModels.User;
import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Dell
 */
public class ChatAreaController implements Initializable {

    @FXML
    private JFXTabPane chatTabPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void openNewChatSession(User user) {
        //add the chat FXML box and pass the chat session obj to the chat tab controller 
        //and when send each time send message and a chat session obj

        //Tab tab=new Tab(user.getUsername());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChatBox.fxml"));
            Tab tab = new Tab(user.getUsername());
            tab.setContent(root);
            Image imag = new Image("/images/x.png");
            ImageView imgView = new ImageView(imag);
            imgView.setFitHeight(20);
            imgView.setFitWidth(20);
           // imgView.setOnMouseClicked
            tab.setGraphic(imgView);
            // chatTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
            chatTabPane.getTabs().add(tab);
        } catch (IOException ex) {
            Logger.getLogger(ChatAreaController.class.getName()).log(Level.SEVERE, null, ex);
        }
// Parent root = (Parent) fXMLLoader.load(this.getClass().getResource();
        //          newValue.setContent(root);

        // chatBox
    }
}
