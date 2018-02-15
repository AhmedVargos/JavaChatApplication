/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.chatclient.controllers;

import com.chatcompany.commonfiles.commModels.User;
import com.jfoenix.controls.JFXTabPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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
        chatTabPane.getTabs().add(new Tab(user.getUsername()));
    }
}
