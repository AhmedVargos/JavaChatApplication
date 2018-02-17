/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatcompany.chatclient.controllers;

import com.chatcompany.chatclient.views.MainApp;
import com.chatcompany.commonfiles.commModels.ChatSession;
import com.chatcompany.commonfiles.commModels.Constants;
import com.chatcompany.commonfiles.commModels.Message;
import com.chatcompany.commonfiles.commModels.User;
import com.jfoenix.controls.JFXTabPane;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

/**
 *
 * @author Dell
 */
public class ChatAreaController implements Initializable {

    @FXML
    private JFXTabPane chatTabPane;
    private static HashMap<String, ChatBoxController> chatBoxConrollers = new HashMap<>();
    private ArrayList<Integer> peopleInChatWith = new ArrayList<>();
    private HashMap<String, Tab> tabsOpened = new HashMap<>();
    /*public JFXTabPane getChatTabPane() {
        return chatTabPane;
    }*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        chatTabPane.tabClosingPolicyProperty().set(TabPane.TabClosingPolicy.SELECTED_TAB);

    }

    public void openNewChatTabReciver(ChatSession ch,Message msg) {
        final ArrayList<Tab> tabs = new ArrayList<>();
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            Parent root = fXMLLoader.load(getClass().getResource("/fxml/ChatBox.fxml").openStream());
            tabs.add(new Tab());
            tabs.get(0).setContent(root);
            
            HBox tabHead = new HBox();
            tabHead.getChildren().add(new Label(ch.getChatName()));
            Image imag = new Image("/images/x.png");
            ImageView imgView = new ImageView(imag);
            imgView.setFitHeight(20);
            imgView.setFitWidth(20);
            imgView.setId(String.valueOf(ch.getChatUsers().get(0).getId()));
            // imgView.setOnMouseClicked
            imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //tabs.remove();
                    chatTabPane.getTabs().remove(tabsOpened.get(imgView.getId()));
                    tabsOpened.remove(imgView.getId());
                    peopleInChatWith.remove(ch.getChatUsers().get(0));
                }
            });
            tabHead.getChildren().add(imgView);
            tabs.get(0).setGraphic(tabHead);
            // chatTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
            ArrayList<User> arrayList = new ArrayList<>();

            ChatBoxController boxController = fXMLLoader.getController();
            boxController.setChatSession(ch);
            tabs.get(0).setId(ch.getId());
            chatBoxConrollers.put(tabs.get(0).getId(), boxController);
            tabsOpened.put(String.valueOf(ch.getChatUsers().get(0).getId()), tabs.get(0));
            
            peopleInChatWith.add(ch.getChatUsers().get(0).getId());
        } catch (IOException ex) {
            //Logger.getLogger(ChatAreaController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chatTabPane.getTabs().add(tabs.get(0));
                chatTabPane.getSelectionModel().select(tabs.get(0));
                append(msg, tabs.get(0).getId());
            }
        });
    }
    
    public void checkUserForChat(User user){
        if(user.getConnStatus() == Constants.OFFLINE){
            System.out.println("User is offline");
        }else{
            boolean alreadyChating = false;
            for(int i = 0; i < peopleInChatWith.size(); i ++){
                if(peopleInChatWith.get(i) == user.getId()){
                    alreadyChating = true;
                }
            }
            if(alreadyChating){
                System.out.println("Already speaking with him");
                chatTabPane.getSelectionModel().select(tabsOpened.get(String.valueOf(user.getId())));
            }else{
                peopleInChatWith.add(user.getId());
                openNewChatSession(user);
            }
        }
    }
    public void openNewChatSession(User user) {
        //add the chat FXML box and pass the chat session obj to the chat tab controller 
        //and when send each time send message and a chat session obj

        //Tab tab=new Tab(user.getUsername());
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            Parent root = fXMLLoader.load(getClass().getResource("/fxml/ChatBox.fxml").openStream());
            Tab tab = new Tab(user.getUsername());
            tab.closableProperty().set(true);
            tab.closableProperty().setValue(Boolean.TRUE);
            tab.setContent(root);
            /*Image imag = new Image("/images/x.png");
            ImageView imgView = new ImageView(imag);
            imgView.setFitHeight(20);
            imgView.setFitWidth(20);
            // imgView.setOnMouseClicked
            tab.setGraphic(imgView);*/
            // chatTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
            ArrayList<User> arrayList = new ArrayList<>();

            User myUser = MainApp.getMainUser();
            arrayList.add(myUser);
            arrayList.add(user);

            ChatSession chatSession = new ChatSession(myUser.getId() + "" + user.getId(), arrayList,myUser.getUsername());
            ChatBoxController boxController = fXMLLoader.getController();
            boxController.setChatSession(chatSession);
            String temp = tab.getId();
            tab.setId(chatSession.getId());
            String temp2 = tab.getId();
            chatBoxConrollers.put(tab.getId(), boxController);
            chatTabPane.getTabs().add(tab);   
            chatTabPane.getSelectionModel().select(tab);
            tabsOpened.put(String.valueOf(user.getId()), tab);
           
            peopleInChatWith.add(user.getId());
        } catch (IOException ex) {
            //Logger.getLogger(ChatAreaController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
// Parent root = (Parent) fXMLLoader.load(this.getClass().getResource();
        //          newValue.setContent(root);

        // chatBox
    }
    
    
    public void openNewGroupChatSession(ArrayList<User> users, String chatName) {
        //add the chat FXML box and pass the chat session obj to the chat tab controller 
        //and when send each time send message and a chat session obj
;
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            Parent root = fXMLLoader.load(getClass().getResource("/fxml/ChatBox.fxml").openStream());
            Tab tab = new Tab(chatName);
            tab.setContent(root);
            /*Image imag = new Image("/images/x.png");
            ImageView imgView = new ImageView(imag);
            imgView.setFitHeight(20);
            imgView.setFitWidth(20);
            // imgView.setOnMouseClicked
            tab.setGraphic(imgView);*/
            // chatTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
            
            
            ChatSession chatSession = new ChatSession(MainApp.getMainUser().getUsername() + new java.util.Date(), users,chatName);
            ChatBoxController boxController = fXMLLoader.getController();
            boxController.setChatSession(chatSession);
            tab.setId(chatSession.getId());
            chatBoxConrollers.put(tab.getId(), boxController);
            chatTabPane.getTabs().add(tab);   
            chatTabPane.getSelectionModel().select(tab);
            tabsOpened.put(chatSession.getId(), tab);
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
