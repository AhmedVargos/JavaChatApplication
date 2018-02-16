/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.chatclient.controllers;

import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.ChatSession;
import com.chatcompany.commonfiles.commModels.Message;
import com.chatcompany.commonfiles.commModels.User;
import com.jfoenix.controls.JFXTabPane;
import com.sun.org.apache.bcel.internal.generic.F2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

/**
 *
 * @author Dell
 */
public class ChatAreaController implements Initializable {

    @FXML
    private JFXTabPane chatTabPane;
    private static HashMap<String, ChatBoxController> chatBoxConrollers = new HashMap<>();

    /*public JFXTabPane getChatTabPane() {
        return chatTabPane;
    }*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void openNewChatTabReciver(ChatSession ch) {
        final ArrayList<Tab> tabs = new ArrayList<>();
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            Parent root = fXMLLoader.load(getClass().getResource("/fxml/ChatBox.fxml").openStream());
            tabs.add(new Tab(ch.getChatUsers().get(0).getUsername()));
            tabs.get(0).setContent(root);
            Image imag = new Image("/images/x.png");
            ImageView imgView = new ImageView(imag);
            imgView.setFitHeight(20);
            imgView.setFitWidth(20);
            // imgView.setOnMouseClicked
            tabs.get(0).setGraphic(imgView);
            // chatTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
            ArrayList<User> arrayList = new ArrayList<>();

            ChatBoxController boxController = fXMLLoader.getController();
            boxController.setChatSession(ch);
            tabs.get(0).setId(ch.getId());
            chatBoxConrollers.put(tabs.get(0).getId(), boxController);
        } catch (IOException ex) {
            //Logger.getLogger(ChatAreaController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chatTabPane.getTabs().add(tabs.get(0));

            }
        });
    }

    public void openNewChatSession(User user) {
        //add the chat FXML box and pass the chat session obj to the chat tab controller 
        //and when send each time send message and a chat session obj

        //Tab tab=new Tab(user.getUsername());
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            Parent root = fXMLLoader.load(getClass().getResource("/fxml/ChatBox.fxml").openStream());
            Tab tab = new Tab(user.getUsername());
            tab.setContent(root);
            Image imag = new Image("/images/x.png");
            ImageView imgView = new ImageView(imag);
            imgView.setFitHeight(20);
            imgView.setFitWidth(20);
            // imgView.setOnMouseClicked
            tab.setGraphic(imgView);
            // chatTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
            ArrayList<User> arrayList = new ArrayList<>();

            User myUser = MainApp.getMainUser();
            arrayList.add(user);
            arrayList.add(myUser);

            ChatSession chatSession = new ChatSession(myUser.getId() + "" + user.getId(), arrayList);
            ChatBoxController boxController = fXMLLoader.getController();
            boxController.setChatSession(chatSession);
            tab.setId(chatSession.getId());
            chatBoxConrollers.put(tab.getId(), boxController);
            chatTabPane.getTabs().add(tab);
        } catch (IOException ex) {
            //Logger.getLogger(ChatAreaController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
// Parent root = (Parent) fXMLLoader.load(this.getClass().getResource();
        //          newValue.setContent(root);

        // chatBox
    }

    public JFXTabPane getChatTabPane() {
        return chatTabPane;
    }

    public static void append(Message msg, String tabId) {
        ChatBoxController chatBoxController = chatBoxConrollers.get(tabId);
        chatBoxController.appendText(msg);

    }
}
